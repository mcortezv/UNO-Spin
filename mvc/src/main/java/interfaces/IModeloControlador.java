package interfaces;
import dto.CartaDTO;
import dto.ConfiguracionPartidaDTO;
import dto.EventoRuletaDTO;
import dto.JugadorDTO;

/**
 * The interface Modelo controlador.
 */
public interface IModeloControlador {

    
    
    void unirsePartida(JugadorDTO jugador, ConfiguracionPartidaDTO configuracion);
    
    void confirmarInicio(JugadorDTO jugador);
    /**
     * Jugar carta.
     *
     * @param carta the carta
     */
    void jugarCarta(CartaDTO carta);

    /**
     * Pedir carta.
     */
    void pedirCarta();

    /**
     * Girar ruleta.
     */
    void girarRuleta();

    /**
     * Gritar uno.
     */
    void gritarUno();

    /**
     * Limpiar evento ruleta.
     */
    void limpiarEventoRuleta();

    /**
     * Reconocer evento.
     *
     * @param indiceJugador the indice jugador
     */
    void reconocerEvento(int indiceJugador);

    /**
     * Aplicar evento ruleta.
     *
     * @param evento    the evento
     * @param resultado the resultado
     */
    void aplicarEventoRuleta(String evento, Object resultado);

    /**
     * Aplicar seleccion color.
     *
     * @param color the color
     */
    void aplicarSeleccionColor(String color);
}