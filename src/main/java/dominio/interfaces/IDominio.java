package dominio.interfaces;
import dominio.Carta;
import dominio.Jugador;
import dominio.Tablero;
import dominio.enums.EstadoPartida;
import dominio.enums.TipoEventoRuleta;

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
    boolean aplicarJugada(Carta c);
    void robarCartaJugadorActual();
    TipoEventoRuleta procesarGiroRuleta() throws Exception;
    void iniciarPartida(List<Jugador> jugadoresIniciales, Tablero tableroInicial);
    Tablero getTablero();
    List<Jugador> getJugadores();
    int getIndiceJugadorActual();
    EstadoPartida getEstadoPartida();
    void gritarUno();
    void aplicarEfectoRuleta(TipoEventoRuleta evento, Object resultado);
    void avanzarTurno();
}
