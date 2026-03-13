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

    public Partida() {
    }

    public Partida(EstadoPartida estadoPartida, int indiceJugadorActual, List<Jugador> jugadores,
                   boolean sentidoHorario) {
        this.estadoPartida = estadoPartida;
        this.indiceJugadorActual = indiceJugadorActual;
        this.jugadores = jugadores;
        this.sentidoHorario = sentidoHorario;
    }

    public EstadoPartida getEstadoPartida() { return estadoPartida; }
    public void setEstadoPartida(EstadoPartida estadoPartida) { this.estadoPartida = estadoPartida; }

    public int getIndiceJugadorActual() { return indiceJugadorActual; }
    public void setIndiceJugadorActual(int indiceJugadorActual) { this.indiceJugadorActual = indiceJugadorActual; }

    public List<Jugador> getJugadores() { return jugadores; }
    public void setJugadores(List<Jugador> jugadores) { this.jugadores = jugadores; }

    public boolean isSentidoHorario() { return sentidoHorario; }
    public void setSentidoHorario(boolean sentidoHorario) { this.sentidoHorario = sentidoHorario; }

    public Tablero getTablero() { return tablero; }
    public void setTablero(Tablero tablero) { this.tablero = tablero; }

    public void iniciarPartida(List<Jugador> jugadoresIniciales, Tablero tableroInicial) {
        this.jugadores = jugadoresIniciales;
        this.tablero = tableroInicial;
        this.estadoPartida = EstadoPartida.EN_PROCESO;
        this.indiceJugadorActual = 0;
        this.sentidoHorario = true;
    }

    @Override
    public boolean validarJugada(Carta carta) {
        return tablero.getDescarte().validarCartaEntrante(carta);
    }

    @Override
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


    @Override
    public TipoEventoRuleta procesarGiroRuleta() throws Exception {
        if (this.estadoPartida != EstadoPartida.GIRO_PENDIENTE) {
            throw new Exception("No es momento de girar la ruleta.");
        }
        return tablero.getRuleta().girar();
    }

    @Override
    public void aplicarEfectoRuleta(TipoEventoRuleta evento, Object resultado) {
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);

        switch (evento) {
            case DESCARTAR_CARTA:
                if (resultado instanceof Integer) {
                    int idx = (Integer) resultado;
                    if (idx >= 0 && idx < jugadorActual.getMano().getCartas().size()) {
                        Carta cartaTirada = jugadorActual.getMano().getCartas().remove(idx);
                        tablero.getDescarte().getCartas().add(cartaTirada);
                    }
                }
                break;

            case DESCARTAR_POR_COLOR:
                if (resultado instanceof String) {
                    aplicarDescartarPorColor(jugadorActual, (String) resultado);
                }
                break;

            case DESCARTAR_POR_NUMERO:
                if (resultado instanceof Integer) {
                    int numeroElegido = (Integer) resultado;
                    List<Carta> cartasDescartadas = jugadorActual.getMano().descartarCartasPorNumero(numeroElegido);
                    tablero.getDescarte().getCartas().addAll(cartasDescartadas);
                }
                break;

            case ROBAR_HASTA_AZUL:
                try { robarHastaColor(jugadorActual, "AZUL"); } catch (Exception e) {}
                break;

            case ROBAR_HASTA_ROJO:
                try { robarHastaColor(jugadorActual, "ROJO"); } catch (Exception e) {}
                break;

            case INTERCAMBIO_DE_MANOS:
                intercambiarManos();
                break;

            case PUNTUACION_MAS_BAJA:
                if (resultado instanceof String) {
                    String nombreCastigado = (String) resultado;
                    aplicarCastigoPuntuacionBaja(nombreCastigado);
                }
                break;

            case GUERRA:
                aplicarGuerra();
                break;

            case CASI_UNO:
                aplicarCasiUno(jugadorActual);
                break;

            case MOSTRAR_LA_MANO:
            case ELEGIR_COLOR:
                break;
        }

        this.estadoPartida = EstadoPartida.EN_PROCESO;
    }


    private void aplicarCasiUno(Jugador jugador) {
        while (jugador.getMano().getCartas().size() > 2) {
            Carta cartaEliminada = jugador.getMano().getCartas().remove(0);
            tablero.getDescarte().getCartas().add(cartaEliminada);
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
            if (cartaMasAlta != null && cartaMasAlta.getNumero() != null && cartaMasAlta.getNumero() > numeroGanador) {
                numeroGanador = cartaMasAlta.getNumero();
            }
        }
        if (numeroGanador != -1) {
            for (Jugador jugador : jugadores) {
                Carta carta = jugador.getMano().getHighest();
                if (carta != null && carta.getNumero() != null && carta.getNumero() == numeroGanador) {
                    List<Carta> cartasDescartadas = jugador.getMano().descartarCartasPorNumero(numeroGanador);
                    tablero.getDescarte().getCartas().addAll(cartasDescartadas);
                }
            }
        }
    }

    private void aplicarDescartarPorColor(Jugador jugador, String colorElegido) {
        if (colorElegido == null || colorElegido.isBlank()) return;

        List<Carta> mano = jugador.getMano().getCartas();
        List<Carta> cartasADescartar = new java.util.ArrayList<>();

        for (Carta c : mano) {
            if (c.getColor() != null && c.getColor().equalsIgnoreCase(colorElegido)) {
                cartasADescartar.add(c);
            }
        }
        mano.removeAll(cartasADescartar);
        tablero.getDescarte().getCartas().addAll(cartasADescartar);
    }

    private void aplicarCastigoPuntuacionBaja(String nombreCastigado) {
        jugadores.stream()
                .filter(j -> j.getNombre().equals(nombreCastigado))
                .findFirst()
                .ifPresent(j -> {
                    try {
                        Carta cartaCastigo = tablero.getMazo().robarCarta();
                        j.getMano().getCartas().add(cartaCastigo);
                    } catch (Exception e) {
                        System.out.println("El mazo se quedó sin cartas.");
                    }
                });
    }
}