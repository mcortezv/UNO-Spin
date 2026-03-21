package mvc.interfaces;

import dto.CartaDTO;
import dominio.enums.TipoEventoRuleta;

public interface IControlador {
    void jugarCarta(CartaDTO carta);
    void onCartaJugada(String valorCarta);
    void onPedirCarta();
    void onUnoGritado();
    void onSpinCompletado();
    void onResultadoEvento(TipoEventoRuleta evento, Object resultado);
    void onReconocerEvento();
    void aplicarEventoRuleta(TipoEventoRuleta evento, Object resultado);
    void avanzarTurno();
    void onSeleccionColor(String color);
}