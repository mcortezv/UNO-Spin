package lobby;

import javax.swing.*;

public class NavegadorLobby implements IFlujoMenu, IFlujoCrearPartida, IFlujoCrearJugador {

    private JFrame menu;
    private JFrame crearPartida;
    private JFrame crearJugador;

    public void setPantallas(JFrame menu, JFrame crearPartida, JFrame crearJugador) {
        this.menu = menu;
        this.crearPartida = crearPartida;
        this.crearJugador = crearJugador;
    }

    @Override
    public void mostrarMenu() {
        mostrarSolo(menu);
    }

    @Override
    public void mostrarCrearPartida() {
        mostrarSolo(crearPartida);
    }

    @Override
    public void mostrarCrearJugador() {
        mostrarSolo(crearJugador);
    }

    private void mostrarSolo(JFrame pantalla) {
        ocultar(menu);
        ocultar(crearPartida);
        ocultar(crearJugador);
        if (pantalla != null) {
            pantalla.setVisible(true);
        }
    }

    private void ocultar(JFrame pantalla) {
        if (pantalla != null) {
            pantalla.setVisible(false);
        }
    }
}
