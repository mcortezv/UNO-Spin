package dominio;

import dominio.enums.EstadoPartida;
import dominio.enums.TipoEventoRuleta;
import dominio.interfaces.IDominio;
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
    private boolean unoGritado = false;

    /**
     * Instantiates a new Partida.
     */
    public Partida() {
    }

    /**
     * Instantiates a new Partida.
     *
     * @param estadoPartida       the estado partida
     * @param indiceJugadorActual the indice jugador actual
     * @param jugadores           the jugadores
     * @param sentidoHorario      the sentido horario
     */
    public Partida(EstadoPartida estadoPartida, int indiceJugadorActual, List<Jugador> jugadores,
            boolean sentidoHorario) {
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

    public boolean aplicarJugada(Carta c) {
        if (tablero.getDescarte().validarCartaEntrante(c)) {
            Jugador jugadorActual = jugadores.get(indiceJugadorActual);
            List<Carta> cartasMano = jugadorActual.getMano().getCartas();

            for (int i = 0; i < cartasMano.size(); i++) {
                Carta cartaEnMano = cartasMano.get(i);
                if (cartaEnMano.getColor().equals(c.getColor()) && cartaEnMano.getValor() == c.getValor()) {
                    cartasMano.remove(i);
                    break;
                }
            }
            tablero.getDescarte().getCartas().add(c);

            if (c.getTipoCarta() == dominio.enums.TipoCarta.NUMERO_SPIN) {
                this.estadoPartida = EstadoPartida.GIRO_PENDIENTE;
            } else if (c.getTipoCarta() == dominio.enums.TipoCarta.REVERSA) {
                this.sentidoHorario = !this.sentidoHorario;
                avanzarTurno();
            } else if (c.getTipoCarta() == dominio.enums.TipoCarta.BLOQUEO) {
                avanzarTurno();
                avanzarTurno();
            } else {
                avanzarTurno();
            }
            return true;
        }
        return false;
    }

    @Override
    public void gritarUno() {
        this.unoGritado = true;
    }

    @Override
    public boolean validarJugada(Carta carta) {
        return tablero.getDescarte().validarCartaEntrante(carta);
    }

    /**
     * Procesar giro ruleta tipo evento ruleta.
     *
     * @return the tipo evento ruleta
     * @throws Exception the exception
     */
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
                robarHastaColor(jugador, "AZUL");
                break;
            case ROBAR_HASTA_ROJO:
                robarHastaColor(jugador, "ROJO");
                break;
            case INTERCAMBIO_DE_MANOS:
                intercambiarManos();
                break;
            case GUERRA:
                aplicarGuerra();
                break;
            case MOSTRAR_LA_MANO:
                break;
            case PUNTUACION_MAS_BAJA:
                aplicarPuntuacionMasBaja();
                break;
            case DESCARTAR_POR_COLOR:
                aplicarDescartarPorColor(jugador);
                break;
            case ELEGIR_COLOR:
            case DESCARTAR_POR_NUMERO:
            case DESCARTAR_CARTA:
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
            jugadores.getFirst().setMano(manoTemporal);
        } else {
            Mano manoTemporal = jugadores.getFirst().getMano();
            for (int i = 0; i < cantidadJugadores - 1; i++) {
                jugadores.get(i).setMano(jugadores.get(i + 1).getMano());
            }
            jugadores.get(cantidadJugadores - 1).setMano(manoTemporal);
        }
    }

    private void aplicarGuerra() {
        int numeroGanador = -1;

        for (Jugador jugador : jugadores) {
            Carta cartaMasAlta = jugador.getMano().getHighest();
            if (cartaMasAlta != null && cartaMasAlta.getNumero() > numeroGanador) {
                numeroGanador = cartaMasAlta.getNumero();
            }
        }

        if (numeroGanador != -1) {
            for (Jugador jugador : jugadores) {
                Carta carta = jugador.getMano().getHighest();
                if (carta != null && carta.getNumero() == numeroGanador) {
                    List<Carta> cartasDescartadas = jugador.getMano().descartarCartasPorNumero(numeroGanador);
                    tablero.getDescarte().getCartas().addAll(cartasDescartadas);
                }
            }
        }
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    /**
     * Avanzar turno.
     */
    public void avanzarTurno() {
        if (!jugadores.isEmpty()) {
            int n = jugadores.size();
            if (sentidoHorario) {
                indiceJugadorActual = (indiceJugadorActual + 1) % n;
            } else {
                indiceJugadorActual = (indiceJugadorActual - 1 + n) % n;
            }
        }
    }

    private void aplicarDescartarPorColor(Jugador jugador) {
        Carta cartaTope = tablero.getDescarte().getUltimaCarta();
        if (cartaTope == null)
            return;
        String colorTope = cartaTope.getColor();
        if (colorTope == null || colorTope.equalsIgnoreCase("NEGRO")) {
            return;
        }
        List<Carta> mano = jugador.getMano().getCartas();
        List<Carta> cartasADescartar = new java.util.ArrayList<>();
        for (Carta c : mano) {
            if (c.getColor() != null && c.getColor().equalsIgnoreCase(colorTope)) {
                cartasADescartar.add(c);
            }
        }
        mano.removeAll(cartasADescartar);
        tablero.getDescarte().getCartas().addAll(cartasADescartar);
    }

    private void aplicarPuntuacionMasBaja() {
        if (jugadores.isEmpty())
            return;
        Jugador jugadorMenosCartas = jugadores.get(0);
        for (Jugador j : jugadores) {
            if (j.getMano().getCartas().size() < jugadorMenosCartas.getMano().getCartas().size()) {
                jugadorMenosCartas = j;
            }
        }
        try {
            Carta cartaCastigo = tablero.getMazo().robarCarta();
            jugadorMenosCartas.getMano().getCartas().add(cartaCastigo);
        } catch (Exception e) {
            System.out.println("El mazo se quedó sin cartas durante el evento Puntuación Más Baja.");
        }
    }

    public void robarCartaJugadorActual() {
        try {
            Carta cartaRobada = tablero.getMazo().robarCarta();
            Jugador jugadorActual = jugadores.get(indiceJugadorActual);
            jugadorActual.getMano().getCartas().add(cartaRobada);
            avanzarTurno();
        } catch (Exception e) {
            System.out.println("No hay más cartas en el mazo: " + e.getMessage());
        }
    }

    public void iniciarPartida(List<Jugador> jugadoresIniciales, Tablero tableroInicial) {
        this.jugadores = jugadoresIniciales;
        this.tablero = tableroInicial;
        this.estadoPartida = EstadoPartida.EN_PROCESO;
        this.indiceJugadorActual = 0;
        this.sentidoHorario = true;
    }
}
