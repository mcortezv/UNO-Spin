package mvc.interfaces;
import dto.CartaDTO;

/**
 * The interface Modelo controlador.
 */
public interface IModeloControlador {

    boolean jugarCarta(CartaDTO carta);

    void pedirCarta();

    void girarRuleta();

    void gritarUno();

    void limpiarEventoRuleta();
}