package interfaces;

import dominio.enums.TipoEventoRuleta;
import dto.CartaDTO;

public interface IModeloControlador {
    void jugarCarta(CartaDTO carta);
    void pedirCarta();
    void girarRuleta();
    void gritarUno();
    void limpiarEventoRuleta();
    void reconocerEvento(int indiceJugador);
    void avanzarPasoEvento();
    void aplicarEventoRuleta(TipoEventoRuleta evento, Object resultado);
    void aplicarSeleccionColor(String color);
}