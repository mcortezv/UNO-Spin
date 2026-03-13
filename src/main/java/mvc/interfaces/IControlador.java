package mvc.interfaces;
import dto.CartaDTO;
import dominio.enums.TipoEventoRuleta; // ← AGREGADO

/**
 * The interface Controlador.
 */
public interface IControlador {

    boolean jugarCarta(CartaDTO carta);

    void onCartaJugada(String valorCarta);

    void onPedirCarta();

    void onUnoGritado();

    void onSpinCompletado();

    void onResultadoEvento(TipoEventoRuleta evento, Object resultado);

    void onReconocerEvento();
}