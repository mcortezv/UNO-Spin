package dto;

/**
 * The type Carta dto.
 */
public class CartaDTO {
    private String valor;
    private String color;

    /**
     * Instantiates a new Carta dto.
     *
     * @param color the color
     * @param valor the valor
     */
    public CartaDTO(String color, String valor) {
        this.color = color;
        this.valor = valor;
    }

    /**
     * Instantiates a new Carta dto.
     */
    public CartaDTO() {}

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
