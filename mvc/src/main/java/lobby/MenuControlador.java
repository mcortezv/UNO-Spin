package lobby;

public class MenuControlador {

    private final IFlujoCrearPartida flujoCrearPartida;
    private final IFlujoCrearJugador flujoCrearJugador;

    public MenuControlador(IFlujoCrearPartida flujoCrearPartida,
                           IFlujoCrearJugador flujoCrearJugador) {
        this.flujoCrearPartida = flujoCrearPartida;
        this.flujoCrearJugador = flujoCrearJugador;
    }

    public void crearPartida() {
        flujoCrearPartida.mostrarCrearPartida();
    }

    public void unirsePartida() {
        flujoCrearJugador.mostrarCrearJugador();
    }
}
