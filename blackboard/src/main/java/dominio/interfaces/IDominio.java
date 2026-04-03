package dominio.interfaces;
import dominio.Carta;
import dominio.Jugador;
import dominio.Tablero;
import dominio.enums.EstadoPartida;
import dominio.enums.TipoEventoRuleta;
import dto.CartaDTO;
import dto.JugadorDTO;

import java.util.List;

/**
 * The interface Dominio.
 */
public interface IDominio {

    /**
     * Validar jugada boolean.
     *
     * @param carta the carta
     * @return the boolean
     */
    boolean validarJugada(Carta carta);

    /**
     * Aplicar jugada boolean.
     *
     * @param c the c
     * @return the boolean
     */
    boolean aplicarJugada(Carta c);

    /**
     * Robar carta jugador actual.
     */
    void robarCartaJugadorActual();

    /**
     * Procesar giro ruleta tipo evento ruleta.
     *
     * @return the tipo evento ruleta
     * @throws Exception the exception
     */
    TipoEventoRuleta procesarGiroRuleta() throws Exception;

    /**
     * Iniciar partida.
     *
     * @param jugadoresIniciales the jugadores iniciales
     * @param tableroInicial     the tablero inicial
     */
    void iniciarPartida(List<Jugador> jugadoresIniciales, Tablero tableroInicial);

    /**
     * Gets tablero.
     *
     * @return the tablero
     */
    Tablero getTablero();

    /**
     * Gets jugadores.
     *
     * @return the jugadores
     */
    List<JugadorDTO> getJugadores();

    /**
     * Gets indice jugador actual.
     *
     * @return the indice jugador actual
     */
    int getIndiceJugadorActual();

    /**
     * Gets estado partida.
     *
     * @return the estado partida
     */
    EstadoPartida getEstadoPartida();

    /**
     * Gritar uno.
     */
    void gritarUno();

    CartaDTO getCartaCima();

    List<CartaDTO> getManoJugador(int indice);
}
