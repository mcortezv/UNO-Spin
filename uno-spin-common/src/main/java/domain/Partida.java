package domain;
import enums.EstadoPartida;
import java.util.List;

/**
 * The type Partida.
 */
public class Partida {
    private List<Jugador> jugadores;
    private EstadoPartida estadoPartida;
    private int indiceJugadorActual;
    private boolean sentidoHorario;
    private ConfiguracionPartida configuracionPartida;
    private Tablero tablero;

    /**
     * Instantiates a new Partida.
     */
    public Partida() {}

    /**
     * Instantiates a new Partida.
     *
     * @param estadoPartida       the estado partida
     * @param indiceJugadorActual the indice jugador actual
     * @param jugadores           the jugadores
     * @param sentidoHorario      the sentido horario
     */
    public Partida(EstadoPartida estadoPartida, int indiceJugadorActual, List<Jugador> jugadores, boolean sentidoHorario) {
        this.estadoPartida = estadoPartida;
        this.indiceJugadorActual = indiceJugadorActual;
        this.jugadores = jugadores;
        this.sentidoHorario = sentidoHorario;
    }

    /**
     * Gets estado partida.
     *
     * @return the estado partida
     */
    public EstadoPartida getEstadoPartida() {
        return estadoPartida;
    }

    /**
     * Sets estado partida.
     *
     * @param estadoPartida the estado partida
     */
    public void setEstadoPartida(EstadoPartida estadoPartida) {
        this.estadoPartida = estadoPartida;
    }

    /**
     * Gets indice jugador actual.
     *
     * @return the indice jugador actual
     */
    public int getIndiceJugadorActual() {
        return indiceJugadorActual;
    }

    /**
     * Sets indice jugador actual.
     *
     * @param indiceJugadorActual the indice jugador actual
     */
    public void setIndiceJugadorActual(int indiceJugadorActual) {
        this.indiceJugadorActual = indiceJugadorActual;
    }

    /**
     * Gets jugadores.
     *
     * @return the jugadores
     */
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    /**
     * Sets jugadores.
     *
     * @param jugadores the jugadores
     */
    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    /**
     * Is sentido horario boolean.
     *
     * @return the boolean
     */
    public boolean isSentidoHorario() {
        return sentidoHorario;
    }

    /**
     * Sets sentido horario.
     *
     * @param sentidoHorario the sentido horario
     */
    public void setSentidoHorario(boolean sentidoHorario) {
        this.sentidoHorario = sentidoHorario;
    }

    public ConfiguracionPartida getConfiguracionPartida() {
        return configuracionPartida;
    }

    public void setConfiguracionPartida(ConfiguracionPartida configuracionPartida) {
        this.configuracionPartida = configuracionPartida;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }
}
