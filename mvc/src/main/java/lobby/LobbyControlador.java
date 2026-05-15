package lobby;

import dto.JugadorDTO;

public class LobbyControlador {

    private final IModeloLobby modelo;

    public LobbyControlador(IModeloLobby modelo) {
        this.modelo = modelo;
    }

    public void solicitarUnion(String nombre, int avatar) {
        JugadorDTO jugador = new JugadorDTO();
        jugador.setNombre(nombre.trim());
        jugador.setNumeroAvatar(avatar);
        modelo.solicitarUnion(jugador);
    }

    public void aceptarSolicitud(String nombre) {
        modelo.aceptarSolicitud(nombre);
    }

    public void rechazarSolicitud(String nombre) {
        modelo.rechazarSolicitud(nombre);
    }

    public void confirmarInicio() {
        modelo.confirmarInicio();
    }
}
