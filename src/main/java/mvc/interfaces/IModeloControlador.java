package mvc.interfaces;
import dto.CartaDTO;
import dto.EventoRuletaDTO;

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
    EventoRuletaDTO girarRuleta();
}
