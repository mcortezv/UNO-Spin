package interfaces;
import dto.TipoEventoRuletaDTO;
import dto.CartaDTO;
import dto.EventoAbandonoDTO;
import dto.EventoFinalizacionDTO;
import dto.JugadorDTO;
import java.util.List;

/**
 * The interface Modelo lectura.
 */
public interface IModeloLectura {

    /**
     * Gets descarte.
     *
     * @return the descarte
     */
    List<CartaDTO> getDescarte();

    /**
     * Gets mano jugador.
     *
     * @return the mano jugador
     */
    List<CartaDTO> getManoJugador();

    /**
     * Gets carta cima.
     *
     * @return the carta cima
     */
    CartaDTO getCartaCima();

    /**
     * Gets nombre turno actual.
     *
     * @return the nombre turno actual
     */
    String getNombreTurnoActual();

    /**
     * Gets jugadores rivales.
     *
     * @return the jugadores rivales
     */
    List<JugadorDTO> getJugadoresRivales();

    /**
     * Gets todos los jugadores.
     *
     * @return the todos los jugadores
     */
    List<JugadorDTO> getTodosLosJugadores();

    /**
     * Is turno activo boolean.
     *
     * @return the boolean
     */
    boolean isTurnoActivo();

    /**
     * Is spin activo boolean.
     *
     * @return the boolean
     */
    boolean isSpinActivo();

    /**
     * Gets evento ruleta actual.
     *
     * @return the evento ruleta actual
     */
    TipoEventoRuletaDTO getEventoRuletaActual();

    /**
     * Is ultima jugada valida boolean.
     *
     * @return the boolean
     */
    boolean isUltimaJugadaValida();

    /**
     * Is seleccion color pendiente boolean.
     *
     * @return the boolean
     */
    boolean isSeleccionColorPendiente();

    /**
     * Is seleccion color propia boolean.
     *
     * @return the boolean
     */
    boolean isSeleccionColorPropia();

    /**
     * Is evento ruleta propio boolean.
     *
     * @return the boolean
     */
    boolean isEventoRuletaPropio();

    /**
     * Carta cima es castigo boolean.
     *
     * @return the boolean
     */
    boolean cartaCimaEsCastigo();

    /**
     * Tiene castigo pendiente local boolean.
     *
     * @return the boolean
     */
    boolean tieneCastigoPendienteLocal();

    /**
     * Gets cartas pendientes castigo local.
     *
     * @return the cartas pendientes castigo local
     */
    int getCartasPendientesCastigoLocal();

    /**
     * Puede usar mazo boolean.
     *
     * @return the boolean
     */
    boolean puedeUsarMazo();

    /**
     * Puede intentar jugar carta boolean.
     *
     * @return the boolean
     */
    boolean puedeIntentarJugarCarta();

    /**
     * Puede jugar carta boolean.
     *
     * @param carta the carta
     * @return the boolean
     */
    boolean puedeJugarCarta(CartaDTO carta);

    /**
     * Gets vista activa.
     *
     * @return the vista activa
     */
    boolean getVistaActiva();

    /**
     * Gets abandono.
     *
     * @return the abandono
     */
    boolean getAbandono();

    /**
     * Gets mi nombre.
     *
     * @return the mi nombre
     */
    String getMiNombre();

    /**
     * Gets evento abandono.
     *
     * @return the evento abandono
     */
    EventoAbandonoDTO getEventoAbandono();
    EventoFinalizacionDTO getEventoFinalizacion();
    boolean isYaVote();
    boolean isVotoEnviado();
    boolean isVotacionPendiente();
}
