package dto;

public class AccionJuegoDTO {
    private String accion;
    private CartaDTO carta;

    public AccionJuegoDTO() {}

    public AccionJuegoDTO(String accion, CartaDTO carta) {
        this.accion = accion;
        this.carta = carta;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public CartaDTO getCarta() {
        return carta;
    }

    public void setCarta(CartaDTO carta) {
        this.carta = carta;
    }

}
