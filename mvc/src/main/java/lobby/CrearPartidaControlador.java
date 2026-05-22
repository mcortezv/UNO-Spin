package lobby;

import dto.ConfiguracionPartidaDTO;

public class CrearPartidaControlador {

    private final IModeloLobby modelo;
    private final IFlujoCrearJugador flujoCrearJugador;

    public CrearPartidaControlador(IModeloLobby modelo,
                                   IFlujoCrearJugador flujoCrearJugador) {
        this.modelo = modelo;
        this.flujoCrearJugador = flujoCrearJugador;
    }

    public void crearPartida(ConfiguracionPartidaDTO configuracion) {
        modelo.setsConfiguracion(configuracion);
        flujoCrearJugador.mostrarCrearJugador();
    }
}
