package mvc.interfaces;
import dto.CartaDTO;

/**
 * The interface Modelo controlador.
 */
public interface IModeloControlador {

    /**
     * Carta seleccionada.
     *
     * @param cartaDTO the carta dto
     */
    void cartaSeleccionada(CartaDTO cartaDTO);

    /**
     * Jugar carta boolean.
     *
     * @param carta the carta
     * @return the boolean
     */
    boolean jugarCarta(CartaDTO carta);
}
