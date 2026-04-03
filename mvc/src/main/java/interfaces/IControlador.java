package interfaces;
import dominio.entidades.enums.TipoEventoRuleta;
import dto.CartaDTO;

/**
 * The interface Controlador.
 */
public interface IControlador {
    /**
     * Jugar carta.
     *
     * @param carta the carta
     */
    void jugarCarta(CartaDTO carta);

    /**
     * On carta jugada.
     *
     * @param valorCarta the valor carta
     */
    void onCartaJugada(String valorCarta);

    /**
     * On pedir carta.
     */
    void onPedirCarta();

    /**
     * On uno gritado.
     */
    void onUnoGritado();

    /**
     * On spin completado.
     */
    void onSpinCompletado();

    /**
     * On resultado evento.
     *
     * @param evento    the evento
     * @param resultado the resultado
     */
    void onResultadoEvento(TipoEventoRuleta evento, Object resultado);

    /**
     * On reconocer evento.
     */
    void onReconocerEvento();

    /**
     * Aplicar evento ruleta.
     *
     * @param evento    the evento
     * @param resultado the resultado
     */
    void aplicarEventoRuleta(TipoEventoRuleta evento, Object resultado);

    /**
     * Avanzar turno.
     */
    void avanzarTurno();

    /**
     * On seleccion color.
     *
     * @param color the color
     */
    void onSeleccionColor(String color);
}
