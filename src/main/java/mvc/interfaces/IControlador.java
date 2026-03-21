package mvc.interfaces;
import dto.CartaDTO;
import dominio.enums.TipoEventoRuleta;

/**
 * The interface Controlador.
 */
public interface IControlador {

    void jugarCarta(CartaDTO carta);

    void onCartaJugada(String valorCarta);

    void onPedirCarta();

    void onUnoGritado();

    void onSpinCompletado();

    void onResultadoEvento(TipoEventoRuleta evento, Object resultado);

    void onReconocerEvento();

    void aplicarEventoRuleta(dominio.enums.TipoEventoRuleta evento, Object resultado);

    void avanzarTurno();
}