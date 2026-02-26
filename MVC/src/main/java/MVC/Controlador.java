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

    public void onCartaJugada(String valorCarta) {
        System.out.println("Carta jugada: " + valorCarta);
    }

    public void onPedirCarta() {
        System.out.println("Pedir carta del mazo");
    }

    public void onUnoGritado() {
        System.out.println("¡UNO!");
    }

    public void onSpinCompletado() {
        System.out.println("Spin completado");
    }
}
