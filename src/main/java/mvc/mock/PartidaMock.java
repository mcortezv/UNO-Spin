package mvc.mock;
import dominio.*;
import dominio.enums.EstadoPartida;
import dominio.enums.TipoCarta;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Partida mock.
 */
public class PartidaMock extends Partida {
    /**
     * Instantiates a new Partida mock.
     */
    public PartidaMock() {
        super();
        List<Carta> cartasSebastian = new ArrayList<>();
        cartasSebastian.add(new Carta("AZUL", 2, TipoCarta.NUMERICA, 2));
        cartasSebastian.add(new Carta("ROJO", 5, TipoCarta.NUMERICA, 5));
        cartasSebastian.add(new Carta("AMARILLO", 6, TipoCarta.NUMERICA, 6));
        Mano manoSebastian = new Mano(cartasSebastian);

        List<Carta> cartasGuasave = new ArrayList<>();
        cartasGuasave.add(new Carta("AMARILLO", 9, TipoCarta.NUMERICA, 9));
        cartasGuasave.add(new Carta("VERDE", 7, TipoCarta.NUMERICA, 7));
        cartasGuasave.add(new Carta("AZUL", 5, TipoCarta.NUMERICA, 5));
        Mano manoGuasave = new Mano(cartasGuasave);

        Jugador sebastian = new Jugador(1, List.of("ROJO", "AZUL", "VERDE", "AMARILLO"), 1, manoSebastian, "Sebastian", 7);
        Jugador guasavenia = new Jugador(2, List.of("AMARILLO", "VERDE", "AZUL", "ROJO"), 1, manoGuasave, "Miss Guasave", 5);

        List<Jugador> listaJugadores = new ArrayList<>();
        listaJugadores.add(sebastian);
        listaJugadores.add(guasavenia);

        setJugadores(listaJugadores);
        setEstadoPartida(EstadoPartida.EN_PROCESO);
        setIndiceJugadorActual(0);
        setSentidoHorario(true);
        setTablero(new TableroMock());
    }
}
