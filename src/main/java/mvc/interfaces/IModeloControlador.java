package mvc.interfaces;

import dto.CartaDTO;
import dto.EventoRuletaDTO;

/**
 * The interface Modelo controlador.
 */
public interface IModeloControlador {

    boolean jugarCarta(CartaDTO carta);

    void pedirCarta();

    EventoRuletaDTO girarRuleta();

    void gritarUno();

    void limpiarEventoRuleta();

    void reconocerEvento(int indiceJugador);

    void avanzarPasoEvento();
}