package dto;

import dominio.enums.TipoCarta;

/**
 * The type Carta dto.
 */
public class CartaDTO {
    private Integer numero;
    private String tipoCarta;
    private String color;
    private String valor;

    /**
     * Instantiates a new Carta dto.
     */
    public CartaDTO() {
    }

    /**
     * Instantiates a new Carta dto.
     *
     * @param color     the color
     * @param numero    the numero
     * @param tipoCarta the tipo carta
     * @param valor     the valor
     */
    public CartaDTO(String color, Integer numero, String tipoCarta, String valor) {
        this.color = color;
        this.numero = numero;
        this.tipoCarta = tipoCarta;
        this.valor = valor;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets color.
     *
     * @param color the color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Gets numero.
     *
     * @return the numero
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * Sets numero.
     *
     * @param numero the numero
     */
    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    /**
     * Gets tipo carta.
     *
     * @return the tipo carta
     */
    public String getTipoCarta() {
        return tipoCarta;
    }

    /**
     * Sets tipo carta.
     *
     * @param tipoCarta the tipo carta
     */
    public void setTipoCarta(String tipoCarta) {
        this.tipoCarta = tipoCarta;
    }

    /**
     * Gets valor.
     *
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * Sets valor.
     *
     * @param valor the valor
     */
    public void setValor(String valor) {
        this.valor = valor;
    }
}
