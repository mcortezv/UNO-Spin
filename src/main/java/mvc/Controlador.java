package mvc;
import dominio.enums.TipoEventoRuleta;
import mvc.interfaces.IControlador;
import dto.CartaDTO;
import mvc.interfaces.IModeloControlador;

/**
 * The type Controlador.
 */
public class Controlador implements IControlador {
    /**
     * The Modelo.
     */
    IModeloControlador modelo;

    /**
     * Instantiates a new Controlador.
     *
     * @param modelo the modelo
     */
    public Controlador(IModeloControlador modelo) {
        this.modelo = modelo;
    }

    @Override
    public boolean jugarCarta(CartaDTO carta) {
        return modelo.jugarCarta(carta);
    }

    /**
     * On carta jugada.
     *
     * @param valorCarta the valor carta
     */
    public void onCartaJugada(String valorCarta) {
        System.out.println("Carta jugada: " + valorCarta);
    }

    /**
     * On pedir carta.
     */
    public void onPedirCarta() {
        System.out.println("Pedir carta del mazo");
        modelo.pedirCarta();
    }

    /**
     * On uno gritado.
     */
    public void onUnoGritado() {
        modelo.gritarUno();
    }

    /**
     * On spin completado.
     */
    public void onSpinCompletado() {
        System.out.println("Spin completado");
        modelo.girarRuleta();
    }

    public void onResultadoEvento(TipoEventoRuleta evento, Object resultado) {
        System.out.println("Evento: " + evento + " | Resultado: " + resultado);
        modelo.limpiarEventoRuleta();
    }
}
