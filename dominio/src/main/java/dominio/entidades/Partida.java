package dominio.entidades;

import dominio.entidades.enums.EstadoPartida;
import dominio.entidades.enums.TipoEventoRuleta;
import dominio.interfaces.IDominio;
import dominio.mappers.CartaMapper;
import dominio.mappers.JugadorMapper;
import dto.CartaDTO;
import dto.JugadorDTO;

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

    @Override
    public void iniciarPartida(List<Jugador> jugadoresIniciales, Tablero tableroInicial) {
        this.jugadores = jugadoresIniciales;
        this.tablero = tableroInicial;
        this.estadoPartida = EstadoPartida.EN_PROCESO;
        this.indiceJugadorActual = 0;
        this.sentidoHorario = true;
    }

    @Override
    public boolean validarJugada(CartaDTO cartaDTO) {
        Carta carta = CartaMapper.toEntity(cartaDTO);
        return tablero.getDescarte().validarCartaEntrante(carta);
    }

    @Override
    public boolean aplicarJugada(CartaDTO cartaDTO) {
        Carta c = CartaMapper.toEntity(cartaDTO);

        if (!tablero.getDescarte().validarCartaEntrante(c)) return false;

        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        List<Carta> cartasMano = jugadorActual.getMano().getCartas();

        boolean removida = false;
        for (int i = 0; i < cartasMano.size(); i++) {
            Carta enMano = cartasMano.get(i);
            if (enMano.getTipoCarta() == c.getTipoCarta()
                    && enMano.getValor() == c.getValor()
                    && matchColor(enMano.getColor(), c.getColor())) {
                cartasMano.remove(i);
                removida = true;
                break;
            }
        }

        if (!removida) return false;

        tablero.getDescarte().getCartas().add(c);

        switch (c.getTipoCarta()) {
            case NUMERO_SPIN  -> estadoPartida = EstadoPartida.GIRO_PENDIENTE;
            case REVERSA      -> { sentidoHorario = !sentidoHorario; avanzarTurno(); }
            case BLOQUEO      -> { avanzarTurno(); avanzarTurno(); }
            case TOMA_DOS     -> { aplicarCastigoUno(siguienteIndice()); avanzarTurno(); }
            case CAMBIO_COLOR,
                 TOMA_CUATRO  -> estadoPartida = EstadoPartida.SELECCION_COLOR_PENDIENTE;
            default           -> avanzarTurno();
        }
        return true;
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
    public void aplicarSeleccionColor(String color) {
        tablero.getDescarte().getUltimaCarta().setColor(color);
        estadoPartida = EstadoPartida.EN_PROCESO;
        avanzarTurno();
    }

    @Override
    public void gritarUno() {
        this.unoGritado = true;
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
            case DESCARTAR_POR_COLOR -> {
                if (resultado instanceof String color) {
                    aplicarDescartarPorColor(jugadorActual, color);
                }
            }
            case DESCARTAR_POR_NUMERO -> {
                if (resultado instanceof Integer num) {
                    tablero.getDescarte().getCartas()
                            .addAll(jugadorActual.getMano().descartarCartasPorNumero(num));
                }
            }
            case ROBAR_HASTA_AZUL -> {
                try { robarHastaColor(jugadorActual, "AZUL"); } catch (Exception ignored) {}
            }
            case ROBAR_HASTA_ROJO -> {
                try { robarHastaColor(jugadorActual, "ROJO"); } catch (Exception ignored) {}
            }
            case INTERCAMBIO_DE_MANOS -> intercambiarManos();
            case PUNTUACION_MAS_BAJA -> {
                if (resultado instanceof String nombre) {
                    aplicarCastigoPuntuacionBaja(nombre);
                }
            }
            case GUERRA    -> aplicarGuerra();
            case CASI_UNO  -> aplicarCasiUno(jugadorActual);
            case MOSTRAR_LA_MANO -> { }
        }
        estadoPartida = EstadoPartida.EN_PROCESO;
    }

    @Override
    public void avanzarTurno() {
        if (jugadores == null || jugadores.isEmpty()) return;
        int n = jugadores.size();
        indiceJugadorActual = sentidoHorario
                ? (indiceJugadorActual + 1) % n
                : (indiceJugadorActual - 1 + n) % n;
    }

    @Override
    public EstadoPartida getEstadoPartida() { return estadoPartida; }

    @Override
    public int getIndiceJugadorActual() { return indiceJugadorActual; }

    @Override
    public int getCantidadCartasJugador(int indiceJugador) {
        return jugadores.get(indiceJugador).getMano().getCartas().size();
    }

    @Override
    public List<JugadorDTO> getJugadores() {
        return JugadorMapper.toDTO(jugadores);
    }

    @Override
    public List<CartaDTO> getManoJugador(int indiceJugador) {
        return CartaMapper.toDTO(jugadores.get(indiceJugador).getMano().getCartas());
    }

    @Override
    public CartaDTO getCartaCima() {
        return CartaMapper.toDTO(tablero.getDescarte().getUltimaCarta());
    }

    @Override
    public List<CartaDTO> getCartasDescarte() {
        return CartaMapper.toDTO(tablero.getDescarte().getCartas());
    }


    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero t) {
        this.tablero = t;
    }

    public void setEstadoPartida(EstadoPartida e) {
        this.estadoPartida = e;
    }

    public void setIndiceJugadorActual(int i) {
        this.indiceJugadorActual = i;
    }

    public void setJugadores(List<Jugador> j) {
        this.jugadores = j;
    }

    public void setSentidoHorario(boolean s) {
        this.sentidoHorario = s;
    }

    public boolean isSentidoHorario() {
        return sentidoHorario;
    }

    public boolean isUnoGritado() {
        return unoGritado;
    }

    public void setUnoGritado(boolean u) {
        this.unoGritado = u;
    }

    public List<Jugador> getJugadoresEntidades() {
        return jugadores;
    }


    private int siguienteIndice() {
        int n = jugadores.size();
        return sentidoHorario
                ? (indiceJugadorActual + 1) % n
                : (indiceJugadorActual - 1 + n) % n;
    }


    private boolean matchColor(String colorMano, String colorJugada) {
        if (colorMano == null || colorJugada == null) return true;
        return colorMano.equals(colorJugada);
    }

    private void aplicarCasiUno(Jugador jugador) {
        List<Carta> mano = jugador.getMano().getCartas();
        while (mano.size() > 2) {
            tablero.getDescarte().getCartas().add(mano.removeFirst());
        }
    }

    private void robarHastaColor(Jugador jugador, String colorObjetivo) throws Exception {
        while (true) {
            Carta c = tablero.getMazo().robarCarta();
            jugador.getMano().getCartas().add(c);
            if (colorObjetivo.equalsIgnoreCase(c.getColor())) break;
        }
    }

    private void intercambiarManos() {
        int n = jugadores.size();
        if (sentidoHorario) {
            Mano tmp = jugadores.get(n - 1).getMano();
            for (int i = n - 1; i > 0; i--)
                jugadores.get(i).setMano(jugadores.get(i - 1).getMano());
            jugadores.get(0).setMano(tmp);
        } else {
            Mano tmp = jugadores.get(0).getMano();
            for (int i = 0; i < n - 1; i++)
                jugadores.get(i).setMano(jugadores.get(i + 1).getMano());
            jugadores.get(n - 1).setMano(tmp);
        }
    }

    private void aplicarGuerra() {
        int numGanador = -1;
        for (Jugador j : jugadores) {
            Carta alta = j.getMano().getMasAlta();
            if (alta != null && alta.getNumero() != null && alta.getNumero() > numGanador)
                numGanador = alta.getNumero();
        }
        if (numGanador != -1) {
            final int num = numGanador;
            for (Jugador j : jugadores) {
                Carta alta = j.getMano().getMasAlta();
                if (alta != null && alta.getNumero() != null && alta.getNumero() == num)
                    tablero.getDescarte().getCartas()
                            .addAll(j.getMano().descartarCartasPorNumero(num));
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