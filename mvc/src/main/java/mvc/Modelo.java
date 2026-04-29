package mvc;
import dto.*;
import interfaces.*;
import java.util.ArrayList;
import java.util.List;
import static enums.TipoAccion.*;

public class Modelo implements IModeloControlador, IModeloLectura {
    private final List<ISuscriptor> suscriptores = new ArrayList<>();
    private final IDispatcher dispatcher;
    private final ISerializer serializer;
    private final String ipServidor;
    private final int puertoServidor;
    private EstadoPartidaDTO estadoPartida;

    public Modelo(ISerializer serializer, IDispatcher dispatcher, String ipServidor, int puertoServidor) {
        this.serializer = serializer;
        this.dispatcher = dispatcher;
        this.ipServidor = ipServidor;
        this.puertoServidor = puertoServidor;
    }

    public void actualizarEstado(EstadoPartidaDTO estado) {
        this.estadoPartida = estado;
        notifyObservers();
    }

    @Override
    public void unirsePartida(JugadorDTO jugador, ConfiguracionPartidaDTO configuracion) {
        TipoAccionDTO accion = new TipoAccionDTO(UNIRSE_PARTIDA);
        accion.setJugadorDTO(jugador);
        accion.setConfiguracion(configuracion);
        enviar(accion);
    }

    @Override
    public void confirmarInicio(JugadorDTO jugador) {
        TipoAccionDTO accion = new TipoAccionDTO(CONFIRMAR_INICIO);
        accion.setJugadorDTO(jugador);
        enviar(accion);
    }

    @Override
    public void jugarCarta(CartaDTO cartaDTO) {
        enviar(new TipoAccionDTO(JUGAR_CARTA, cartaDTO));
    }

    @Override
    public void pedirCarta() {
        enviar(new TipoAccionDTO(PEDIR_CARTA));
    }

    @Override
    public void girarRuleta() {
        enviar(new TipoAccionDTO(GIRAR_RULETA));
    }

    @Override
    public void gritarUno() {
        enviar(new TipoAccionDTO(GRITAR_UNO));
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
        enviar(new TipoAccionDTO(RECONOCER_EVENTO));
        limpiarEventoRuleta();
    }

    @Override
    public void aplicarSeleccionColor(String color) {
        CartaDTO payload = new CartaDTO();
        payload.setColor(color);
        enviar(new TipoAccionDTO(SELECCIONAR_COLOR, payload));
    }

    @Override
    public void aplicarEventoRuleta(String evento, Object resultado) {
        notifyObservers();
    }

    @Override
    public boolean isUltimaJugadaValida() {
        return estadoPartida != null && estadoPartida.isUltimaJugadaValida();
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
        if (estadoPartida == null || estadoPartida.getJugadores() == null) {
            return "Esperando...";
        }
        int indice = estadoPartida.getIndiceJugadorActual();
        if (indice < 0 || indice >= estadoPartida.getJugadores().size()) {
            return "Esperando...";
        }
        return estadoPartida.getJugadores().get(indice).getNombre();
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
        return estadoPartida != null && estadoPartida.isEsTuTurno()
                && "EN_PROCESO".equals(estadoPartida.getEstadoPartida());
    }

    @Override
    public boolean isSpinActivo() {
        return estadoPartida != null
                && "GIRO_PENDIENTE".equals(estadoPartida.getEstadoPartida())
                && getEventoRuletaActual() == null;
    }

    @Override
    public boolean isSeleccionColorPendiente() {
        return estadoPartida != null
                && "SELECCION_COLOR_PENDIENTE".equals(estadoPartida.getEstadoPartida());
    }

    @Override
    public String getEventoRuletaActual() {
        return estadoPartida != null ? estadoPartida.getEventoRuletaActivo() : null;
    }

    public void subscribe(ISuscriptor suscriptor) {
        suscriptores.add(suscriptor);
    }

    public void unsubscribe(ISuscriptor suscriptor) {
        suscriptores.remove(suscriptor);
    }

    private void notifyObservers() {
        for (ISuscriptor s : suscriptores) {
            s.update(this);
        }
    }

    private void enviar(TipoAccionDTO accion) {
        String json = serializer.serialize(accion);
        dispatcher.enviar(json, puertoServidor, ipServidor);
    }
}
