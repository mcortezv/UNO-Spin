package dominio.entidades;
import dominio.entidades.enums.EstadoPartida;
import dominio.entidades.enums.TipoCarta;
import dto.TipoEventoRuletaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


class PartidaFlujoTest {

    private Partida partida;
    private Jugador jugador1;
    private Jugador jugador2;

    @BeforeEach
    void setUp() {
        jugador1 = new Jugador("Sebas", new Mano(new ArrayList<>()), List.of("ROJO"), 0, 1);
        jugador2 = new Jugador("Manuel", new Mano(new ArrayList<>()), List.of("AZUL"), 0, 2);

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
    void cambioColorTransicionaASeleccionPendiente() {
        Carta comodin = new Carta(null, null, TipoCarta.CAMBIO_COLOR, 50);
        jugador1.getMano().getCartas().add(comodin);

        partida.aplicarJugada(comodin);

        assertEquals(EstadoPartida.SELECCION_COLOR_PENDIENTE, partida.getEstadoPartida());
    }

    @Test
    void seleccionColorRestautraEstadoEnProceso() {
        Carta comodin = new Carta(null, null, TipoCarta.CAMBIO_COLOR, 50);
        jugador1.getMano().getCartas().add(comodin);
        partida.aplicarJugada(comodin);

        partida.aplicarSeleccionColor("VERDE");

        assertEquals(EstadoPartida.EN_PROCESO, partida.getEstadoPartida());
    }

    @Test
    void seleccionColorActualizaColorDeCima() {
        Carta comodin = new Carta(null, null, TipoCarta.CAMBIO_COLOR, 50);
        jugador1.getMano().getCartas().add(comodin);
        partida.aplicarJugada(comodin);

        partida.aplicarSeleccionColor("AMARILLO");

        assertEquals("AMARILLO", partida.getCartaCima().getColor());
    }

    @Test
    void seleccionColorAvanzaTurno() {
        Carta comodin = new Carta(null, null, TipoCarta.CAMBIO_COLOR, 50);
        jugador1.getMano().getCartas().add(comodin);
        partida.aplicarJugada(comodin);

        int turnoAntes = partida.getIndiceJugadorActual();
        partida.aplicarSeleccionColor("ROJO");

        assertNotEquals(turnoAntes, partida.getIndiceJugadorActual());
    }


    @Test
    void tomaCuatroTransicionaASeleccionColor() {
        Carta tomaCuatro = new Carta(null, null, TipoCarta.TOMA_CUATRO, 50);
        jugador1.getMano().getCartas().add(tomaCuatro);

        partida.aplicarJugada(tomaCuatro);

        assertEquals(EstadoPartida.SELECCION_COLOR_PENDIENTE, partida.getEstadoPartida());
    }


    @Test
    void numeroSpinTransicionaAGiroPendiente() {
        partida.getTablero().getDescarte().getCartas().clear();
        partida.getTablero().getDescarte().getCartas()
                .add(new Carta("ROJO", 3, TipoCarta.NUMERICA, 3));

        Carta spin = new Carta("ROJO", 7, TipoCarta.NUMERO_SPIN, 7);
        jugador1.getMano().getCartas().add(spin);

        partida.aplicarJugada(spin);

        assertEquals(EstadoPartida.GIRO_PENDIENTE, partida.getEstadoPartida());
    }

    @Test
    void procesarGiroRuletaRequiereEstadoGiroPendiente() {
        assertThrows(Exception.class, () -> partida.procesarGiroRuleta());
    }

    @Test
    void procesarGiroRuletaDevuelveEventoNoNulo() throws Exception {
        partida.getTablero().getDescarte().getCartas().clear();
        partida.getTablero().getDescarte().getCartas()
                .add(new Carta("AZUL", 2, TipoCarta.NUMERICA, 2));

        Carta spin = new Carta("AZUL", 5, TipoCarta.NUMERO_SPIN, 5);
        jugador1.getMano().getCartas().add(spin);
        partida.aplicarJugada(spin);

        TipoEventoRuletaDTO evento = partida.procesarGiroRuleta();

        assertNotNull(evento);
    }

    @Test
    void aplicarEfectoRuletaRestautraEstadoEnProceso() {
        partida.aplicarEfectoRuleta(TipoEventoRuletaDTO.CASI_UNO, null);
        assertEquals(EstadoPartida.EN_PROCESO, partida.getEstadoPartida());
    }


    @Test
    void tomaDosAgregaDosCartasAlSiguiente() {
        partida.getTablero().getDescarte().getCartas().clear();
        partida.getTablero().getDescarte().getCartas()
                .add(new Carta("VERDE", 1, TipoCarta.NUMERICA, 1));

        Carta tomaDos = new Carta("VERDE", null, TipoCarta.TOMA_DOS, 20);
        jugador1.getMano().getCartas().add(tomaDos);

        int cartasBob = jugador2.getMano().getCartas().size();
        partida.aplicarJugada(tomaDos);

        assertEquals(cartasBob + 2, jugador2.getMano().getCartas().size());
    }

    @Test
    void tomaDosAvanzaTurnoAlJugadorCastigado() {
        partida.getTablero().getDescarte().getCartas().clear();
        partida.getTablero().getDescarte().getCartas()
                .add(new Carta("VERDE", 1, TipoCarta.NUMERICA, 1));

        Carta tomaDos = new Carta("VERDE", null, TipoCarta.TOMA_DOS, 20);
        jugador1.getMano().getCartas().add(tomaDos);
        partida.aplicarJugada(tomaDos);

        assertEquals(1, partida.getIndiceJugadorActual());
    }


    @Test
    void reversaInvierteSentidoYAvanzaTurno() {
        partida.getTablero().getDescarte().getCartas().clear();
        partida.getTablero().getDescarte().getCartas()
                .add(new Carta("ROJO", 4, TipoCarta.NUMERICA, 4));

        Carta reversa = new Carta("ROJO", null, TipoCarta.REVERSA, 20);
        jugador1.getMano().getCartas().add(reversa);

        assertTrue(partida.isSentidoHorario());
        partida.aplicarJugada(reversa);

        assertFalse(partida.isSentidoHorario());
        assertEquals(1, partida.getIndiceJugadorActual());
    }


    @Test
    void robarCartaConMazoVacioReciclaDescarte() {
        partida.getTablero().getMazo().getCartas().clear();

        partida.getTablero().getDescarte().getCartas().clear();
        for (int i = 1; i <= 5; i++) {
            partida.getTablero().getDescarte().getCartas()
                    .add(new Carta("ROJO", i, TipoCarta.NUMERICA, i));
        }

        int manoAntes = jugador1.getMano().getCartas().size();
        assertDoesNotThrow(() -> partida.robarCartaJugadorActual());

        assertEquals(manoAntes + 1, jugador1.getMano().getCartas().size());
        assertEquals(1, partida.getTablero().getDescarte().getCartas().size());
    }


    @Test
    void casiUnoDescartaHastaMitad() {
        jugador1.getMano().getCartas().clear();
        jugador1.getMano().getCartas().addAll(Arrays.asList(
                new Carta("ROJO", 1, TipoCarta.NUMERICA, 1),
                new Carta("AZUL", 2, TipoCarta.NUMERICA, 2),
                new Carta("VERDE", 3, TipoCarta.NUMERICA, 3),
                new Carta("AMARILLO", 4, TipoCarta.NUMERICA, 4)
        ));

        partida.aplicarEfectoRuleta(TipoEventoRuletaDTO.CASI_UNO, null);

        assertEquals(2, jugador1.getMano().getCartas().size());
    }



    @Test
    void guerraSinCartasNumericasNoLanzaExcepcion() {
        jugador1.getMano().getCartas().clear();
        jugador1.getMano().getCartas().add(new Carta(null, null, TipoCarta.CAMBIO_COLOR, 50));

        jugador2.getMano().getCartas().clear();
        jugador2.getMano().getCartas().add(new Carta(null, null, TipoCarta.TOMA_CUATRO, 50));

        assertDoesNotThrow(() -> partida.aplicarEfectoRuleta(TipoEventoRuletaDTO.GUERRA, null));
    }


    @Test
    void descartarPorColorEliminaCartasDelColor() {
        jugador1.getMano().getCartas().clear();
        jugador1.getMano().getCartas().addAll(Arrays.asList(
                new Carta("ROJO", 1, TipoCarta.NUMERICA, 1),
                new Carta("ROJO", 2, TipoCarta.NUMERICA, 2),
                new Carta("AZUL", 3, TipoCarta.NUMERICA, 3)
        ));

        partida.aplicarEfectoRuleta(TipoEventoRuletaDTO.DESCARTAR_POR_COLOR, "ROJO");

        assertEquals(1, jugador1.getMano().getCartas().size());
        assertEquals("AZUL", jugador1.getMano().getCartas().get(0).getColor());
    }


    @Test
    void descartarPorNumeroEliminaCartasDelNumero() {
        jugador1.getMano().getCartas().clear();
        jugador1.getMano().getCartas().addAll(Arrays.asList(
                new Carta("ROJO", 5, TipoCarta.NUMERICA, 5),
                new Carta("AZUL", 5, TipoCarta.NUMERICA, 5),
                new Carta("VERDE", 3, TipoCarta.NUMERICA, 3)
        ));

        partida.aplicarEfectoRuleta(TipoEventoRuletaDTO.DESCARTAR_POR_NUMERO, 5);

        assertEquals(1, jugador1.getMano().getCartas().size());
        assertEquals(3, jugador1.getMano().getCartas().get(0).getNumero());
    }


    @Test
    void intercambioManosInvierteManos() {
        jugador1.getMano().getCartas().clear();
        jugador1.getMano().getCartas().add(new Carta("ROJO", 1, TipoCarta.NUMERICA, 1));

        jugador2.getMano().getCartas().clear();
        jugador2.getMano().getCartas().addAll(Arrays.asList(
                new Carta("AZUL", 2, TipoCarta.NUMERICA, 2),
                new Carta("VERDE", 3, TipoCarta.NUMERICA, 3)
        ));

        partida.aplicarEfectoRuleta(TipoEventoRuletaDTO.INTERCAMBIO_DE_MANOS, null);

        assertEquals(2, jugador1.getMano().getCartas().size());
        assertEquals(1, jugador2.getMano().getCartas().size());
    }


    @Test
    void gritarUnoMarcaFlag() {
        assertFalse(partida.isUnoGritado());
        partida.gritarUno();
        assertTrue(partida.isUnoGritado());
    }


    @Test
    void jugarUltimaCartaFinalizaPartida() {
        jugador1.getMano().getCartas().clear();
        Carta cima = partida.getCartaCima();
        Carta ultima = new Carta(cima.getColor(), 1, TipoCarta.NUMERICA, 1);
        jugador1.getMano().getCartas().add(ultima);

        partida.aplicarJugada(ultima);

        assertEquals(EstadoPartida.FINALIZADA, partida.getEstadoPartida());
    }

    @Test
    void jugarUltimaCartaNoAvanzaTurno() {
        jugador1.getMano().getCartas().clear();
        Carta cima = partida.getCartaCima();
        Carta ultima = new Carta(cima.getColor(), 2, TipoCarta.NUMERICA, 2);
        jugador1.getMano().getCartas().add(ultima);

        partida.aplicarJugada(ultima);

        assertEquals(0, partida.getIndiceJugadorActual());
    }


    @Test
    void tomaCuatroDaCuatroCartasAlSiguiente() {
        Carta tomaCuatro = new Carta(null, null, TipoCarta.TOMA_CUATRO, 50);
        jugador1.getMano().getCartas().add(tomaCuatro);

        int cartasManuel = jugador2.getMano().getCartas().size();
        partida.aplicarJugada(tomaCuatro);

        assertEquals(cartasManuel + 4, jugador2.getMano().getCartas().size());
    }

    @Test
    void tomaCuatroTransicionaASeleccionColorYDaCartas() {
        Carta tomaCuatro = new Carta(null, null, TipoCarta.TOMA_CUATRO, 50);
        jugador1.getMano().getCartas().add(tomaCuatro);

        partida.aplicarJugada(tomaCuatro);

        assertEquals(EstadoPartida.SELECCION_COLOR_PENDIENTE, partida.getEstadoPartida());
    }
}
