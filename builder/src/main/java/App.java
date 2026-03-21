import dominio.entidades.*;
import dominio.entidades.enums.TipoCarta;
import dominio.interfaces.IDominio;
import mock.ModeloVistaJugador;
import mvc.Controlador;
import mvc.Modelo;
import op.UITurnoJugador;

import java.util.ArrayList;
import java.util.List;

/**
 * The type App.
 */
public class App {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        IDominio dominio = new Partida();
        List<Carta> manoSebas = new ArrayList<>();
        manoSebas.add(new Carta("ROJO", 2, TipoCarta.NUMERICA, 2));
        manoSebas.add(new Carta("ROJO", 3, TipoCarta.NUMERO_SPIN, 3));

        List<Carta> manoGuasave = new ArrayList<>();
        manoGuasave.add(new Carta("AZUL", 8, TipoCarta.NUMERICA, 8));
        manoGuasave.add(new Carta("AMARILLO", 1, TipoCarta.NUMERO_SPIN, 1));

        Jugador j1 = new Jugador(1, new ArrayList<>(), 0, new Mano(manoSebas), "Sebas", 0);
        Jugador j2 = new Jugador(2, new ArrayList<>(), 1, new Mano(manoGuasave), "Miss Guasave", 0);

        List<Jugador> listaJugadores = new ArrayList<>();
        listaJugadores.add(j1);
        listaJugadores.add(j2);
        List<Carta> cartasDescarte = new ArrayList<>();
        cartasDescarte.add(new Carta("ROJO", 5, TipoCarta.NUMERICA, 5));

        List<Carta> cartasMazo = new ArrayList<>();
        cartasMazo.add(new Carta("AZUL", 9, TipoCarta.NUMERICA, 9));
        cartasMazo.add(new Carta("VERDE", 4, TipoCarta.NUMERICA, 4));
        cartasMazo.add(new Carta("AMARILLO", 2, TipoCarta.NUMERICA, 2));
        cartasMazo.add(new Carta("ROJO", 7, TipoCarta.NUMERICA, 7));
        cartasMazo.add(new Carta("AZUL", 1, TipoCarta.NUMERICA, 1));
        cartasMazo.add(new Carta("ROJO", 5, TipoCarta.NUMERICA, 5));
        cartasMazo.add(new Carta("VERDE", 8, TipoCarta.NUMERICA, 8));

        Tablero tableroFalso = new Tablero(new Descarte(cartasDescarte), new Mazo(cartasMazo), new Ruleta());

        dominio.iniciarPartida(listaJugadores, tableroFalso);

        Modelo modelo = new Modelo(dominio);

        ModeloVistaJugador modeloSebas = new ModeloVistaJugador(0, modelo);
        ModeloVistaJugador modeloGuasave = new ModeloVistaJugador(1, modelo);

        Controlador controladorSebas = new Controlador(modeloSebas);
        Controlador controladorGuasave = new Controlador(modeloGuasave);

        UITurnoJugador ventanaSebas = new UITurnoJugador(controladorSebas, modeloSebas, List.of(0, 0));
        UITurnoJugador ventanaGuasave = new UITurnoJugador(controladorGuasave, modeloGuasave, List.of(880, 400));

        modeloSebas.subscribe(ventanaSebas);
        modeloGuasave.subscribe(ventanaGuasave);

        ventanaSebas.update(modeloSebas);
        ventanaGuasave.update(modeloGuasave);

        ventanaSebas.setVisible(true);
        ventanaGuasave.setVisible(true);
    }
}
