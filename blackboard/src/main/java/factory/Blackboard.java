package factory;
import dominio.IDominio;
import dominio.ILobby;
import dominio.entidades.Jugador;
import dominio.entidades.Partida;
import dominio.entidades.enums.EstadoPartida;
import dominio.entidades.enums.TipoAccion;
import dto.CartaDTO;
import dto.EstadoPartidaDTO;
import dto.JugadorDTO;
import dto.SolicitudUnionDTO;
import dto.TipoAccionDTO;
import dto.TipoEventoRuletaDTO;
import interfaces.IBlackboard;
import interfaces.IBlackboardObservador;
import mappers.CartaMapper;
import mappers.ConfiguracionPartidaMapper;
import mappers.JugadorMapper;
import interfaces.IReceptor;
import interfaces.ISerializer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Blackboard.
 *
 */
public class Blackboard implements IBlackboard, IReceptor{

    private static Blackboard instance;
    private final List<IBlackboardObservador> suscriptores = new ArrayList<>();
    private IDominio dominio;
    private ILobby lobby;
    private final ISerializer serializer;
    private final Map<String, String> ipsPorNombre = new LinkedHashMap<>();
    private final Map<String, Integer> puertosPorNombre = new LinkedHashMap<>();
    private String ultimaAccionLobby = null;
    private String nombreSolicitudResuelta = null;

    /**
     * Instantiates a new Blackboard.
     *
     * @param serializer the serializer
     */
    Blackboard(ISerializer serializer) {
        this.serializer = serializer;
        instance = this;
    }

    private void setPartida(Partida p) {
        this.dominio = p;
        this.lobby = p;
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
            case ACEPTAR_SOLICITUD  -> procesarAceptarSolicitud(accion);
            case RECHAZAR_SOLICITUD -> procesarRechazarSolicitud(accion);
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
        setPartida(new Partida(EstadoPartida.NO_INICIADA, 0, new ArrayList<>(), true));
        if (dto.getConfiguracion() != null) {
            lobby.setConfiguracion(ConfiguracionPartidaMapper.toEntity(dto.getConfiguracion()));
        }
    }

    private void procesarUnirse(TipoAccionDTO accion) {
        if (dominio == null) {
            setPartida(new Partida(EstadoPartida.NO_INICIADA, 0, new ArrayList<>(), true));
        }
        if (!lobby.puedeUnirseAlLobby()) return;

        Jugador jugador = JugadorMapper.toEntity(accion.getJugadorDTO());
        ipsPorNombre.put(jugador.getNombre(), accion.getIp());
        puertosPorNombre.put(jugador.getNombre(), accion.getPuerto());

        lobby.registrarJugadorLobby(jugador);

        if (jugador.getNombre().equals(lobby.getHostNombre())) {
            ultimaAccionLobby = "HOST_UNIDO";
            nombreSolicitudResuelta = null;
            if (accion.getConfiguracion() != null) {
                lobby.setConfiguracion(ConfiguracionPartidaMapper.toEntity(accion.getConfiguracion()));
            }
        } else {
            ultimaAccionLobby = "SOLICITUD_NUEVA";
            nombreSolicitudResuelta = jugador.getNombre();
        }
    }

    private void procesarAceptarSolicitud(TipoAccionDTO accion) {
        if (accion.getJugadorDTO() == null) return;
        String nombre = accion.getJugadorDTO().getNombre();
        lobby.aceptarSolicitudLobby(nombre);
        ultimaAccionLobby = "SOLICITUD_ACEPTADA";
        nombreSolicitudResuelta = nombre;
        if (lobby.esSalaLlena()) arrancarPartida();
    }

    private void procesarRechazarSolicitud(TipoAccionDTO accion) {
        if (accion.getJugadorDTO() == null) return;
        String nombre = accion.getJugadorDTO().getNombre();
        lobby.rechazarSolicitudLobby(nombre);
        ultimaAccionLobby = "SOLICITUD_RECHAZADA";
        nombreSolicitudResuelta = nombre;
    }

    private void arrancarPartida() {
        dominio.iniciarPartida(new ArrayList<>(lobby.getJugadoresInscritos()), lobby.getConfiguracion());
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
        if (lobby == null) return new ArrayList<>();
        return JugadorMapper.toDTO(lobby.getJugadoresInscritos(), -1);
    }

    @Override
    public List<SolicitudUnionDTO> getSolicitudesPendientes() {
        if (lobby == null) return new ArrayList<>();
        return lobby.getSolicitudesPendientesLobby().stream()
                .map(j -> new SolicitudUnionDTO(JugadorMapper.toDTO(j, false), "PENDIENTE"))
                .collect(Collectors.toList());
    }

    @Override
    public String getUltimaAccionLobby() { return ultimaAccionLobby; }

    @Override
    public String getNombreSolicitudResuelta() { return nombreSolicitudResuelta; }

    @Override
    public String getHostNombre() {
        return lobby != null ? lobby.getHostNombre() : null;
    }

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
    public List<String> getNombresConectados() {
        return new ArrayList<>(ipsPorNombre.keySet());
    }

    @Override
    public EstadoPartidaDTO getEstadoParaJugador(String nombre) {
        if (partidaIniciada()) {
            List<Jugador> jugadoresJuego = dominio.getJugadores();
            int idx = -1;
            for (int i = 0; i < jugadoresJuego.size(); i++) {
                if (jugadoresJuego.get(i).getNombre().equals(nombre)) { idx = i; break; }
            }
            if (idx < 0) return null;
            return buildEstadoPartida(idx);
        }

        if (ultimaAccionLobby == null) return null;

        List<JugadorDTO> jugadores = getJugadores();
        List<SolicitudUnionDTO> solicitudesPend = getSolicitudesPendientes();
        boolean esHost = nombre.equals(getHostNombre());
        boolean esSolicitudResuelta = nombre.equals(nombreSolicitudResuelta);
        boolean estaEnSala = lobby != null && lobby.getJugadoresInscritos().stream()
                .anyMatch(j -> j.getNombre().equals(nombre));

        switch (ultimaAccionLobby) {
            case "HOST_UNIDO" -> {
                if (!esHost) return null;
                EstadoPartidaDTO dto = new EstadoPartidaDTO();
                dto.setEstadoPartida("NO_INICIADA");
                dto.setJugadores(jugadores);
                return dto;
            }
            case "SOLICITUD_NUEVA" -> {
                if (!esHost) return null;
                EstadoPartidaDTO dto = new EstadoPartidaDTO();
                dto.setEstadoPartida("NO_INICIADA");
                dto.setJugadores(jugadores);
                dto.setSolicitudesPendientes(solicitudesPend);
                return dto;
            }
            case "SOLICITUD_ACEPTADA" -> {
                if (esSolicitudResuelta) {
                    EstadoPartidaDTO dto = new EstadoPartidaDTO();
                    dto.setResultadoSolicitud("ACEPTADA");
                    dto.setJugadores(jugadores);
                    return dto;
                }
                if (esHost) {
                    EstadoPartidaDTO dto = new EstadoPartidaDTO();
                    dto.setEstadoPartida("NO_INICIADA");
                    dto.setJugadores(jugadores);
                    dto.setSolicitudesPendientes(solicitudesPend);
                    return dto;
                }
                if (estaEnSala) {
                    EstadoPartidaDTO dto = new EstadoPartidaDTO();
                    dto.setEstadoPartida("NO_INICIADA");
                    dto.setJugadores(jugadores);
                    return dto;
                }
                return null;
            }
            case "SOLICITUD_RECHAZADA" -> {
                if (esSolicitudResuelta) {
                    EstadoPartidaDTO dto = new EstadoPartidaDTO();
                    dto.setResultadoSolicitud("RECHAZADA");
                    return dto;
                }
                if (esHost) {
                    EstadoPartidaDTO dto = new EstadoPartidaDTO();
                    dto.setEstadoPartida("NO_INICIADA");
                    dto.setJugadores(jugadores);
                    dto.setSolicitudesPendientes(solicitudesPend);
                    return dto;
                }
                return null;
            }
        }
        return null;
    }

    private EstadoPartidaDTO buildEstadoPartida(int indiceJugador) {
        TipoEventoRuletaDTO eventoRuleta = "GIRO_PENDIENTE".equals(getEstadoPartida())
                ? getEventoRuleta() : null;
        return new EstadoPartidaDTO(
                getIndiceJugadorActual(),
                getEstadoPartida(),
                getCartaCima(),
                getJugadores(),
                getManoJugador(indiceJugador),
                getIndiceJugadorActual() == indiceJugador,
                eventoRuleta,
                isUltimaJugadaValida());
    }
}
