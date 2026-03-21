package dominio.entidades;

import dominio.entidades.enums.EstadoPartida;
import dominio.entidades.enums.TipoEventoRuleta;
import dominio.interfaces.IDominio;
import java.util.List;

public class Partida implements IDominio {
    private List<Jugador> jugadores;
    private EstadoPartida estadoPartida;
    private int indiceJugadorActual;
    private boolean sentidoHorario;
    private Tablero tablero;
    private boolean unoGritado = false;

    public Partida() {}

    public Partida(EstadoPartida estadoPartida, int indiceJugadorActual,
                   List<Jugador> jugadores, boolean sentidoHorario) {
        this.estadoPartida = estadoPartida;
        this.indiceJugadorActual = indiceJugadorActual;
        this.jugadores = jugadores;
        this.sentidoHorario = sentidoHorario;
    }

    public EstadoPartida getEstadoPartida() {
        return estadoPartida;
    }

    public void setEstadoPartida(EstadoPartida e) {
        this.estadoPartida = e;
    }

    public int getIndiceJugadorActual() {
        return indiceJugadorActual;
    }

    public void setIndiceJugadorActual(int i) {
        this.indiceJugadorActual = i;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> j) {
        this.jugadores = j;
    }

    public boolean isSentidoHorario() {
        return sentidoHorario;
    }

    public void setSentidoHorario(boolean s) {
        this.sentidoHorario = s;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero t) {
        this.tablero = t;
    }


    @Override
    public Carta getCartaCima() {
        return tablero.getDescarte().getUltimaCarta();
    }

    @Override
    public List<Carta> getCartasDescarte() {
        return tablero.getDescarte().getCartas();
    }

    @Override
    public int getCantidadCartasJugador(int indiceJugador) {
        return jugadores.get(indiceJugador).getMano().getCartas().size();
    }

    @Override
    public List<Carta> getManoJugador(int indiceJugador) {
        return jugadores.get(indiceJugador).getMano().getCartas();
    }

    @Override
    public void aplicarCastigoUno(int indiceJugador) {
        try {
            Carta castigo = tablero.getMazo().robarCarta();
            jugadores.get(indiceJugador).getMano().getCartas().add(castigo);
        } catch (Exception e) {
            System.out.println("Mazo vacío al aplicar castigo UNO.");
        }
    }


    @Override
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
        if (!tablero.getDescarte().validarCartaEntrante(c)) return false;

        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        List<Carta> cartasMano = jugadorActual.getMano().getCartas();

        for (int i = 0; i < cartasMano.size(); i++) {
            Carta cartaEnMano = cartasMano.get(i);
            if (cartaEnMano.getColor().equals(c.getColor())
                    && cartaEnMano.getValor() == c.getValor()) {
                cartasMano.remove(i);
                break;
            }
        }
        tablero.getDescarte().getCartas().add(c);

        switch (c.getTipoCarta()) {
            case NUMERO_SPIN:
                estadoPartida = EstadoPartida.GIRO_PENDIENTE;
                break;
            case REVERSA:
                sentidoHorario = !sentidoHorario;
                avanzarTurno();
                break;
            case BLOQUEO:
                avanzarTurno();
                avanzarTurno();
                break;
            case CAMBIO_COLOR:
            case TOMA_CUATRO:
                estadoPartida = EstadoPartida.SELECCION_COLOR_PENDIENTE;
                break;
            default:
                avanzarTurno();
                break;
        }
        return true;
    }

    @Override
    public void aplicarSeleccionColor(String color) {
        Carta cima = tablero.getDescarte().getUltimaCarta();
        cima.setColor(color);
        estadoPartida = EstadoPartida.EN_PROCESO;
        avanzarTurno();
    }

    @Override
    public void gritarUno() {
        this.unoGritado = true;
    }

    @Override
    public void avanzarTurno() {
        if (!jugadores.isEmpty()) {
            int n = jugadores.size();
            indiceJugadorActual = sentidoHorario
                    ? (indiceJugadorActual + 1) % n
                    : (indiceJugadorActual - 1 + n) % n;
        }
    }

    @Override
    public void robarCartaJugadorActual() {
        try {
            Carta cartaRobada = tablero.getMazo().robarCarta();
            jugadores.get(indiceJugadorActual).getMano().getCartas().add(cartaRobada);
            avanzarTurno();
        } catch (Exception e) {
            System.out.println("No hay más cartas en el mazo: " + e.getMessage());
        }
    }

    @Override
    public TipoEventoRuleta procesarGiroRuleta() throws Exception {
        if (estadoPartida != EstadoPartida.GIRO_PENDIENTE) {
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
                    List<Carta> mano = jugadorActual.getMano().getCartas();
                    if (idx >= 0 && idx < mano.size()) {
                        tablero.getDescarte().getCartas().add(mano.remove(idx));
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
                    int num = (Integer) resultado;
                    tablero.getDescarte().getCartas()
                            .addAll(jugadorActual.getMano().descartarCartasPorNumero(num));
                }
                break;
            case ROBAR_HASTA_AZUL:
                try { robarHastaColor(jugadorActual, "AZUL"); } catch (Exception ignored) {}
                break;
            case ROBAR_HASTA_ROJO:
                try { robarHastaColor(jugadorActual, "ROJO"); } catch (Exception ignored) {}
                break;
            case INTERCAMBIO_DE_MANOS:
                intercambiarManos();
                break;
            case PUNTUACION_MAS_BAJA:
                if (resultado instanceof String) {
                    aplicarCastigoPuntuacionBaja((String) resultado);
                }
                break;
            case GUERRA:
                aplicarGuerra();
                break;
            case CASI_UNO:
                aplicarCasiUno(jugadorActual);
                break;
            case MOSTRAR_LA_MANO:
           
        }
        estadoPartida = EstadoPartida.EN_PROCESO;
    }

    private void aplicarCasiUno(Jugador jugador) {
        while (jugador.getMano().getCartas().size() > 2) {
            tablero.getDescarte().getCartas()
                    .add(jugador.getMano().getCartas().remove(0));
        }
    }

    private void robarHastaColor(Jugador jugador, String colorObjetivo) throws Exception {
        boolean encontro = false;
        while (!encontro) {
            Carta c = tablero.getMazo().robarCarta();
            jugador.getMano().getCartas().add(c);
            if (colorObjetivo.equalsIgnoreCase(c.getColor())) encontro = true;
        }
    }

    private void intercambiarManos() {
        int n = jugadores.size();
        if (sentidoHorario) {
            Mano tmp = jugadores.get(n - 1).getMano();
            for (int i = n - 1; i > 0; i--) jugadores.get(i).setMano(jugadores.get(i - 1).getMano());
            jugadores.get(0).setMano(tmp);
        } else {
            Mano tmp = jugadores.get(0).getMano();
            for (int i = 0; i < n - 1; i++) jugadores.get(i).setMano(jugadores.get(i + 1).getMano());
            jugadores.get(n - 1).setMano(tmp);
        }
    }

    private void aplicarGuerra() {
        int numGanador = -1;
        for (Jugador j : jugadores) {
            Carta alta = j.getMano().getHighest();
            if (alta != null && alta.getNumero() != null && alta.getNumero() > numGanador)
                numGanador = alta.getNumero();
        }
        if (numGanador != -1) {
            final int num = numGanador;
            for (Jugador j : jugadores) {
                Carta alta = j.getMano().getHighest();
                if (alta != null && alta.getNumero() != null && alta.getNumero() == num)
                    tablero.getDescarte().getCartas().addAll(j.getMano().descartarCartasPorNumero(num));
            }
        }
    }

    private void aplicarDescartarPorColor(Jugador jugador, String color) {
        if (color == null || color.isBlank()) return;
        List<Carta> mano = jugador.getMano().getCartas();
        List<Carta> aDescartar = mano.stream()
                .filter(c -> color.equalsIgnoreCase(c.getColor()))
                .toList();
        mano.removeAll(aDescartar);
        tablero.getDescarte().getCartas().addAll(aDescartar);
    }

    private void aplicarCastigoPuntuacionBaja(String nombre) {
        jugadores.stream()
                .filter(j -> j.getNombre().equals(nombre))
                .findFirst()
                .ifPresent(j -> {
                    try { j.getMano().getCartas().add(tablero.getMazo().robarCarta()); }
                    catch (Exception e) { System.out.println("Mazo vacío."); }
                });
    }
}