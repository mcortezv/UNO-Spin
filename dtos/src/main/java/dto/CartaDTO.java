package dto;

public class CartaDTO {
    private String valor;
    private String color;

    public CartaDTO(String color, String valor) {
        this.color = color;
        this.valor = valor;
    }

    public CartaDTO() {}

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
