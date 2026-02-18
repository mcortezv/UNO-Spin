package domain;
import enums.TipoCarta;

/**
 * The type Carta.
 */
public class Carta {
    private Integer numero;
    private TipoCarta tipoCarta;
    private String color;
    private int valor;

    /**
     * Instantiates a new Carta.
     */
    public Carta() {}

    /**
     * Instantiates a new Carta.
     *
     * @param color     the color
     * @param numero    the numero
     * @param tipoCarta the tipo carta
     * @param valor     the valor
     */
    public Carta(String color, Integer numero, TipoCarta tipoCarta, int valor) {
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
    public TipoCarta getTipoCarta() {
        return tipoCarta;
    }

    /**
     * Sets tipo carta.
     *
     * @param tipoCarta the tipo carta
     */
    public void setTipoCarta(TipoCarta tipoCarta) {
        this.tipoCarta = tipoCarta;
    }

    /**
     * Gets valor.
     *
     * @return the valor
     */
    public int getValor() {
        return valor;
    }

    /**
     * Sets valor.
     *
     * @param valor the valor
     */
    public void setValor(int valor) {
        this.valor = valor;
    }
}
