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
import dto.JugadorDTO;
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
    private ConfiguracionPartida configuracion;

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
            case CREAR_PARTIDA    -> procesarCrearPartida(accion);
            case UNIRSE_PARTIDA   -> procesarUnirse(accion);
            case CONFIRMAR_INICIO -> procesarConfirmacion(accion);
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
        if (dominio == null || dominio.getEstadoPartida() != EstadoPartida.NO_INICIADA) return;
        if (jugadoresInscritos.size() >= MAX_JUGADORES) return;

        Jugador jugador = JugadorMapper.toEntity(accion.getJugadorDTO());
        jugadoresInscritos.add(jugador);
        ipsPorNombre.put(jugador.getNombre(), accion.getIp());
        puertosPorNombre.put(jugador.getNombre(), accion.getPuerto());

        if (jugadoresInscritos.size() == 1 && accion.getConfiguracion() != null) {
            this.configuracion = ConfiguracionPartidaMapper.toEntity(accion.getConfiguracion());
        }

        if (jugadoresInscritos.size() == MAX_JUGADORES) {
            arrancarPartida();
        }
    }

    private void procesarConfirmacion(TipoAccionDTO accion) {
        if (dominio == null || dominio.getEstadoPartida() != EstadoPartida.NO_INICIADA) return;
        if (accion.getJugadorDTO() == null) return;

        confirmaciones.add(accion.getJugadorDTO().getNombre());

        boolean todosConfirmaron = jugadoresInscritos.stream()
                .allMatch(j -> confirmaciones.contains(j.getNombre()));
        if (todosConfirmaron && jugadoresInscritos.size() >= MIN_JUGADORES) {
            arrancarPartida();
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
        return JugadorMapper.toDTO(dominio.getJugadores(), dominio.getIndiceJugadorActual());
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
}
