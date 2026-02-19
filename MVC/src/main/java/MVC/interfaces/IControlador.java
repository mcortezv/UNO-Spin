package MVC.interfaces;
import dto.CartaDTO;

public interface IControlador {

    void jugarCarta(CartaDTO carta);

    void seleccionar(CartaDTO carta);
}
