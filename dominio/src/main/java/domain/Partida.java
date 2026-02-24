package domain;
import enums.EstadoPartida;
import enums.TipoEventoRuleta;
import interfaces.IDominio;

import java.util.List;

/**
 * The type Partida.
 */
public class Partida implements IDominio {
    private List<Jugador> jugadores;
    private EstadoPartida estadoPartida;
    private int indiceJugadorActual;
    private boolean sentidoHorario;
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




    @Override
    public boolean validarJugada(Carta carta) {
        return false;
    }

    public TipoEventoRuleta procesarGiroRuleta() throws Exception {
        if (this.estadoPartida != EstadoPartida.GIRO_PENDIENTE) {
            throw new Exception("No es momento de girar la ruleta.");
        }

        TipoEventoRuleta evento = tablero.getRuleta().girar();
        Jugador jugador = jugadores.get(indiceJugadorActual);

        switch (evento) {
            case CASI_UNO:
                aplicarCasiUno(jugador);
                break;
            case ROBAR_HASTA_AZUL:
                robarHastaColor(jugador, "Azul");
                break;
            case ROBAR_HASTA_ROJO:
                robarHastaColor(jugador, "Rojo");
                break;
            case INTERCAMBIO_DE_MANOS:
                intercambiarManos();
                break;
            case GUERRA:
                this.estadoPartida = EstadoPartida.EN_PROCESO;
                break;
            case MOSTRAR_LA_MANO:
            case PUNTUACION_MAS_BAJA:
            case DESCARTAR_POR_COLOR:
                break;
        }
        if (evento != TipoEventoRuleta.GUERRA && evento != TipoEventoRuleta.DESCARTAR_POR_COLOR) {
            this.estadoPartida = EstadoPartida.EN_PROCESO;
            avanzarTurno();
        }

        return evento;
    }

    private void aplicarCasiUno(Jugador jugador) {
        while (jugador.getMano().getCartas().size() > 2) {
            Carta cartaEliminada = jugador.getMano().getCartas().remove(0); // Quita la primera
            tablero.getDescarte().getCartas().add(cartaEliminada); // La pone en el descarte
        }
    }

    private void robarHastaColor(Jugador jugador, String colorObjetivo) throws Exception {
        boolean encontroColor = false;
        while (!encontroColor) {
            Carta cartaRobada = tablero.getMazo().robarCarta();
            jugador.getMano().getCartas().add(cartaRobada);
            if (cartaRobada.getColor() != null && cartaRobada.getColor().equalsIgnoreCase(colorObjetivo)) {
                encontroColor = true;
            }
        }
    }

    private void intercambiarManos() {
        int cantidadJugadores = jugadores.size();

        if (this.sentidoHorario) {
            Mano manoTemporal = jugadores.get(cantidadJugadores - 1).getMano();
            for (int i = cantidadJugadores - 1; i > 0; i--) {
                jugadores.get(i).setMano(jugadores.get(i - 1).getMano());
            }
            jugadores.get(0).setMano(manoTemporal);
        } else {
            Mano manoTemporal = jugadores.get(0).getMano();
            for (int i = 0; i < cantidadJugadores - 1; i++) {
                jugadores.get(i).setMano(jugadores.get(i + 1).getMano());
            }
            jugadores.get(cantidadJugadores - 1).setMano(manoTemporal);
        }
    }
}
