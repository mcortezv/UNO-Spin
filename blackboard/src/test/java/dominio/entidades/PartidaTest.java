package dominio.entidades;

import dominio.entidades.enums.EstadoPartida;
import dominio.entidades.enums.TipoCarta;
import dominio.entidades.enums.TipoEventoRuleta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class PartidaTest {

    private Partida partida;
    private Jugador jugador1;
    private Jugador jugador2;

    @BeforeEach
    void setUp() {
        jugador1 = new Jugador("Miss wasabe", new Mano(new ArrayList<>()), List.of("ROJO"), 0, 1);
        jugador2 = new Jugador("Juanpi", new Mano(new ArrayList<>()), List.of("AZUL"), 0, 2);

        ConfiguracionPartida config = new ConfiguracionPartida();
        config.setValorMinimo(0);
        config.setValorMaximo(9);
        config.setCantidadComodines(8);
        config.setCantidadCartasAccion(6);
        config.setTiempoMaximoRuleta(10f);

        partida = new Partida();
        partida.iniciarPartida(Arrays.asList(jugador1, jugador2), config);
    }

    @Test
    void testIniciarPartida() {
        assertEquals(EstadoPartida.EN_PROCESO, partida.getEstadoPartida());
        assertEquals(0, partida.getIndiceJugadorActual());
        assertTrue(partida.isSentidoHorario());
        assertEquals(2, partida.getJugadores().size());
    }

    @Test
    void testRepartoInicial() {
        assertEquals(7, jugador1.getMano().getCartas().size());
        assertEquals(7, jugador2.getMano().getCartas().size());
    }

    @Test
    void testCartaInicialEnDescarte() {
        assertNotNull(partida.getCartaCima());
        assertEquals(TipoCarta.NUMERICA, partida.getCartaCima().getTipoCarta());
    }

    @Test
    void testValidarJugadaCorrecta() {
        Carta cima = partida.getCartaCima();
        Carta carta = new Carta(cima.getColor(), 5, TipoCarta.NUMERICA, 5);
        assertTrue(partida.validarJugada(carta));
    }

    @Test
    void testAplicarJugadaNumeroSpin() {
        partida.getTablero().getDescarte().getCartas().clear();
        partida.getTablero().getDescarte().getCartas().add(new Carta("AZUL", 5, TipoCarta.NUMERICA, 5));

        Carta carta = new Carta("AZUL", 7, TipoCarta.NUMERO_SPIN, 7);
        jugador1.getMano().getCartas().add(carta);

        boolean resultado = partida.aplicarJugada(carta);

        assertTrue(resultado);
        assertEquals(EstadoPartida.GIRO_PENDIENTE, partida.getEstadoPartida());
    }

    @Test
    void testAplicarJugadaReversa() {
        partida.getTablero().getDescarte().getCartas().clear();
        partida.getTablero().getDescarte().getCartas().add(new Carta("VERDE", 5, TipoCarta.NUMERICA, 5));

        Carta carta = new Carta("VERDE", null, TipoCarta.REVERSA, 20);
        jugador1.getMano().getCartas().add(carta);

        boolean resultado = partida.aplicarJugada(carta);

        assertTrue(resultado);
        assertFalse(partida.isSentidoHorario());
    }

    @Test
    void testAplicarJugadaBloqueo() {
        partida.getTablero().getDescarte().getCartas().clear();
        partida.getTablero().getDescarte().getCartas().add(new Carta("AMARILLO", 2, TipoCarta.NUMERICA, 2));

        Carta carta = new Carta("AMARILLO", null, TipoCarta.BLOQUEO, 20);
        jugador1.getMano().getCartas().add(carta);

        assertTrue(partida.aplicarJugada(carta));
    }

    @Test
    void testAplicarJugadaNormal() {
        Carta cima = partida.getCartaCima();
        Carta carta = new Carta(cima.getColor(), 5, TipoCarta.NUMERICA, 5);
        jugador1.getMano().getCartas().add(carta);

        boolean resultado = partida.aplicarJugada(carta);

        assertTrue(resultado);
        assertEquals(1, partida.getIndiceJugadorActual());
    }

    @Test
    void testAplicarJugadaInvalida() {
        Carta cima = partida.getCartaCima();
        String otroColor = "ROJO".equals(cima.getColor()) ? "AZUL" : "ROJO";
        Carta carta = new Carta(otroColor, (cima.getNumero() == null ? 9 : (cima.getNumero() + 1) % 10), TipoCarta.NUMERICA, 9);
        jugador1.getMano().getCartas().add(carta);

        boolean resultado = partida.aplicarJugada(carta);

        assertFalse(resultado);
        assertFalse(partida.getTablero().getDescarte().getCartas().contains(carta));
    }

    @Test
    void testAvanzarTurnoHorario() {
        partida.avanzarTurno();
        assertEquals(1, partida.getIndiceJugadorActual());
    }

    @Test
    void testAvanzarTurnoAntihorario() {
        partida.setSentidoHorario(false);
        partida.avanzarTurno();
        assertEquals(1, partida.getIndiceJugadorActual());
    }

    @Test
    void testRobarCartaJugadorActual() {
        int cartasAntes = jugador1.getMano().getCartas().size();
        partida.robarCartaJugadorActual();
        assertEquals(cartasAntes + 1, jugador1.getMano().getCartas().size());
    }

    @Test
    void testProcesarGiroRuletaNoPendiente() {
        assertThrows(Exception.class, () -> partida.procesarGiroRuleta());
    }

    @Test
    void testAplicarEfectoRuletaCasiUno() {
        jugador1.getMano().getCartas().clear();
        jugador1.getMano().getCartas().addAll(Arrays.asList(
                new Carta("ROJO", 1, TipoCarta.NUMERICA, 1),
                new Carta("AZUL", 2, TipoCarta.NUMERICA, 2),
                new Carta("VERDE", 3, TipoCarta.NUMERICA, 3),
                new Carta("AMARILLO", 4, TipoCarta.NUMERICA, 4)
        ));

        partida.aplicarEfectoRuleta(TipoEventoRuleta.CASI_UNO, null);

        assertEquals(2, jugador1.getMano().getCartas().size());
    }

    @Test
    void testAplicarEfectoRuletaDescartarPorColor() {
        jugador1.getMano().getCartas().clear();
        jugador1.getMano().getCartas().addAll(Arrays.asList(
                new Carta("ROJO", 1, TipoCarta.NUMERICA, 1),
                new Carta("ROJO", 2, TipoCarta.NUMERICA, 2),
                new Carta("AZUL", 3, TipoCarta.NUMERICA, 3)
        ));

        int descarteAntes = partida.getTablero().getDescarte().getCartas().size();
        partida.aplicarEfectoRuleta(TipoEventoRuleta.DESCARTAR_POR_COLOR, "ROJO");

        assertEquals(1, jugador1.getMano().getCartas().size());
        assertTrue(partida.getTablero().getDescarte().getCartas().size() >= descarteAntes + 2);
    }
}
