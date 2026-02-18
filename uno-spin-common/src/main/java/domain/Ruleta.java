package domain;
import enums.TipoEventoRuleta;

/**
 * The type Ruleta.
 */
public class Ruleta {
    private EventoRuleta eventoRuleta;

    /**
     * Instantiates a new Ruleta.
     */
    public Ruleta() {}

    /**
     * Instantiates a new Ruleta.
     *
     * @param eventoRuleta the evento ruleta
     */
    public Ruleta(EventoRuleta eventoRuleta) {
        this.eventoRuleta = eventoRuleta;
    }

    /**
     * Gets evento ruleta.
     *
     * @return the evento ruleta
     */
    public EventoRuleta getEventoRuleta() {
        return eventoRuleta;
    }

    /**
     * Sets evento ruleta.
     *
     * @param eventoRuleta the evento ruleta
     */
    public void setEventoRuleta(EventoRuleta eventoRuleta) {
        this.eventoRuleta = eventoRuleta;
    }
}
