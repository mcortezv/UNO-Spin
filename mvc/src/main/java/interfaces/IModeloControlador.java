package interfaces;
import dto.CartaDTO;
import dto.ConfiguracionPartidaDTO;
import dto.JugadorDTO;

public interface IModeloControlador {

    void unirsePartida(JugadorDTO jugador, ConfiguracionPartidaDTO configuracion);

    void confirmarInicio(JugadorDTO jugador);

    void jugarCarta(CartaDTO carta);

    void pedirCarta();

    void girarRuleta();

    void gritarUno();

    void limpiarEventoRuleta();

    void reconocerEvento(int indiceJugador);

    void aplicarEventoRuleta(String evento, Object resultado);

    void aplicarSeleccionColor(String color);
}
