package MVC;
import MVC.interfaces.IControlador;
import dto.CartaDTO;
import MVC.interfaces.IModeloControlador;

public class Controlador implements IControlador {
    IModeloControlador modelo;

    public Controlador(IModeloControlador modelo) {
        this.modelo = modelo;
    }

    public void jugarCarta(CartaDTO carta) {
        modelo.jugarCarta(carta);
    }

    public void seleccionar(CartaDTO carta) {
        modelo.cartaSeleccionada(carta);
    }
}
