
import dominio.entidades.Carta;
import dominio.entidades.Descarte;
import dominio.entidades.Jugador;
import dominio.entidades.Mano;
import dominio.entidades.Mazo;
import dominio.entidades.Partida;
import dominio.entidades.Ruleta;
import dominio.entidades.Tablero;
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
    private Tablero tablero;

    @BeforeEach
    void setUp() {
        jugador1 = new Jugador("Miss wasabe", new Mano(new ArrayList<>()), Arrays.asList("ROJO"), 0, 1);
        jugador2 = new Jugador("Juanpi", new Mano(new ArrayList<>()), Arrays.asList("AZUL"), 0, 2);
        tablero = new Tablero(new Descarte(), new Mazo(), new Ruleta());

        partida = new Partida();
        partida.iniciarPartida(Arrays.asList(jugador1, jugador2), tablero);

        // Carta inicial en el descarte para evitar NoSuchElementException
        tablero.getDescarte().getCartas().add(new Carta("ROJO", 3, TipoCarta.NUMERICA, 3));
    }

    @Test
    void testIniciarPartida() {
        assertEquals(EstadoPartida.EN_PROCESO, partida.getEstadoPartida());
        assertEquals(0, partida.getIndiceJugadorActual());
        assertTrue(partida.isSentidoHorario());
        assertEquals(2, partida.getJugadores().size());
    }

    @Test
    void testValidarJugadaCorrecta() {
        Carta carta = new Carta("ROJO", 5, TipoCarta.NUMERICA, 5);
        tablero.getDescarte().getCartas().add(new Carta("ROJO", 3, TipoCarta.NUMERICA, 3));
        assertTrue(partida.validarJugada(carta));
    }

    @Test
    void testAplicarJugadaNumeroSpin() {
        tablero.getDescarte().getCartas().clear();
        tablero.getDescarte().getCartas().add(new Carta("AZUL", 5, TipoCarta.NUMERICA, 5));

        Carta carta = new Carta("AZUL", 7, TipoCarta.NUMERO_SPIN, 7);
        jugador1.getMano().getCartas().add(carta);

        boolean resultado = partida.aplicarJugada(carta);

        assertTrue(resultado, "La jugada Spin debería ser válida");
        assertEquals(EstadoPartida.GIRO_PENDIENTE, partida.getEstadoPartida());
    }

    @Test
    void testAplicarJugadaReversa() {
        // Ajustar descarte para que coincida en color
        tablero.getDescarte().getCartas().clear();
        tablero.getDescarte().getCartas().add(new Carta("VERDE", 5, TipoCarta.NUMERICA, 5));

        Carta carta = new Carta("VERDE", 0, TipoCarta.REVERSA, 20);
        jugador1.getMano().getCartas().add(carta);

        boolean resultado = partida.aplicarJugada(carta);

        assertTrue(resultado, "La jugada Reversa debería ser válida");
        assertFalse(partida.isSentidoHorario(), "El sentido debe invertirse");
    }

    @Test
    void testAplicarJugadaBloqueo() {
        tablero.getDescarte().getCartas().clear();
        tablero.getDescarte().getCartas().add(new Carta("AMARILLO", 2, TipoCarta.NUMERICA, 2));

        Carta carta = new Carta("AMARILLO", 0, TipoCarta.BLOQUEO, 20);
        jugador1.getMano().getCartas().add(carta);

        boolean resultado = partida.aplicarJugada(carta);

        assertTrue(resultado, "La jugada Bloqueo debería ser válida");
    }

    @Test
    void testAplicarJugadaNormal() {
        Carta carta = new Carta("ROJO", 5, TipoCarta.NUMERICA, 5);
        jugador1.getMano().getCartas().add(carta);

        boolean resultado = partida.aplicarJugada(carta);

        assertTrue(resultado, "La jugada normal debería ser válida");
        assertEquals(1, partida.getIndiceJugadorActual(), "El turno debe avanzar al siguiente jugador");
        assertTrue(tablero.getDescarte().getCartas().contains(carta), "La carta debe estar en el descarte");
    }

    @Test
    void testAplicarJugadaInvalida() {
        Carta carta = new Carta("AZUL", 9, TipoCarta.NUMERICA, 9);
        jugador1.getMano().getCartas().add(carta);

        boolean resultado = partida.aplicarJugada(carta);

        assertFalse(resultado, "La jugada inválida debería devolver false");
        assertFalse(tablero.getDescarte().getCartas().contains(carta), "La carta no debe estar en el descarte");
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
        assertEquals(1, partida.getIndiceJugadorActual()); // con 2 jugadores retrocede
    }

    @Test
    void testRobarCartaJugadorActual() {
        Carta carta = new Carta("AZUL", 9, TipoCarta.NUMERICA, 9);
        tablero.getMazo().getCartas().add(carta);

        partida.robarCartaJugadorActual();

        assertTrue(jugador1.getMano().getCartas().contains(carta));
    }

    @Test
    void testProcesarGiroRuletaNoPendiente() {
        assertThrows(Exception.class, () -> partida.procesarGiroRuleta());
    }

    @Test
    void testAplicarEfectoRuletaCasiUno() {
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
        jugador1.getMano().getCartas().addAll(Arrays.asList(
                new Carta("ROJO", 1, TipoCarta.NUMERICA, 1),
                new Carta("ROJO", 2, TipoCarta.NUMERICA, 2),
                new Carta("AZUL", 3, TipoCarta.NUMERICA, 3)
        ));

        partida.aplicarEfectoRuleta(TipoEventoRuleta.DESCARTAR_POR_COLOR, "ROJO");

        assertEquals(1, jugador1.getMano().getCartas().size());
        assertTrue(tablero.getDescarte().getCartas().size() >= 2);
    }
}
