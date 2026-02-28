package mvc;
import dominio.Carta;
import dominio.Descarte;
import dominio.Tablero;
import dominio.enums.TipoCarta;
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
