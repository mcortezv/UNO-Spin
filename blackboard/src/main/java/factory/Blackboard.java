package factory;
import dominio.IDominio;
import dominio.entidades.ConfiguracionPartida;
import dominio.entidades.Jugador;
import dominio.entidades.Partida;
import dominio.entidades.enums.EstadoPartida;
import dominio.entidades.enums.TipoAccion;
import dto.*;
import interfaces.IBlackboardObservador;
import mappers.CartaMapper;
import mappers.ConfiguracionPartidaMapper;
import mappers.JugadorMapper;
import interfaces.IBlackboard;
import interfaces.IReceptor;
import interfaces.ISerializer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Blackboard.
 *
 */
public class Blackboard implements IBlackboard, IReceptor{

    private static final int MIN_JUGADORES = 2;
    private static final int MAX_JUGADORES = 4;

    private static Blackboard instance;
    private final List<IBlackboardObservador> suscriptores = new ArrayList<>();
    private IDominio dominio;
    private final ISerializer serializer;
    private final List<Jugador> jugadoresInscritos = new ArrayList<>();
    //private final Set<String> confirmaciones = new HashSet<>();
    private final Map<String, String> ipsPorNombre = new LinkedHashMap<>();
    private final Map<String, Integer> puertosPorNombre = new LinkedHashMap<>();
    private final Map<String, SolicitudUnionDTO> solicitudes = new LinkedHashMap<>();
    private String ultimaAccionLobby = null;
    private String nombreSolicitudResuelta = null;
    private String hostNombre = null;
    private ConfiguracionPartida configuracion;
    private EventoAbandonoDTO eventoAbandono;

    /**
     * Instantiates a new Blackboard.
     *
     * @param serializer the serializer
     */
    Blackboard(ISerializer serializer) {
        this.serializer = serializer;
        instance = this;
    }

    public void setDominio(IDominio dominio) {
        this.dominio = dominio;
    }

    /**
     * Suscribir.
     *
     * @param observador the receptor
     */
    public static void suscribir(IBlackboardObservador observador) {
        instance.suscriptores.add(observador);
    }

    @Override
    public synchronized void recibirMensaje(String json) {
        TipoAccionDTO accion = serializer.deserialize(json, TipoAccionDTO.class);
        TipoAccion tipo = TipoAccion.valueOf(accion.getTipoAccion());
        switch (tipo) {
            case CREAR_PARTIDA      -> procesarCrearPartida(accion);
            case UNIRSE_PARTIDA     -> procesarUnirse(accion);
            case SOLICITAR_INICIO   -> procesarSolicitudInicio(accion);
            case ACEPTAR_SOLICITUD  -> procesarAceptarSolicitud(accion);
            case RECHAZAR_SOLICITUD -> procesarRechazarSolicitud(accion);
            case CONFIRMAR_INICIO   -> procesarConfirmarInicio(accion);
            case RECHAZAR_INICIO    -> procesarRechazarInicio(accion);
            case ABANDONAR_PARTIDA -> procesarAbandono(accion);
            default -> {
                if (dominio == null || dominio.getEstadoPartida() == null || dominio.getEstadoPartida() == EstadoPartida.NO_INICIADA) break;
                switch (tipo) {
                    case JUGAR_CARTA         -> dominio.aplicarJugada(CartaMapper.toEntity(accion.getCartaDTO()));
                    case PEDIR_CARTA         -> dominio.robarCartaJugadorActual();
                    case PEDIR_CARTA_CASTIGO -> dominio.robarCartaSinAvanzarTurno();
                    case GRITAR_UNO          -> dominio.gritarUno();
                    case SELECCIONAR_COLOR   -> dominio.aplicarSeleccionColor(accion.getCartaDTO().getColor().toUpperCase());
                    case GIRAR_RULETA        -> { try { dominio.procesarGiroRuleta(); } catch (Exception e) { System.err.println("Ruleta: " + e.getMessage()); } }
                    case RECONOCER_EVENTO    -> { TipoEventoRuletaDTO ev = dominio.getEventoRuleta(); dominio.aplicarEfectoRuleta(ev, parsearResultado(ev, accion.getResultadoEvento())); dominio.avanzarTurno(); }
                    default                  -> {}
                }
            }
        }
        notificar();
    }

    private void procesarCrearPartida(TipoAccionDTO dto) {
        if (dominio != null) return;
        setDominio(new Partida(EstadoPartida.NO_INICIADA, 0, new ArrayList<>(), true));
        this.configuracion = dto.getConfiguracion() != null
            ? ConfiguracionPartidaMapper.toEntity(dto.getConfiguracion())
            : configuracionDefault();
    }


    private void procesarUnirse(TipoAccionDTO accion) {
        if (dominio == null) {
            setDominio(new Partida(EstadoPartida.NO_INICIADA, 0, new ArrayList<>(), true));
        }
        if (dominio.getEstadoPartida() != EstadoPartida.NO_INICIADA) return;
        if (jugadoresInscritos.size() >= MAX_JUGADORES) return;

        Jugador jugador = JugadorMapper.toEntity(accion.getJugadorDTO());
        ipsPorNombre.put(jugador.getNombre(), accion.getIp());
        puertosPorNombre.put(jugador.getNombre(), accion.getPuerto());

        if (jugadoresInscritos.isEmpty()) {
            jugadoresInscritos.add(jugador);
            hostNombre = jugador.getNombre();
            ultimaAccionLobby = "HOST_UNIDO";
            nombreSolicitudResuelta = null;
            if (accion.getConfiguracion() != null) {
                this.configuracion = ConfiguracionPartidaMapper.toEntity(accion.getConfiguracion());
            }
        } else {
            SolicitudUnionDTO solicitud = new SolicitudUnionDTO(
                    accion.getJugadorDTO(), accion.getIp(), accion.getPuerto(), "PENDIENTE");
            solicitudes.put(jugador.getNombre(), solicitud);
            ultimaAccionLobby = "SOLICITUD_NUEVA";
            nombreSolicitudResuelta = jugador.getNombre();
        }
    }

    private void procesarAceptarSolicitud(TipoAccionDTO accion) {
        if (accion.getJugadorDTO() == null) return;
        String nombre = accion.getJugadorDTO().getNombre();
        SolicitudUnionDTO solicitud = solicitudes.get(nombre);
        if (solicitud == null) return;

        solicitud.setEstado("ACEPTADA");
        Jugador jugador = JugadorMapper.toEntity(solicitud.getJugadorDTO());
        jugadoresInscritos.add(jugador);
        ultimaAccionLobby = "SOLICITUD_ACEPTADA";
        nombreSolicitudResuelta = nombre;

        if (jugadoresInscritos.size() >= MAX_JUGADORES) {
            arrancarPartida();
        }
    }

    private void procesarRechazarSolicitud(TipoAccionDTO accion) {
        if (accion.getJugadorDTO() == null) return;
        String nombre = accion.getJugadorDTO().getNombre();
        SolicitudUnionDTO solicitud = solicitudes.get(nombre);
        if (solicitud == null) return;

        solicitud.setEstado("RECHAZADA");
        ultimaAccionLobby = "SOLICITUD_RECHAZADA";
        nombreSolicitudResuelta = nombre;
    }

    private void procesarSolicitudInicio(TipoAccionDTO accion) {
        if (dominio == null || dominio.getEstadoPartida() != EstadoPartida.NO_INICIADA) return;
        if (accion.getJugadorDTO() == null) return;
        dominio.solicitarInicio();
        String nombre= accion.getJugadorDTO().getNombre();
        nombreSolicitudResuelta= nombre;

       // Jugador jugador= JugadorMapper.toEntity(accion.getJugadorDTO());
       // dominio.agregarConfirmacion(jugador);
        ultimaAccionLobby= "SOLICITAR_INICIO";


//        if (dominio.getCantidadConfirmaciones() >=  MIN_JUGADORES && jugadoresInscritos.size() >= MAX_JUGADORES){
//            arrancarPartida();
//        }
    }

    private void procesarConfirmarInicio(TipoAccionDTO accion){
        Jugador jugador= JugadorMapper.toEntity(accion.getJugadorDTO());
        dominio.agregarConfirmacion(jugador);
        ultimaAccionLobby= "CONFIRMAR_INICIO";

        if (dominio.getCantidadConfirmaciones() >= jugadoresInscritos.size()-1 && jugadoresInscritos.size() >= MIN_JUGADORES){
            arrancarPartida();
        }

    }

    private void procesarRechazarInicio(TipoAccionDTO accion){
        Jugador jugador= JugadorMapper.toEntity(accion.getJugadorDTO());
        dominio.cancelarConfirmaciones();
        ultimaAccionLobby= "NEGAR_INICIO";

    }

    private void arrancarPartida() {
        if (configuracion == null) {
            configuracion = configuracionDefault();
        }
        dominio.iniciarPartida(new ArrayList<>(jugadoresInscritos), configuracion);
    }

    private ConfiguracionPartida configuracionDefault() {
        ConfiguracionPartida c = new ConfiguracionPartida();
        c.setValorMinimo(0);
        c.setValorMaximo(9);
        c.setCantidadComodines(8);
        c.setCantidadCartasAccion(6);
        c.setTiempoMaximoRuleta(10f);
        return c;
    }

    private void notificar() {
        for (IBlackboardObservador s : suscriptores) {
            s.update(this);
        }
    }

    @Override
    public List<JugadorDTO> getJugadores() {
        if (partidaIniciada()) {
            return JugadorMapper.toDTO(dominio.getJugadores(), dominio.getIndiceJugadorActual());
        }
        return JugadorMapper.toDTO(jugadoresInscritos, -1);
    }

    @Override
    public List<SolicitudUnionDTO> getSolicitudesPendientes() {
        return solicitudes.values().stream()
                .filter(s -> "PENDIENTE".equals(s.getEstado()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUltimaAccionLobby() { return ultimaAccionLobby; }

    @Override
    public String getNombreSolicitudResuelta() { return nombreSolicitudResuelta; }

    @Override
    public String getHostNombre() { return hostNombre; }

    @Override
    public CartaDTO getCartaCima() {
        if (!partidaIniciada()) return null;
        return CartaMapper.toDTO(dominio.getCartaCima());
    }

    @Override
    public List<CartaDTO> getManoJugador(int indiceJugador) {
        if (!partidaIniciada()) return List.of();
        return CartaMapper.toDTO(dominio.getManoJugador(indiceJugador));
    }

    @Override
    public String getEstadoPartida() {
        if (dominio == null) return null;
        EstadoPartida e = dominio.getEstadoPartida();
        return e == null ? null : e.name();
    }

    @Override
    public int getIndiceJugadorActual() {
        return dominio == null ? 0 : dominio.getIndiceJugadorActual();
    }

    @Override
    public TipoEventoRuletaDTO getEventoRuleta() {
        if (!partidaIniciada()) return null;
        return dominio.getEventoRuleta();
    }

    @Override
    public boolean isUltimaJugadaValida() {
        return dominio != null && dominio.isUltimaJugadaValida();
    }

    @Override
    public String getIpJugador(String nombre) {
        return ipsPorNombre.get(nombre);
    }

    @Override
    public int getPuertoJugador(String nombre) {
        return puertosPorNombre.getOrDefault(nombre, 0);
    }

    private boolean partidaIniciada() {
        if (dominio == null) return false;
        EstadoPartida e = dominio.getEstadoPartida();
        return e != null && e != EstadoPartida.NO_INICIADA;
    }

    private Object parsearResultado(TipoEventoRuletaDTO evento, String raw) {
        if (raw == null || evento == null) return null;
        if (evento == TipoEventoRuletaDTO.DESCARTAR_POR_NUMERO) {
            try { return Integer.parseInt(raw); } catch (NumberFormatException e) { return null; }
        }
        return raw;
    }

    @Override
    public EventoAbandonoDTO getEventoAbandono() {
        EventoAbandonoDTO temp = this.eventoAbandono;
        eventoAbandono = null;
        return temp;
    }

    private void procesarAbandono(TipoAccionDTO accion) {
        eventoAbandono = dominio.removerJugador(JugadorMapper.toEntity(accion.getJugadorDTO()).getNombre());
    }
}
