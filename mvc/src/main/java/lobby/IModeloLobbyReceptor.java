package lobby;

import dto.EstadoPartidaDTO;

public interface IModeloLobbyReceptor {
    void procesarRespuesta(EstadoPartidaDTO estado);
    boolean estaEnJuego();
}
