package factory;
import dto.CartaDTO;
import dto.JugadorDTO;
import dto.TipoAccionDTO;
import implementacion.JsonSerializer;
import interfaces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BlackboardIntegracionTest {

    private IBlackboard blackboard;
    private IReceptor receptorBlackboard;
    private ISerializer serializer;

    @BeforeEach
    void setUp() {
        IFactoryBlackboard factoryBlackboard = new FactoryBlackboard();
        serializer = new JsonSerializer();
        blackboard = factoryBlackboard.crearBlackboardObservable(serializer);
        receptorBlackboard = factoryBlackboard.crearReceptorBlackboard(serializer);
    }


    private void enviar(TipoAccionDTO accion) {
        receptorBlackboard.recibirMensaje(serializer.serialize(accion));
    }

    private void unirJugador(String nombre) {
        TipoAccionDTO accion = new TipoAccionDTO("UNIRSE_PARTIDA");
        JugadorDTO jugador = new JugadorDTO();
        jugador.setNombre(nombre);
        accion.setJugadorDTO(jugador);
        enviar(accion);
    }

    private void confirmar(String nombre) {
        TipoAccionDTO accion = new TipoAccionDTO("CONFIRMAR_INICIO");
        JugadorDTO jugador = new JugadorDTO();
        jugador.setNombre(nombre);
        accion.setJugadorDTO(jugador);
        enviar(accion);
    }

    private void arrancarConDosJugadores() {
        unirJugador("Ana");
        unirJugador("Bob");
        confirmar("Ana");
        confirmar("Bob");
    }



    @Test
    void unJugadorNoArrancaPartida() {
        unirJugador("Ana");
        assertEquals("NO_INICIADA", blackboard.getEstadoPartida());
    }

    @Test
    void jugadoresDuplicadosNoSuperanMaximo() {
        unirJugador("Ana");
        unirJugador("Ana");
        unirJugador("Bob");
        assertEquals("NO_INICIADA", blackboard.getEstadoPartida());
    }

    @Test
    void dosJugadoresConConfirmacionArrancanPartida() {
        arrancarConDosJugadores();
        assertEquals("EN_PROCESO", blackboard.getEstadoPartida());
    }

    @Test
    void cuatroJugadoresArrancanSinConfirmacion() {
        unirJugador("Ana");
        unirJugador("Bob");
        unirJugador("Carlos");
        unirJugador("Diana");
        assertEquals("EN_PROCESO", blackboard.getEstadoPartida());
    }


    @Test
    void jugarCartaAntesDeIniciarNoHaceNada() {
        unirJugador("Ana");
        CartaDTO carta = new CartaDTO();
        carta.setColor("ROJO");
        carta.setTipoCarta("NUMERICA");
        carta.setNumero(5);
        carta.setValor("5");
        TipoAccionDTO accion = new TipoAccionDTO("JUGAR_CARTA", carta);
        assertDoesNotThrow(() -> enviar(accion));
        assertEquals("NO_INICIADA", blackboard.getEstadoPartida());
    }

    @Test
    void pedirCartaAntesDeIniciarNoHaceNada() {
        unirJugador("Ana");
        assertDoesNotThrow(() -> enviar(new TipoAccionDTO("PEDIR_CARTA")));
        assertEquals("NO_INICIADA", blackboard.getEstadoPartida());
    }


    @Test
    void cartaCimaNoNulaTrasIniciar() {
        arrancarConDosJugadores();
        assertNotNull(blackboard.getCartaCima());
    }

    @Test
    void cadaJugadorRecibeSieteCartasIniciales() {
        arrancarConDosJugadores();
        assertEquals(7, blackboard.getManoJugador(0).size());
        assertEquals(7, blackboard.getManoJugador(1).size());
    }

    @Test
    void indiceJugadorActualEsCeroAlArrancar() {
        arrancarConDosJugadores();
        assertEquals(0, blackboard.getIndiceJugadorActual());
    }

    @Test
    void jugadoresListaContieneAmbosNombres() {
        arrancarConDosJugadores();
        List<JugadorDTO> jugadores = blackboard.getJugadores();
        assertEquals(2, jugadores.size());
        assertTrue(jugadores.stream().anyMatch(j -> "Ana".equals(j.getNombre())));
        assertTrue(jugadores.stream().anyMatch(j -> "Bob".equals(j.getNombre())));
    }


    @Test
    void pedirCartaAumentaManoYAvanzaTurno() {
        arrancarConDosJugadores();
        int manoAntes = blackboard.getManoJugador(0).size();
        enviar(new TipoAccionDTO("PEDIR_CARTA"));

        assertEquals(manoAntes + 1, blackboard.getManoJugador(0).size());
        assertEquals(1, blackboard.getIndiceJugadorActual());
    }


    @Test
    void jugarCartaComodinEsValida() {
        arrancarConDosJugadores();

        CartaDTO comodin = new CartaDTO();
        comodin.setColor(null);
        comodin.setTipoCarta("CAMBIO_COLOR");
        comodin.setNumero(null);
        comodin.setValor("50");

        blackboard.getManoJugador(0);
        TipoAccionDTO accion = new TipoAccionDTO("JUGAR_CARTA", comodin);
        enviar(accion);

        assertTrue(blackboard.isUltimaJugadaValida());
    }


    @Test
    void jugarCartaInvalidaMarcaUltimaJugadaInvalida() {
        arrancarConDosJugadores();

        CartaDTO cima = blackboard.getCartaCima();
        String otroColor = "ROJO".equals(cima.getColor()) ? "AZUL" : "ROJO";
        int otroNumero = (cima.getNumero() == null ? 9 : (cima.getNumero() + 1) % 10);

        CartaDTO invalida = new CartaDTO();
        invalida.setColor(otroColor);
        invalida.setTipoCarta("NUMERICA");
        invalida.setNumero(otroNumero);
        invalida.setValor(String.valueOf(otroNumero));

        enviar(new TipoAccionDTO("JUGAR_CARTA", invalida));

        assertFalse(blackboard.isUltimaJugadaValida());
    }


    @Test
    void eventoRuletaNuloAntesDeGiro() {
        arrancarConDosJugadores();
        assertNull(blackboard.getEventoRuleta());
    }
}
