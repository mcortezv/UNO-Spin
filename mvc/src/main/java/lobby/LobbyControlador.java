package lobby;

import dto.ConfiguracionPartidaDTO;
import dto.JugadorDTO;
import interfaces.IOnJuegoIniciado;

public class LobbyControlador implements ISuscriptorLobby {

    private final IModeloLobby modelo;
    private IOnJuegoIniciado controladorJuego;
    private String miNombre;
    private boolean juegoIniciado = false;


    public LobbyControlador(IModeloLobby modelo) {
        this.modelo = modelo;
    }

    public void setControladorJuego(IOnJuegoIniciado controladorJuego) {
        this.controladorJuego = controladorJuego;
    }

    @Override
    public void actualizar(IModeloLobbyLectura modelo) {
        if (!juegoIniciado && modelo.getEstadoUnion() == EstadoUnion.EN_JUEGO) {
            juegoIniciado = true;
            if (controladorJuego != null) {
                controladorJuego.iniciarJuego(miNombre);
            }
        }
    }

    public void solicitarUnion(JugadorDTO jugador) {
        this.miNombre = jugador.getNombre();
        modelo.solicitarUnion(jugador);
    }

    public void aceptarSolicitud(String nombre) {
        modelo.aceptarSolicitud(nombre);
    }

    public void rechazarSolicitud(String nombre) {
        modelo.rechazarSolicitud(nombre);
    }

    public void solicitarInicio() {
        modelo.solicitarInicio();
    }

    public void confirmarInicio() {
        modelo.confirmarInicio();
    }

    public void negarInicio() {
        modelo.rechazarInicio();
    }

    public void solicitarConfiguracion(ConfiguracionPartidaDTO dto) {
        modelo.setsConfiguracion(dto);
    }

    public void solicitarRegistro(JugadorDTO dto){
    modelo.registrarJugador(dto);
    }

}