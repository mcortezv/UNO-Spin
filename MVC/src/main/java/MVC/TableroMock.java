package MVC;
import domain.Carta;
import domain.Descarte;
import domain.Tablero;
import enums.TipoCarta;
import java.util.ArrayList;
import java.util.List;

public class TableroMock extends Tablero {
    public TableroMock() {
        super();
        Carta cartaCentro = new Carta();
        cartaCentro.setColor("ROJO");
        cartaCentro.setNumero(5);
        cartaCentro.setTipoCarta(TipoCarta.NUMERICA);

        List<Carta> listaDescarte = new ArrayList<>();
        listaDescarte.add(cartaCentro);

        this.setDescarte(new Descarte(listaDescarte));
    }
}
