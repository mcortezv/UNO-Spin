package dominio.entidades;
import dto.TipoEventoRuletaDTO;
import java.util.Random;

/**
 * The type Ruleta.
 */
public class Ruleta {
    private TipoEventoRuletaDTO eventoRuleta;

    /**
     * Instantiates a new Ruleta.
     */
    public Ruleta() {}

    /**
     * Instantiates a new Ruleta.
     *
     * @param eventoRuleta the evento ruleta
     */
    public Ruleta(TipoEventoRuletaDTO eventoRuleta) {
        this.eventoRuleta = eventoRuleta;
    }

    /**
     * Gets evento ruleta.
     *
     * @return the evento ruleta
     */
    public TipoEventoRuletaDTO getEventoRuleta() {
        return eventoRuleta;
    }

    /**
     * Sets evento ruleta.
     *
     * @param eventoRuleta the evento ruleta
     */
    public void setEventoRuleta(TipoEventoRuletaDTO eventoRuleta) {
        this.eventoRuleta = eventoRuleta;
    }

    /**
     * Girar tipo evento ruleta.
     *
     * @return the tipo evento ruleta
     */
    public TipoEventoRuletaDTO girar(){
        TipoEventoRuletaDTO[] eventos = TipoEventoRuletaDTO.values();
        int indice = new Random().nextInt(eventos.length);
        return this.eventoRuleta = eventos[indice];
    }
}
