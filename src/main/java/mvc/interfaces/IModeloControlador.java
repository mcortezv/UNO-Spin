package mvc.interfaces;
import dto.CartaDTO;

/**
 * The interface Modelo controlador.
 */
public interface IModeloControlador {

    /**
     * Jugar carta boolean.
     *
     * @param carta the carta
     * @return the boolean
     */
    boolean jugarCarta(CartaDTO carta);

    /**
     * Pedir carta.
     */
    void pedirCarta();

    /**
     * Girar ruleta.
     */
    void girarRuleta();
}
