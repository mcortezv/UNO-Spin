package mvc.interfaces;
import dto.CartaDTO;

public interface IModeloControlador {

    void cartaSeleccionada(CartaDTO cartaDTO);

    boolean jugarCarta(CartaDTO carta);
}
