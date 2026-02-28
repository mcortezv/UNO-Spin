package dominio;
import dominio.enums.TipoEventoRuleta;
import java.util.Random;

/**
 * The type Ruleta.
 */
public class Ruleta {
    private TipoEventoRuleta eventoRuleta;

    /**
     * Instantiates a new Ruleta.
     */
    public Ruleta() {}

    /**
     * Instantiates a new Ruleta.
     *
     * @param eventoRuleta the evento ruleta
     */
    public Ruleta(TipoEventoRuleta eventoRuleta) {
        this.eventoRuleta = eventoRuleta;
    }

    /**
     * Gets evento ruleta.
     *
     * @return the evento ruleta
     */
    public TipoEventoRuleta getEventoRuleta() {
        return eventoRuleta;
    }

    /**
     * Sets evento ruleta.
     *
     * @param eventoRuleta the evento ruleta
     */
    public void setEventoRuleta(TipoEventoRuleta eventoRuleta) {
        this.eventoRuleta = eventoRuleta;
    }

    public TipoEventoRuleta girar(){
        TipoEventoRuleta[] eventos = TipoEventoRuleta.values();
        int indice = new Random().nextInt(eventos.length);
        return this.eventoRuleta = eventos[indice];
    }
}
