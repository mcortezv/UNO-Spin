package mvc.interfaces;
import dto.CartaDTO;
import dto.EventoRuletaDTO;

/**
 * The interface Controlador.
 */
public interface IControlador {

    /**
     * Jugar carta.
     *
     * @param carta the carta
     */
    boolean jugarCarta(CartaDTO carta);

    //-----------------------------------------------------------------------------------------------------------------
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
    EventoRuletaDTO onSpinCompletado();
}
