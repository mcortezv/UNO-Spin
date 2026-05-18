package dominio.entidades;
import dominio.entidades.enums.EstadoPartida;
import dominio.entidades.enums.TipoCarta;
import dto.EventoAbandonoDTO;
import dto.TipoEventoRuletaDTO;
import dominio.IDominio;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The type Partida.
 */
public class Partida implements IDominio {
    private static final int CARTAS_INICIALES = 7;
    private static final int VALOR_CARTA_ACCION = 20;
    private static final int VALOR_CARTA_COMODIN = 50;
    private static final int NUMERO_SPIN_POR_COLOR = 2;
    private static final String[] COLORES = {"ROJO", "AZUL", "AMARILLO", "VERDE"};
    private List<Jugador> jugadores;
    private EstadoPartida estadoPartida;
    private int indiceJugadorActual;
    private boolean sentidoHorario;
    private Tablero tablero;
    private boolean unoGritado = false;
    private boolean ultimaJugadaValida = true;
    private String ganador;

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
    public Partida(EstadoPartida estadoPartida, int indiceJugadorActual,
                   List<Jugador> jugadores, boolean sentidoHorario) {
        this.estadoPartida = estadoPartida;
        this.indiceJugadorActual = indiceJugadorActual;
        this.jugadores = jugadores;
        this.sentidoHorario = sentidoHorario;
    }

    @Override
    public void iniciarPartida(List<Jugador> jugadoresIniciales, ConfiguracionPartida configuracion) {
        this.jugadores = jugadoresIniciales;
        this.indiceJugadorActual = 0;
        this.sentidoHorario = true;

        Mazo mazo = new Mazo(generarMazo(configuracion));
        mazo.mezclar();

        for (Jugador j : jugadoresIniciales) {
            j.setMano(new Mano(new ArrayList<>()));
            for (int i = 0; i < CARTAS_INICIALES; i++) {
                try {
                    j.getMano().getCartas().add(mazo.robarCarta());
                } catch (Exception ignored) {}
            }
        }

        Descarte descarte = new Descarte();
        Carta inicial = extraerCartaInicial(mazo);
        if (inicial != null) {
            descarte.getCartas().add(inicial);
        }

        this.tablero = new Tablero(descarte, mazo, new Ruleta());
        this.estadoPartida = EstadoPartida.EN_PROCESO;
    }

    private List<Carta> generarMazo(ConfiguracionPartida cfg) {
        List<Carta> cartas = new ArrayList<>();

        for (String color : COLORES) {
            for (int valor = cfg.getValorMinimo(); valor <= cfg.getValorMaximo(); valor++) {
                int copias = (valor == 0) ? 1 : 2;
                for (int i = 0; i < copias; i++) {
                    cartas.add(new Carta(color, valor, TipoCarta.NUMERICA, valor));
                }
            }

            TipoCarta[] tiposAccion = {TipoCarta.BLOQUEO, TipoCarta.REVERSA, TipoCarta.TOMA_DOS};
            int[] reparto = repartir(cfg.getCantidadCartasAccion(), tiposAccion.length);
            for (int t = 0; t < tiposAccion.length; t++) {
                for (int i = 0; i < reparto[t]; i++) {
                    cartas.add(new Carta(color, null, tiposAccion[t], VALOR_CARTA_ACCION));
                }
            }

            for (int i = 0; i < NUMERO_SPIN_POR_COLOR; i++) {
                int valor = ThreadLocalRandom.current().nextInt(cfg.getValorMinimo(), cfg.getValorMaximo() + 1);
                cartas.add(new Carta(color, valor, TipoCarta.NUMERO_SPIN, valor));
            }
        }

        int[] comodines = repartir(cfg.getCantidadComodines(), 2);
        for (int i = 0; i < comodines[0]; i++) {
            cartas.add(new Carta(null, null, TipoCarta.CAMBIO_COLOR, VALOR_CARTA_COMODIN));
        }
        for (int i = 0; i < comodines[1]; i++) {
            cartas.add(new Carta(null, null, TipoCarta.TOMA_CUATRO, VALOR_CARTA_COMODIN));
        }

        return cartas;
    }

    private int[] repartir(int total, int n) {
        int[] r = new int[n];
        int base = total / n;
        int resto = total % n;
        for (int i = 0; i < n; i++) {
            r[i] = base + (i < resto ? 1 : 0);
        }
        return r;
    }

    private Carta extraerCartaInicial(Mazo mazo) {
        List<Carta> cartas = mazo.getCartas();
        for (int i = cartas.size() - 1; i >= 0; i--) {
            if (cartas.get(i).getTipoCarta() == TipoCarta.NUMERICA) {
                return cartas.remove(i);
            }
        }
        return cartas.isEmpty() ? null : cartas.removeLast();
    }

    @Override
    public boolean aplicarJugada(Carta carta) {
        if (!tablero.getDescarte().validarCartaEntrante(carta)) {
            this.ultimaJugadaValida = false;
            return false;
        }

        this.ultimaJugadaValida = true;

        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        List<Carta> cartasMano = jugadorActual.getMano().getCartas();

        boolean removida = false;
        for (int i = 0; i < cartasMano.size(); i++) {
            Carta enMano = cartasMano.get(i);
            if (enMano.getTipoCarta() == carta.getTipoCarta()
                    && enMano.getValor() == carta.getValor()
                    && matchColor(enMano.getColor(), carta.getColor())) {
                cartasMano.remove(i);
                removida = true;
                break;
            }
        }

        if (!removida) return false;

        tablero.getDescarte().getCartas().add(carta);

        if (cartasMano.isEmpty()) {
            estadoPartida = EstadoPartida.FINALIZADA;
            return true;
        }

        switch (carta.getTipoCarta()) {
            case NUMERO_SPIN  -> estadoPartida = EstadoPartida.GIRO_PENDIENTE;
            case REVERSA      -> { sentidoHorario = !sentidoHorario; avanzarTurno(); }
            case BLOQUEO      -> { avanzarTurno(); avanzarTurno(); }
            case TOMA_DOS     -> avanzarTurno();
            case TOMA_CUATRO  -> estadoPartida = EstadoPartida.SELECCION_COLOR_PENDIENTE;
            case CAMBIO_COLOR -> estadoPartida = EstadoPartida.SELECCION_COLOR_PENDIENTE;
            default           -> avanzarTurno();
        }
        return true;
    }

    @Override
    public void robarCartaJugadorActual() {
        reciclarDescarteSiNecesario();
        try {
            Carta cartaRobada = tablero.getMazo().robarCarta();
            jugadores.get(indiceJugadorActual).getMano().getCartas().add(cartaRobada);
        } catch (Exception e) {
        }
    }

    @Override
    public void robarCartaSinAvanzarTurno() {
        reciclarDescarteSiNecesario();
        try {
            Carta cartaRobada = tablero.getMazo().robarCarta();
            jugadores.get(indiceJugadorActual).getMano().getCartas().add(cartaRobada);
        } catch (Exception e) {
            System.out.println("No hay más cartas disponibles.");
        }
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
    public void aplicarCastigoUno(int indiceJugador) {
        reciclarDescarteSiNecesario();
        try {
            Carta castigo = tablero.getMazo().robarCarta();
            jugadores.get(indiceJugador).getMano().getCartas().add(castigo);
        } catch (Exception e) {
        }
    }

    @Override
    public TipoEventoRuletaDTO procesarGiroRuleta() throws Exception {
        if (estadoPartida != EstadoPartida.GIRO_PENDIENTE) {
            throw new Exception("No es momento de girar la ruleta.");
        }
        return tablero.getRuleta().girar();
    }

    @Override
    public void aplicarEfectoRuleta(TipoEventoRuletaDTO evento, Object resultado) {
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
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    @Override
    public List<Carta> getManoJugador(int indiceJugador) {
        return jugadores.get(indiceJugador).getMano().getCartas();
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
    public boolean isUltimaJugadaValida() {
        return this.ultimaJugadaValida;
    }

    @Override
    public TipoEventoRuletaDTO getEventoRuleta() {
        return tablero.getRuleta().getEventoRuleta();
    }

    /**
     * Gets tablero.
     *
     * @return the tablero
     */
    public Tablero getTablero() {
        return tablero;
    }

    /**
     * Sets tablero.
     *
     * @param t the t
     */
    public void setTablero(Tablero t) {
        this.tablero = t;
    }

    /**
     * Sets estado partida.
     *
     * @param e the e
     */
    public void setEstadoPartida(EstadoPartida e) {
        this.estadoPartida = e;
    }

    /**
     * Sets indice jugador actual.
     *
     * @param i the
     */
    public void setIndiceJugadorActual(int i) {
        this.indiceJugadorActual = i;
    }

    /**
     * Sets jugadores.
     *
     * @param j the j
     */
    public void setJugadores(List<Jugador> j) {
        this.jugadores = j;
    }

    /**
     * Sets sentido horario.
     *
     * @param s the s
     */
    public void setSentidoHorario(boolean s) {
        this.sentidoHorario = s;
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
     * Is uno gritado boolean.
     *
     * @return the boolean
     */
    public boolean isUnoGritado() {
        return unoGritado;
    }

    /**
     * Sets uno gritado.
     *
     * @param u the u
     */
    public void setUnoGritado(boolean u) {
        this.unoGritado = u;
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
        int intentos = 0;
        while (intentos++ < 60) {
            reciclarDescarteSiNecesario();
            Carta c = tablero.getMazo().robarCarta();
            jugador.getMano().getCartas().add(c);
            if (colorObjetivo.equalsIgnoreCase(c.getColor())) break;
        }
    }

    private void reciclarDescarteSiNecesario() {
        if (!tablero.getMazo().getCartas().isEmpty()) return;
        List<Carta> descarte = tablero.getDescarte().getCartas();
        if (descarte.size() <= 1) return;
        List<Carta> recicladas = new ArrayList<>(descarte.subList(0, descarte.size() - 1));
        descarte.subList(0, descarte.size() - 1).clear();
        recicladas.forEach(c -> { if (c.esComodin()) c.setColor(null); });
        Collections.shuffle(recicladas);
        tablero.getMazo().getCartas().addAll(recicladas);
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
                    catch (Exception e) { System.out.println("Mazo vacio."); }
                });
    }

    public void setGanador(String nombre){
        this.ganador = nombre;
    }

    public String getGanador(){
        return ganador;
    }

    public EventoAbandonoDTO removerJugador(String nombre) {
        devolverCartas(nombre);
        EventoAbandonoDTO evento = new EventoAbandonoDTO();

        for (Jugador j : jugadores) {
            if (j.getNombre().equalsIgnoreCase(nombre)){
                evento.setNombreAbandono(nombre);
                jugadores.remove(j);
                break;
            }
        }

        if (jugadores.size() <= 1) {
            setGanador(jugadores.getFirst().getNombre());
            evento.setNombreGanador(ganador);
            finalizarPartida();
        }

        return evento;
    }

    private void devolverCartas(String nombre) {
        for (Jugador j : jugadores) {
            if (j.getNombre().equalsIgnoreCase(nombre)){
                List<Carta> cartas = j.getMano().getCartas();
                tablero.getMazo().addCartas(cartas);
            }
        }
    }

    public void finalizarPartida() {
        estadoPartida = EstadoPartida.FINALIZADA;
    }
}