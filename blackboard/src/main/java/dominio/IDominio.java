package dominio;
import dominio.entidades.Carta;
import dominio.entidades.ConfiguracionPartida;
import dominio.entidades.Jugador;
import dominio.entidades.enums.EstadoPartida;
import dto.TipoEventoRuletaDTO;
import java.util.List;

/**
 * The interface Dominio.
 */
public interface IDominio {

    /**
     * Iniciar partida.
     *
     * @param jugadoresIniciales the jugadores iniciales
     * @param configuracion      the configuracion
     */
    void iniciarPartida(List<Jugador> jugadoresIniciales, ConfiguracionPartida configuracion);

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
     * @param carta the carta
     * @return the boolean
     */
    boolean aplicarJugada(Carta carta);

    /**
     * Robar carta jugador actual.
     */
    void robarCartaJugadorActual();

    /**
     * Robar carta jugador actual sin avanzar turno.
     */
    void robarCartaSinAvanzarTurno();

    /**
     * Aplicar seleccion color.
     *
     * @param color the color
     */
    void aplicarSeleccionColor(String color);

    /**
     * Gritar uno.
     */
    void gritarUno();

    /**
     * Aplicar castigo uno.
     *
     * @param indiceJugador the indice jugador
     */
    void aplicarCastigoUno(int indiceJugador);

    /**
     * Procesar giro ruleta tipo evento ruleta.
     *
     * @return the tipo evento ruleta
     * @throws Exception the exception
     */
    TipoEventoRuletaDTO procesarGiroRuleta() throws Exception;

    /**
     * Aplicar efecto ruleta.
     *
     * @param evento    the evento
     * @param resultado the resultado
     */
    void aplicarEfectoRuleta(TipoEventoRuletaDTO evento, Object resultado);

    /**
     * Avanzar turno.
     */
    void avanzarTurno();

    /**
     * Gets estado partida.
     *
     * @return the estado partida
     */
    EstadoPartida getEstadoPartida();

    /**
     * Gets indice jugador actual.
     *
     * @return the indice jugador actual
     */
    int getIndiceJugadorActual();

    /**
     * Gets jugadores.
     *
     * @return the jugadores
     */
    List<Jugador> getJugadores();

    /**
     * Gets cantidad cartas jugador.
     *
     * @param indiceJugador the indice jugador
     * @return the cantidad cartas jugador
     */
    int getCantidadCartasJugador(int indiceJugador);

    /**
     * Gets mano jugador.
     *
     * @param indiceJugador the indice jugador
     * @return the mano jugador
     */
    List<Carta> getManoJugador(int indiceJugador);

    /**
     * Gets carta cima.
     *
     * @return the carta cima
     */
    Carta getCartaCima();

    /**
     * Gets cartas descarte.
     *
     * @return the cartas descarte
     */
    List<Carta> getCartasDescarte();

    /**
     * Is ultima jugada valida boolean.
     *
     * @return the boolean
     */
    boolean isUltimaJugadaValida();

    /**
     * Gets evento ruleta.
     *
     * @return the evento ruleta
     */
    TipoEventoRuletaDTO getEventoRuleta();

    void setsConfiguracion(ConfiguracionPartida configuracion);

}
