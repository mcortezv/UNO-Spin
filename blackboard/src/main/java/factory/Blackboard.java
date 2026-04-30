package factory;
import dominio.IDominio;
import dominio.entidades.ConfiguracionPartida;
import dominio.entidades.Jugador;
import dominio.entidades.enums.EstadoPartida;
import dominio.entidades.enums.TipoAccion;
import dominio.entidades.enums.TipoEventoRuleta;
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
import java.util.List;
import java.util.Set;

/**
 * The type Blackboard.
 *
 */
public class Blackboard implements IBlackboard, IReceptor{

    private static final int MIN_JUGADORES = 2;
    private static final int MAX_JUGADORES = 4;

    private static Blackboard instance;
    private final List<IReceptor> suscriptores = new ArrayList<>();
    private final IDominio dominio;
    private final ISerializer serializer;
    private final List<Jugador> jugadoresInscritos = new ArrayList<>();
    private final Set<String> confirmaciones = new HashSet<>();
    private ConfiguracionPartida configuracion;

    /**
     * Instantiates a new Blackboard.
     *
     * @param dominio    the dominio
     * @param serializer the serializer
     */
    Blackboard(IDominio dominio, ISerializer serializer) {
        this.dominio = dominio;
        this.serializer = serializer;
        instance = this;
    }

    /**
     * Suscribir.
     *
     * @param receptor the receptor
     */
    public static void suscribir(IReceptor receptor) {
        instance.suscriptores.add(receptor);
    }

    @Override
    public synchronized void recibirMensaje(String json) {
        TipoAccionDTO accion = serializer.deserialize(json, TipoAccionDTO.class);
        TipoAccion tipo = TipoAccion.valueOf(accion.getTipoAccion());
        switch (tipo) {
            case UNIRSE_PARTIDA   -> procesarUnirse(accion);
            case CONFIRMAR_INICIO -> procesarConfirmacion(accion);
            default -> {
                if (dominio.getEstadoPartida() == null || dominio.getEstadoPartida() == EstadoPartida.NO_INICIADA) break;
                switch (tipo) {
                    case JUGAR_CARTA       -> dominio.aplicarJugada(CartaMapper.toEntity(accion.getCartaDTO()));
                    case PEDIR_CARTA       -> dominio.robarCartaJugadorActual();
                    case GRITAR_UNO        -> dominio.gritarUno();
                    case SELECCIONAR_COLOR -> dominio.aplicarSeleccionColor(accion.getCartaDTO().getColor());
                    case GIRAR_RULETA      -> { try { dominio.procesarGiroRuleta(); } catch (Exception e) { System.err.println("Ruleta: " + e.getMessage()); } }
                    case RECONOCER_EVENTO  -> { TipoEventoRuleta ev = dominio.getEventoRuleta(); dominio.aplicarEfectoRuleta(ev, parsearResultado(ev, accion.getResultadoEvento())); dominio.avanzarTurno(); }
                    default                -> {}
                }
            }
        }
        notificar(json);
    }

    private void procesarUnirse(TipoAccionDTO accion) {
        if (dominio.getEstadoPartida() != EstadoPartida.NO_INICIADA) return;
        if (jugadoresInscritos.size() >= MAX_JUGADORES) return;

        Jugador jugador = JugadorMapper.toEntity(accion.getJugadorDTO());
        jugadoresInscritos.add(jugador);

        if (jugadoresInscritos.size() == 1 && accion.getConfiguracion() != null) {
            this.configuracion = ConfiguracionPartidaMapper.toEntity(accion.getConfiguracion());
        }

        if (jugadoresInscritos.size() == MAX_JUGADORES) {
            arrancarPartida();
        }
    }

    private void procesarConfirmacion(TipoAccionDTO accion) {
        if (dominio.getEstadoPartida() != EstadoPartida.NO_INICIADA) return;
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

    private void notificar(String json) {
        for (IReceptor s : suscriptores) {
            s.recibirMensaje(json);
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
        EstadoPartida e = dominio.getEstadoPartida();
        return e == null ? null : e.name();

    }

    @Override
    public int getIndiceJugadorActual() {
        return dominio.getIndiceJugadorActual();
    }

    @Override
    public String getEventoRuleta() {
        if (!partidaIniciada()) return null;
        TipoEventoRuleta e = dominio.getEventoRuleta();
        return e == null ? null : e.name();
    }

    @Override
    public boolean isUltimaJugadaValida() {
        return dominio.isUltimaJugadaValida();
    }

    private boolean partidaIniciada() {
        EstadoPartida e = dominio.getEstadoPartida();
        return e != null && e != EstadoPartida.NO_INICIADA;
    }

    private Object parsearResultado(TipoEventoRuleta evento, String raw) {
        if (raw == null || evento == null) return null;
        if (evento == TipoEventoRuleta.DESCARTAR_POR_NUMERO) {
            try { return Integer.parseInt(raw); } catch (NumberFormatException e) { return null; }
        }
        return raw;
    }
}
