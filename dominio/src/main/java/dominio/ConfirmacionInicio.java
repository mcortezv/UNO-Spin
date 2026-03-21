package dominio;

/**
 * The type Confirmacion inicio.
 */
public class ConfirmacionInicio {
    private boolean acepto;

    /**
     * Instantiates a new Confirmacion inicio.
     *
     * @param acepto the acepto
     */
    public ConfirmacionInicio(boolean acepto) {
        this.acepto = acepto;
    }

    /**
     * Is acepto boolean.
     *
     * @return the boolean
     */
    public boolean isAcepto() {
        return acepto;
    }

    /**
     * Sets acepto.
     *
     * @param acepto the acepto
     */
    public void setAcepto(boolean acepto) {
        this.acepto = acepto;
    }
}
