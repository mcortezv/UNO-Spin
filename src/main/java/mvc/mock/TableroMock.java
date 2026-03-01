package mvc.mock;
import dominio.*;
import dominio.enums.TipoCarta;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Tablero mock.
 */
public class TableroMock extends Tablero {
    /**
     * Instantiates a new Tablero mock.
     */
    public TableroMock() {
        super();

        ArrayList<Carta> descarte = new ArrayList<Carta>();
        descarte.add(new Carta("ROJO", 5, TipoCarta.NUMERICA, 5));
        setDescarte(new Descarte(descarte));

        ArrayList<Carta> mazo = new ArrayList<Carta>();
        mazo.add(new Carta("ROJO", 0, TipoCarta.NUMERICA, 1));
        mazo.add(new Carta("ROJO", 1, TipoCarta.NUMERICA, 1));
        mazo.add(new Carta("ROJO", 2, TipoCarta.NUMERICA, 2));
        mazo.add(new Carta("ROJO", 3, TipoCarta.NUMERICA, 3));
        mazo.add(new Carta("ROJO", 4, TipoCarta.NUMERICA, 4));
        mazo.add(new Carta("ROJO", 5, TipoCarta.NUMERICA, 5));
        mazo.add(new Carta("ROJO", 6, TipoCarta.NUMERICA, 6));
        mazo.add(new Carta("ROJO", 7, TipoCarta.NUMERICA, 7));
        mazo.add(new Carta("ROJO", 8, TipoCarta.NUMERICA, 8));
        mazo.add(new Carta("ROJO", 9, TipoCarta.NUMERICA, 9));
        setMazo(new Mazo(mazo));

        setRuleta(new Ruleta());
        getRuleta().girar();
    }
}
