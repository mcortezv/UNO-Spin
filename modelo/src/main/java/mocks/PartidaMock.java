package mocks;

import domain.Carta;
import domain.Jugador;
import domain.Mano;
import domain.Partida;
import enums.TipoCarta;

import java.util.ArrayList;
import java.util.List;

public class PartidaMock extends Partida {
    public PartidaMock() {
        super();

        Jugador jugadorFalso = new Jugador();
        jugadorFalso.setNombre("Jugador de Prueba");
        List<Carta> cartasMano = new ArrayList<>();
        cartasMano.add(new Carta("AZUL", 2, TipoCarta.NUMERICA, 2));
        cartasMano.add(new Carta("VERDE", 9, TipoCarta.NUMERICA, 9));
        jugadorFalso.setMano(new Mano(cartasMano));
        List<Jugador> listaJugadores = new ArrayList<>();
        listaJugadores.add(jugadorFalso);

        this.setJugadores(listaJugadores);
        this.setIndiceJugadorActual(0);
    }
}
