package dto;

public class SolicitudUnionDTO {

    private JugadorDTO jugadorDTO;
    private String estado;

    public SolicitudUnionDTO() {}

    public SolicitudUnionDTO(JugadorDTO jugadorDTO, String estado) {
        this.jugadorDTO = jugadorDTO;
        this.estado = estado;
    }

    public JugadorDTO getJugadorDTO() { return jugadorDTO; }
    public void setJugadorDTO(JugadorDTO jugadorDTO) { this.jugadorDTO = jugadorDTO; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
