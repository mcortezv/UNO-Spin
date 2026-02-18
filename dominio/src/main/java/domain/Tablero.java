package domain;

/**
 * The type Tablero.
 */
public class Tablero {
    private Mazo mazo;
    private Descarte descarte;
    private Ruleta ruleta;

    /**
     * Instantiates a new Tablero.
     */
    public Tablero() {}

    /**
     * Instantiates a new Tablero.
     *
     * @param descarte the descarte
     * @param mazo     the mazo
     * @param ruleta   the ruleta
     */
    public Tablero(Descarte descarte, Mazo mazo, Ruleta ruleta) {
        this.descarte = descarte;
        this.mazo = mazo;
        this.ruleta = ruleta;
    }

    /**
     * Gets descarte.
     *
     * @return the descarte
     */
    public Descarte getDescarte() {
        return descarte;
    }

    /**
     * Sets descarte.
     *
     * @param descarte the descarte
     */
    public void setDescarte(Descarte descarte) {
        this.descarte = descarte;
    }

    /**
     * Gets mazo.
     *
     * @return the mazo
     */
    public Mazo getMazo() {
        return mazo;
    }

    /**
     * Sets mazo.
     *
     * @param mazo the mazo
     */
    public void setMazo(Mazo mazo) {
        this.mazo = mazo;
    }

    /**
     * Gets ruleta.
     *
     * @return the ruleta
     */
    public Ruleta getRuleta() {
        return ruleta;
    }

    /**
     * Sets ruleta.
     *
     * @param ruleta the ruleta
     */
    public void setRuleta(Ruleta ruleta) {
        this.ruleta = ruleta;
    }
}
