package mvc.interfaces;
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
     * Seleccionar.
     *
     * @param carta the carta
     */
    void seleccionar(CartaDTO carta);

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
}
