package dto;

public class TipoAccionDTO {
    private String tipoAccion;
    private CartaDTO cartaDTO;
    private JugadorDTO jugadorDTO;
    private String ip;
    private int puerto;
    private ConfiguracionPartidaDTO configuracion;

    public TipoAccionDTO() {}

    public TipoAccionDTO(String tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public TipoAccionDTO(String tipoAccion, CartaDTO cartaDTO) {
        this.tipoAccion = tipoAccion;
        this.cartaDTO = cartaDTO;
    }

    public String getTipoAccion() { return tipoAccion; }

    public void setTipoAccion(String tipoAccion) { this.tipoAccion = tipoAccion; }

    public CartaDTO getCartaDTO() { return cartaDTO; }
    public void setCartaDTO(CartaDTO cartaDTO) { this.cartaDTO = cartaDTO; }

    public JugadorDTO getJugadorDTO() { return jugadorDTO; }
    public void setJugadorDTO(JugadorDTO jugadorDTO) { this.jugadorDTO = jugadorDTO; }

    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }

    public int getPuerto() { return puerto; }
    public void setPuerto(int puerto) { this.puerto = puerto; }

    public ConfiguracionPartidaDTO getConfiguracion() { return configuracion; }
    public void setConfiguracion(ConfiguracionPartidaDTO configuracion) { this.configuracion = configuracion; }
}
