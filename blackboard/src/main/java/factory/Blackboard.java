package factory;
import dominio.IDominio;
import dominio.entidades.ConfiguracionPartida;
import dominio.entidades.Jugador;
import dominio.entidades.Partida;
import dominio.entidades.enums.EstadoPartida;
import dominio.entidades.enums.TipoAccion;
import dto.TipoEventoRuletaDTO;
import interfaces.IBlackboardObservador;
import mappers.CartaMapper;
import mappers.ConfiguracionPartidaMapper;
import mappers.JugadorMapper;
import dto.CartaDTO;
import dto.EventoFinalizacionDTO;
import dto.JugadorDTO;
import dto.SolicitudUnionDTO;
import dto.TipoAccionDTO;
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
    private final Set<String> confirmaciones = new HashSet<>();
    private final Map<String, String> ipsPorNombre = new LinkedHashMap<>();
    private final Map<String, Integer> puertosPorNombre = new LinkedHashMap<>();
    private final Map<String, SolicitudUnionDTO> solicitudes = new LinkedHashMap<>();
    private String ultimaAccionLobby = null;
    private String nombreSolicitudResuelta = null;
    private String hostNombre = null;
    private ConfiguracionPartida configuracion;
    private EventoFinalizacionDTO eventoFinalizacion = null;

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
            case CREAR_PARTIDA -> procesarCrearPartida(accion);
            case UNIRSE_PARTIDA -> procesarUnirse(accion);
            case CONFIRMAR_INICIO -> procesarConfirmacion(accion);
            case ACEPTAR_SOLICITUD -> procesarAceptarSolicitud(accion);
            case RECHAZAR_SOLICITUD -> procesarRechazarSolicitud(accion);
            default -> {
                if (dominio == null || dominio.getEstadoPartida() == null || dominio.getEstadoPartida() == EstadoPartida.NO_INICIADA)
                    break;

                if (tipo != TipoAccion.SOLICITAR_FINALIZAR) {
                    eventoFinalizacion = null;
                    dominio.resetearVotos();
                }
                switch (tipo) {
                    case JUGAR_CARTA -> dominio.aplicarJugada(CartaMapper.toEntity(accion.getCartaDTO()));
                    case PEDIR_CARTA -> dominio.robarCartaJugadorActual();
                    case PEDIR_CARTA_CASTIGO -> dominio.robarCartaSinAvanzarTurno();
                    case GRITAR_UNO -> dominio.gritarUno();
                    case SELECCIONAR_COLOR ->
                            dominio.aplicarSeleccionColor(accion.getCartaDTO().getColor().toUpperCase());
                    case GIRAR_RULETA -> {
                        try {
                            dominio.procesarGiroRuleta();
                        } catch (Exception e) {
                            System.err.println("Ruleta: " + e.getMessage());
                        }
                    }
                    case RECONOCER_EVENTO -> {
                        TipoEventoRuletaDTO ev = dominio.getEventoRuleta();
                        dominio.aplicarEfectoRuleta(ev, parsearResultado(ev, accion.getResultadoEvento()));
                        dominio.avanzarTurno();
                    }
                    case SOLICITAR_FINALIZAR -> procesarFinalizacion(accion);
                    default -> {
                    }
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

    private void procesarConfirmacion(TipoAccionDTO accion) {
        if (dominio == null || dominio.getEstadoPartida() != EstadoPartida.NO_INICIADA) return;
        if (accion.getJugadorDTO() == null) return;

        confirmaciones.add(accion.getJugadorDTO().getNombre());

        boolean todosConfirmaron = jugadoresInscritos.stream()
                .allMatch(j -> confirmaciones.contains(j.getNombre()));
        if (todosConfirmaron && jugadoresInscritos.size() >= MIN_JUGADORES) {
            arrancarPartida();
        } else {
            ultimaAccionLobby = null;
        }
    }

    private void procesarFinalizacion(TipoAccionDTO accion) {
        if (dominio == null || dominio.getEstadoPartida() == EstadoPartida.NO_INICIADA) return;

        boolean acepta = Boolean.parseBoolean(accion.getResultadoEvento());
        dominio.registrarVoto(acepta);

        if (dominio.estaTerminada()) {
            List<Jugador> jugadores = dominio.getJugadores();
            List<Jugador> ordenados = jugadores.stream()
                    .sorted((a, b) -> a.getMano().getCartas().size() - b.getMano().getCartas().size())
                    .collect(Collectors.toList());

            List<JugadorDTO> posiciones = new ArrayList<>();
            for (int i = 0; i < ordenados.size(); i++) {
                JugadorDTO dto = JugadorMapper.toDTO(ordenados.get(i), false);
                int posicion = 1;
                for (int j = 0; j < i; j++) {
                    if (ordenados.get(j).getMano().getCartas().size() < ordenados.get(i).getMano().getCartas().size()) {
                        posicion = j + 2;
                    }
                }
                dto.setCantidadCartas(posicion);
                posiciones.add(dto);
            }
            eventoFinalizacion = new EventoFinalizacionDTO(posiciones);
        } else if (dominio.getVotosEnContra() > 0) {
            eventoFinalizacion = new EventoFinalizacionDTO(false);
            dominio.resetearVotos();
        } else {
            EventoFinalizacionDTO enCurso = new EventoFinalizacionDTO(false);
            enCurso.setVotacionEnCurso(true);
            eventoFinalizacion = enCurso;
        }
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
    public EventoFinalizacionDTO getEventoFinalizacion() {
        return eventoFinalizacion;
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
}
