package mvc;
import dto.*;
import interfaces.*;
import java.util.ArrayList;
import java.util.List;
import static enums.TipoAccion.*;

/**
 * The type Modelo.
 */
public class Modelo implements IModeloControlador, IModeloLectura, IModeloConexion {
    private final List<ISuscriptor> suscriptores = new ArrayList<>();
    private final IDispatcher dispatcher;
    private final ISerializer serializer;
    private int puertoServidor;
    private String ipServidor;
    private EstadoPartidaDTO estadoPartida;
    private boolean ultimaJugadaValida;

    public Modelo(ISerializer serializer, IDispatcher dispatcher) {
        this.serializer = serializer;
        this.dispatcher = dispatcher;
    }

    @Override
    public void jugarCarta(CartaDTO cartaDTO) {
       String json = serializer.serialize(new TipoAccionDTO(JUGAR_CARTA, cartaDTO));
       this.dispatcher.enviar(json, puertoServidor, ipServidor);
    }

    @Override
    public void pedirCarta() {
        String json = serializer.serialize(new TipoAccionDTO(PEDIR_CARTA));
        this.dispatcher.enviar(json, puertoServidor, ipServidor);
    }

    @Override
    public void girarRuleta() {
        String json = serializer.serialize(new TipoAccionDTO(GIRAR_RULETA));
        this.dispatcher.enviar(json, puertoServidor, ipServidor);
    }

    @Override
    public void gritarUno() {
        String json = serializer.serialize(new TipoAccionDTO(GRITAR_UNO));
        this.dispatcher.enviar(json, puertoServidor, ipServidor);
    }

    @Override
    public void limpiarEventoRuleta() {
        if (estadoPartida != null) {
            estadoPartida.setEventoRuletaActivo(null);
        }
        notifyObservers();
    }

    @Override
    public void reconocerEvento(int indiceJugador) {
        String json = serializer.serialize(new TipoAccionDTO(RECONOCER_EVENTO));
        this.dispatcher.enviar(json, puertoServidor, ipServidor);
        limpiarEventoRuleta();
    }

    @Override
    public void aplicarSeleccionColor(String color) {
        String json = serializer.serialize(new TipoAccionDTO(SELECCIONAR_COLOR));
        this.dispatcher.enviar(json, puertoServidor, ipServidor);
    }

    @Override
    public void aplicarEventoRuleta(EventoRuletaDTO evento, Object resultado) {
        notifyObservers();
    }

    @Override
    public boolean isUltimaJugadaValida() {
        if (estadoPartida != null) {
            return estadoPartida.isUltimaJugadaValida();
        }
        return false;
    }

    @Override
    public List<CartaDTO> getDescarte() {
        List<CartaDTO> descarte = new ArrayList<>();
        if (getCartaCima() != null) {
            descarte.add(getCartaCima());
        }
        return descarte;
    }

    @Override
    public List<CartaDTO> getManoJugador() {
        return estadoPartida != null && estadoPartida.getManoJugador() != null
                ? estadoPartida.getManoJugador() : new ArrayList<>();
    }

    @Override
    public CartaDTO getCartaCima() {
        return estadoPartida != null ? estadoPartida.getCartaCima() : null;
    }

    @Override
    public String getNombreTurnoActual() {
        if (estadoPartida != null && estadoPartida.getJugadores() != null) {
            int indice = estadoPartida.getIndiceJugadorActual();
            if (indice >= 0 && indice < estadoPartida.getJugadores().size()) {
                return estadoPartida.getJugadores().get(indice).getNombre();
            }
        }
        return "Esperando...";
    }

    @Override
    public List<JugadorDTO> getJugadoresRivales() {
        return getTodosLosJugadores();
    }

    @Override
    public List<JugadorDTO> getTodosLosJugadores() {
        return estadoPartida != null && estadoPartida.getJugadores() != null
                ? estadoPartida.getJugadores() : new ArrayList<>();
    }

    @Override
    public boolean isTurnoActivo() {
        return estadoPartida != null && estadoPartida.isEsTuTurno() && "EN_PROCESO".equals(estadoPartida.getEstadoPartida());
    }

    @Override
    public boolean isSpinActivo() {
        return estadoPartida != null && "GIRO_PENDIENTE".equals(estadoPartida.getEstadoPartida()) && getEventoRuletaActual() == null;
    }

    @Override
    public boolean isSeleccionColorPendiente() {
        return estadoPartida != null && "SELECCION_COLOR_PENDIENTE".equals(estadoPartida.getEstadoPartida());
    }

    @Override
    public EventoRuletaDTO getEventoRuletaActual() {
        return estadoPartida != null ? estadoPartida.getEventoRuletaActivo() : null;
    }

    @Override
    public List<CartaDTO> getManoJugadorEspecifico(int indiceJugador) {
        if (estadoPartida != null && estadoPartida.getJugadores() != null) {
            if (indiceJugador >= 0 && indiceJugador < estadoPartida.getJugadores().size()) {
                List<CartaDTO> cartasDelRival = estadoPartida.getJugadores().get(indiceJugador).getCartasMano();
                return cartasDelRival != null ? cartasDelRival : new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public boolean isTurnoActivoEspecifico(int indiceJugador) {
        return estadoPartida != null && estadoPartida.getIndiceJugadorActual() == indiceJugador;
    }

    /**
     * Subscribe.
     *
     * @param suscriptor the suscriptor
     */
    public void subscribe(ISuscriptor suscriptor) {
        suscriptores.add(suscriptor);
    }

    /**
     * Unsubscribe.
     *
     * @param suscriptor the suscriptor
     */
    public void unsubscribe(ISuscriptor suscriptor) {
        suscriptores.remove(suscriptor);
    }

    private void notifyObservers() {
        for (ISuscriptor s : suscriptores) {
            s.update(this);
        }
    }

    @Override
    public void enviar(String json, int port, String ip) {
        if (this.dispatcher == null) {
            this.enviar(json, port, ip);
        }
    }
}