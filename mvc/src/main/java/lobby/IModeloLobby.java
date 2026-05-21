package lobby;

import dto.ConfiguracionPartidaDTO;
import dto.JugadorDTO;

public interface IModeloLobby {

    void solicitarUnion(JugadorDTO jugador);

    void aceptarSolicitud(String nombre);

    void rechazarSolicitud(String nombre);

    void solicitarInicio();
    
    void confirmarInicio();
    
    void rechazarInicio();

    void setsConfiguracion(ConfiguracionPartidaDTO dto);

    void registrarJugador(JugadorDTO jugador);

}