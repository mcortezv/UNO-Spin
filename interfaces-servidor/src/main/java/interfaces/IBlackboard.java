package interfaces;
import dto.*;

import dto.CartaDTO;
import dto.EventoFinalizacionDTO;
import dto.JugadorDTO;
import dto.SolicitudUnionDTO;
import dto.TipoEventoRuletaDTO;
import java.util.List;

/**
 * The interface Blackboard.
 */
public interface IBlackboard {

    /**
     * Gets jugadores.
     *
     * @return the jugadores
     */
    List<JugadorDTO> getJugadores();

    /**
     * Gets carta cima.
     *
     * @return the carta cima
     */
    CartaDTO getCartaCima();

    /**
     * Gets mano jugador.
     *
     * @param indiceJugador the indice jugador
     * @return the mano jugador
     */
    List<CartaDTO> getManoJugador(int indiceJugador);

    /**
     * Gets estado partida.
     *
     * @return the estado partida
     */
    String getEstadoPartida();

    /**
     * Gets indice jugador actual.
     *
     * @return the indice jugador actual
     */
    int getIndiceJugadorActual();

    /**
     * Gets evento ruleta.
     *
     * @return the evento ruleta
     */
    TipoEventoRuletaDTO getEventoRuleta();

    /**
     * Is ultima jugada valida boolean.
     *
     * @return the boolean
     */
    boolean isUltimaJugadaValida();

    /**
     * Gets ip of a registered player.
     *
     * @param nombre the player name
     * @return the ip, or null if not registered
     */
    String getIpJugador(String nombre);

    /**
     * Gets port of a registered player.
     *
     * @param nombre the player name
     * @return the port, or 0 if not registered
     */
    int getPuertoJugador(String nombre);

    /**
     * Gets evento finalizacion.
     *
     * @return the evento finalizacion
     */
    EventoFinalizacionDTO getEventoFinalizacion();

    /**
     * Gets solicitudes pendientes.
     *
     * @return the solicitudes pendientes
     */
    List<SolicitudUnionDTO> getSolicitudesPendientes();

    /**
     * Gets ultima accion lobby.
     *
     * @return the ultima accion lobby
     */
    String getUltimaAccionLobby();

    /**
     * Gets nombre solicitud resuelta.
     *
     * @return the nombre solicitud resuelta
     */
    String getNombreSolicitudResuelta();

    /**
     * Gets host nombre.
     *
     * @return the host nombre
     */
    String getHostNombre();

    /**
     * Gets evento abandono.
     *
     * @return the evento abandono
     */
    EventoAbandonoDTO getEventoAbandono();

    public JugadorDTO procesarRegistro(JugadorDTO dto);
}