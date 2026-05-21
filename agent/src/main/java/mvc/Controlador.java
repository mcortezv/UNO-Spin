package mvc;
import dto.CartaDTO;
import dto.EventoRuletaDTO;
import interfaces.IModeloControlador;

/**
 * The type Controlador.
 */
public class Controlador{

    private final IModeloControlador modelo;

    /**
     * Instantiates a new Controlador.
     *
     * @param modelo the modelo
     */
    public Controlador(IModeloControlador modelo) {
        this.modelo = modelo;
    }

    public void jugarCarta(CartaDTO carta) {
        modelo.jugarCarta(carta);
    }

    public void onCartaJugada(String valorCarta) {
        System.out.println("Carta jugada: " + valorCarta);
    }

    public void onPedirCarta() {
        System.out.println("Pedir carta del mazo");
        modelo.pedirCarta();
    }

    public void onUnoGritado() {
        modelo.gritarUno();
    }

    public void onSpinCompletado() {
        System.out.println("Spin completado");
        modelo.girarRuleta();
    }

    public void onResultadoEvento(EventoRuletaDTO evento, Object resultado) {
        System.out.println("Enviando evento al modelo: " + evento + " | Resultado: " + resultado);
        modelo.aplicarEventoRuleta(evento, resultado);
    }

    public void onReconocerEvento() {
        modelo.limpiarEventoRuleta();
    }

    public void aplicarEventoRuleta(EventoRuletaDTO evento, Object resultado) {
        modelo.aplicarEventoRuleta(evento, resultado);
    }

    public void avanzarTurno() { }

    public void onSeleccionColor(String color) {
        modelo.aplicarSeleccionColor(color);
    }
}