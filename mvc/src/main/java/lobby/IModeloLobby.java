package lobby;

import dto.JugadorDTO;

public interface IModeloLobby {

    void solicitarUnion(JugadorDTO jugador);

    void aceptarSolicitud(String nombre);

    void rechazarSolicitud(String nombre);

    void confirmarInicio();
}
