package dto;

/**
 * The type Tipo accion dto.
 */
public class TipoAccionDTO {
    private String tipoAccion;
    private CartaDTO cartaDTO;
    private JugadorDTO jugadorDTO;
    private String ip;
    private int puerto;
    private ConfiguracionPartidaDTO configuracion;
    private String resultadoEvento;

    /**
     * Instantiates a new Tipo accion dto.
     */
    public TipoAccionDTO() {}

    /**
     * Instantiates a new Tipo accion dto.
     *
     * @param tipoAccion the tipo accion
     */
    public TipoAccionDTO(String tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    /**
     * Instantiates a new Tipo accion dto.
     *
     * @param tipoAccion the tipo accion
     * @param cartaDTO   the carta dto
     */
    public TipoAccionDTO(String tipoAccion, CartaDTO cartaDTO) {
        this.tipoAccion = tipoAccion;
        this.cartaDTO = cartaDTO;
    }

    /**
     * Gets tipo accion.
     *
     * @return the tipo accion
     */
    public String getTipoAccion() { return tipoAccion; }

    /**
     * Sets tipo accion.
     *
     * @param tipoAccion the tipo accion
     */
    public void setTipoAccion(String tipoAccion) { this.tipoAccion = tipoAccion; }

    /**
     * Gets carta dto.
     *
     * @return the carta dto
     */
    public CartaDTO getCartaDTO() { return cartaDTO; }

    /**
     * Sets carta dto.
     *
     * @param cartaDTO the carta dto
     */
    public void setCartaDTO(CartaDTO cartaDTO) { this.cartaDTO = cartaDTO; }

    /**
     * Gets jugador dto.
     *
     * @return the jugador dto
     */
    public JugadorDTO getJugadorDTO() { return jugadorDTO; }

    /**
     * Sets jugador dto.
     *
     * @param jugadorDTO the jugador dto
     */
    public void setJugadorDTO(JugadorDTO jugadorDTO) { this.jugadorDTO = jugadorDTO; }

    /**
     * Gets ip.
     *
     * @return the ip
     */
    public String getIp() { return ip; }

    /**
     * Sets ip.
     *
     * @param ip the ip
     */
    public void setIp(String ip) { this.ip = ip; }

    /**
     * Gets puerto.
     *
     * @return the puerto
     */
    public int getPuerto() { return puerto; }

    /**
     * Sets puerto.
     *
     * @param puerto the puerto
     */
    public void setPuerto(int puerto) { this.puerto = puerto; }

    /**
     * Gets configuracion.
     *
     * @return the configuracion
     */
    public ConfiguracionPartidaDTO getConfiguracion() { return configuracion; }

    /**
     * Sets configuracion.
     *
     * @param configuracion the configuracion
     */
    public void setConfiguracion(ConfiguracionPartidaDTO configuracion) { this.configuracion = configuracion; }

    public String getResultadoEvento() { return resultadoEvento; }

    public void setResultadoEvento(String resultadoEvento) { this.resultadoEvento = resultadoEvento; }
}
