package interfaces;
import dto.CartaDTO;
import dto.ConfiguracionPartidaDTO;
import dto.JugadorDTO;

/**
 * The interface Modelo controlador.
 */
public interface IModeloControlador {

    /**
     * Unirse partida.
     *
     * @param jugador       the jugador
     * @param configuracion the configuracion
     */
    void unirsePartida(JugadorDTO jugador, ConfiguracionPartidaDTO configuracion);

    /**
     * Confirmar inicio.
     *
     * @param jugador the jugador
     */
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

    /**
     * Sets vista activa.
     *
     * @param vistaActiva the vista activa
     */
    void setVistaActiva(boolean vistaActiva);

    /**
     * Solicitar abandono.
     */
    void solicitarAbandono();

    /**
     * Abandonar partida.
     */
    void abandonarPartida();
}
