package mvc;
import dto.CartaDTO;
import dto.ConfiguracionPartidaDTO;
import dto.JugadorDTO;
import interfaces.IModeloControlador;

/**
 * The type Controlador.
 */
public class Controlador {

    private final IModeloControlador modelo;

    /**
     * Instantiates a new Controlador.
     *
     * @param modelo the modelo
     */
    public Controlador(IModeloControlador modelo) {
        this.modelo = modelo;
    }

    /**
     * Unirse partida.
     *
     * @param jugador       the jugador
     * @param configuracion the configuracion
     */
    public void unirsePartida(JugadorDTO jugador, ConfiguracionPartidaDTO configuracion) {
        modelo.unirsePartida(jugador, configuracion);
    }

    /**
     * Confirmar inicio.
     *
     * @param jugador the jugador
     */
    public void confirmarInicio(JugadorDTO jugador) {
        modelo.confirmarInicio(jugador);
    }

    /**
     * Jugar carta.
     *
     * @param carta the carta
     */
    public void jugarCarta(CartaDTO carta) {
        modelo.jugarCarta(carta);
    }

    /**
     * On pedir carta.
     */
    public void onPedirCarta() {
        modelo.pedirCarta();
    }

    /**
     * On uno gritado.
     */
    public void onUnoGritado() {
        modelo.gritarUno();
    }

    /**
     * On spin completado.
     */
    public void onSpinCompletado() {
        modelo.girarRuleta();
    }

    /**
     * On resultado evento.
     *
     * @param evento    the evento
     * @param resultado the resultado
     */
    public void onResultadoEvento(String evento, Object resultado) {
        modelo.aplicarEventoRuleta(evento, resultado);
    }

    /**
     * On reconocer evento.
     */
    public void onReconocerEvento() {
        modelo.limpiarEventoRuleta();
    }

    /**
     * Aplicar evento ruleta.
     *
     * @param evento    the evento
     * @param resultado the resultado
     */
    public void aplicarEventoRuleta(String evento, Object resultado) {
        modelo.aplicarEventoRuleta(evento, resultado);
    }

    /**
     * On seleccion color.
     *
     * @param color the color
     */
    public void onSeleccionColor(String color) {
        modelo.aplicarSeleccionColor(color);
    }

    /**
     * Solicitar abandono.
     */
    public void solicitarAbandono() {
        modelo.solicitarAbandono();
    }

    /**
     * Abandonar partida.
     */
    public void abandonarPartida() {
        modelo.abandonarPartida();
    }

    /**
     * On solicitar terminar partida.
     */
    public void onSolicitarTerminar() {
        modelo.solicitarTerminarPartida();
    }

    /**
     * On enviar voto.
     *
     * @param acepta the desicion
     */
    public void onVotoTerminar(boolean acepta) {
        modelo.enviarVotoTerminar(acepta);
    }
}
