package mvc.mock;
import dominio.*;
import dominio.enums.TipoCarta;
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

        setDescarte(new Descarte(List.of(new Carta("ROJO", 5, TipoCarta.NUMERICA, 5))));

        setMazo(new Mazo(List.of(new Carta("ROJO", 1, TipoCarta.NUMERICA, 1),
                new Carta("ROJO", 2, TipoCarta.NUMERICA, 2),
                new Carta("ROJO", 3, TipoCarta.NUMERICA, 3),
                new Carta("ROJO", 4, TipoCarta.NUMERICA, 4),
                new Carta("ROJO", 5, TipoCarta.NUMERICA, 5),
                new Carta("ROJO", 6, TipoCarta.NUMERICA, 6),
                new Carta("ROJO", 7, TipoCarta.NUMERICA, 7),
                new Carta("ROJO", 8, TipoCarta.NUMERICA, 8),
                new Carta("ROJO", 9, TipoCarta.NUMERICA, 9),
                new Carta("ROJO", 0, TipoCarta.NUMERICA, 0))));

        setRuleta(new Ruleta());
        getRuleta().girar();
    }
}
