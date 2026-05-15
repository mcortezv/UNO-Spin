package lobby;

import dto.JugadorDTO;
import dto.SolicitudUnionDTO;
import java.util.List;

public interface IModeloLobbyLectura {
    String getEstadoUnion();
    String getMensajeError();
    List<JugadorDTO> getJugadoresEnSala();
    List<SolicitudUnionDTO> getSolicitudesPendientes();
}
