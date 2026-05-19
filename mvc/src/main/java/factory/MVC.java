package factory;

import dto.EstadoPartidaDTO;
import interfaces.IReceptor;
import interfaces.ISerializer;
import lobby.IModeloLobbyReceptor;
import mvc.Modelo;

public class MVC implements IReceptor {

    private final ISerializer serializer;
    private final IModeloLobbyReceptor lobbyModelo;
    private final Modelo gameModelo;

    public MVC(ISerializer serializer, IModeloLobbyReceptor lobbyModelo, Modelo gameModelo) {
        this.serializer = serializer;
        this.lobbyModelo = lobbyModelo;
        this.gameModelo = gameModelo;
    }

    @Override
    public void recibirMensaje(String json) {
        EstadoPartidaDTO estado = serializer.deserialize(json, EstadoPartidaDTO.class);
        if (lobbyModelo.estaEnJuego()) {
            gameModelo.actualizarEstado(estado);
        } else {
            lobbyModelo.procesarRespuesta(estado);
            if (lobbyModelo.estaEnJuego()) {
                gameModelo.actualizarEstado(estado);
            }
        }
    }
}
