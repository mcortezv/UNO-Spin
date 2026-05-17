package dto;
import java.util.List;

/**
 * The type Estado partida dto.
 */
public class EstadoPartidaDTO {
    private int indiceJugadorActual;
    private String estadoPartida;
    private CartaDTO cartaCima;
    private List<JugadorDTO> jugadores;
    private List<CartaDTO> manoJugador;
    private boolean esTuTurno;
    private TipoEventoRuletaDTO eventoRuletaActivo;
    private boolean ultimaJugadaValida;
    private List<SolicitudUnionDTO> solicitudesPendientes;
    private String resultadoSolicitud;
    private EventoFinalizacionDTO eventoFinalizacion;

    /**
     * Instantiates a new Estado partida dto.
     */
    public EstadoPartidaDTO() {}

    /**
     * Instantiates a new Estado partida dto.
     *
     * @param indiceJugadorActual the indice jugador actual
     * @param estadoPartida       the estado partida
     * @param cartaCima           the carta cima
     * @param jugadores           the jugadores
     * @param manoJugador         the mano jugador
     * @param esTuTurno           the es tu turno
     * @param eventoRuletaActivo  the evento ruleta activo
     * @param ultimaJugadaValida  the ultima jugada valida
     */
    public EstadoPartidaDTO(int indiceJugadorActual, String estadoPartida, CartaDTO cartaCima, List<JugadorDTO> jugadores, List<CartaDTO> manoJugador, boolean esTuTurno, TipoEventoRuletaDTO eventoRuletaActivo, boolean ultimaJugadaValida) {
        this.indiceJugadorActual = indiceJugadorActual;
        this.estadoPartida = estadoPartida;
        this.cartaCima = cartaCima;
        this.jugadores = jugadores;
        this.manoJugador = manoJugador;
        this.esTuTurno = esTuTurno;
        this.eventoRuletaActivo = eventoRuletaActivo;
        this.ultimaJugadaValida = ultimaJugadaValida;
    }

    /**
     * Gets indice jugador actual.
     *
     * @return the indice jugador actual
     */
    public int getIndiceJugadorActual() {
        return indiceJugadorActual;
    }

    /**
     * Sets indice jugador actual.
     *
     * @param i the
     */
    public void setIndiceJugadorActual(int i) {
        this.indiceJugadorActual = i;
    }

    /**
     * Gets estado partida.
     *
     * @return the estado partida
     */
    public String getEstadoPartida() {
        return estadoPartida;
    }

    /**
     * Sets estado partida.
     *
     * @param estadoPartida the estado partida
     */
    public void setEstadoPartida(String estadoPartida) {
        this.estadoPartida = estadoPartida;
    }

    /**
     * Gets carta cima.
     *
     * @return the carta cima
     */
    public CartaDTO getCartaCima() {
        return cartaCima;
    }

    /**
     * Sets carta cima.
     *
     * @param cartaCima the carta cima
     */
    public void setCartaCima(CartaDTO cartaCima) {
        this.cartaCima = cartaCima;
    }

    /**
     * Gets jugadores.
     *
     * @return the jugadores
     */
    public List<JugadorDTO> getJugadores() {
        return jugadores;
    }

    /**
     * Sets jugadores.
     *
     * @param jugadores the jugadores
     */
    public void setJugadores(List<JugadorDTO> jugadores) {
        this.jugadores = jugadores;
    }

    /**
     * Gets mano jugador.
     *
     * @return the mano jugador
     */
    public List<CartaDTO> getManoJugador() {
        return manoJugador;
    }

    /**
     * Sets mano jugador.
     *
     * @param manoJugador the mano jugador
     */
    public void setManoJugador(List<CartaDTO> manoJugador) {
        this.manoJugador = manoJugador;
    }

    /**
     * Is es tu turno boolean.
     *
     * @return the boolean
     */
    public boolean isEsTuTurno() {
        return esTuTurno;
    }

    /**
     * Sets es tu turno.
     *
     * @param esTuTurno the es tu turno
     */
    public void setEsTuTurno(boolean esTuTurno) {
        this.esTuTurno = esTuTurno;
    }

    /**
     * Gets evento ruleta activo.
     *
     * @return the evento ruleta activo
     */
    public TipoEventoRuletaDTO getEventoRuletaActivo() {
        return eventoRuletaActivo;
    }

    /**
     * Sets evento ruleta activo.
     *
     * @param eventoRuletaActivo the evento ruleta activo
     */
    public void setEventoRuletaActivo(TipoEventoRuletaDTO eventoRuletaActivo) {
        this.eventoRuletaActivo = eventoRuletaActivo;
    }

    /**
     * Is ultima jugada valida boolean.
     *
     * @return the boolean
     */
    public boolean isUltimaJugadaValida() {
        return ultimaJugadaValida;
    }

    /**
     * Sets ultima jugada valida.
     *
     * @param ultimaJugadaValida the ultima jugada valida
     */
    public void setUltimaJugadaValida(boolean ultimaJugadaValida) {
        this.ultimaJugadaValida = ultimaJugadaValida;
    }

    public List<SolicitudUnionDTO> getSolicitudesPendientes() { return solicitudesPendientes; }
    public void setSolicitudesPendientes(List<SolicitudUnionDTO> solicitudesPendientes) {
        this.solicitudesPendientes = solicitudesPendientes;
    }

    public String getResultadoSolicitud() { return resultadoSolicitud; }
    public void setResultadoSolicitud(String resultadoSolicitud) {
        this.resultadoSolicitud = resultadoSolicitud;
    }

    public EventoFinalizacionDTO getEventoFinalizacion() {
        return eventoFinalizacion;
    }

    public void setEventoFinalizacion(EventoFinalizacionDTO eventoFinalizacion) {
        this.eventoFinalizacion = eventoFinalizacion;
    }
    
    
    
}
