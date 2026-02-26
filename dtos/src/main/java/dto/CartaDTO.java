package dto;

public class CartaDTO {
    private Integer numero;
    private String tipoCarta;
    private String color;
    private int valor;

    public CartaDTO(Integer numero, String tipoCarta, String color, int valor) {
        this.numero = numero;
        this.tipoCarta = tipoCarta;
        this.color = color;
        this.valor = valor;
    }

    public CartaDTO() {

    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getTipoCarta() {
        return tipoCarta;
    }

    public void setTipoCarta(String tipoCarta) {
        this.tipoCarta = tipoCarta;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
