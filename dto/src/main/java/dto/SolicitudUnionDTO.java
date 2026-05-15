package dto;

public class SolicitudUnionDTO {

    private JugadorDTO jugadorDTO;
    private String ip;
    private int puerto;
    private String estado;

    public SolicitudUnionDTO() {}

    public SolicitudUnionDTO(JugadorDTO jugadorDTO, String ip, int puerto, String estado) {
        this.jugadorDTO = jugadorDTO;
        this.ip = ip;
        this.puerto = puerto;
        this.estado = estado;
    }

    public JugadorDTO getJugadorDTO() { return jugadorDTO; }
    public void setJugadorDTO(JugadorDTO jugadorDTO) { this.jugadorDTO = jugadorDTO; }

    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }

    public int getPuerto() { return puerto; }
    public void setPuerto(int puerto) { this.puerto = puerto; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
