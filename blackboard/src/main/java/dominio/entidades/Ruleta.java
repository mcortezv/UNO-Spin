<<<<<<<< HEAD:blackboard/src/main/java/dominio/Ruleta.java
λpackage dominio;
import dominio.enums.TipoEventoRuleta;

========
package dominio.entidades;
import dto.TipoEventoRuletaDTO;
>>>>>>>> origin/main:blackboard/src/main/java/dominio/entidades/Ruleta.java
import java.util.Random;

/**
 * The type Ruleta.
 */
public class Ruleta {
    private TipoEventoRuletaDTO eventoRuleta;

    /**
     * Instantiates a new Ruleta.
     */
    public Ruleta() {
    }

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
<<<<<<<< HEAD:blackboard/src/main/java/dominio/Ruleta.java
    public TipoEventoRuleta girar() {
        TipoEventoRuleta[] eventos = TipoEventoRuleta.values();
========
    public TipoEventoRuletaDTO girar(){
        TipoEventoRuletaDTO[] eventos = TipoEventoRuletaDTO.values();
>>>>>>>> origin/main:blackboard/src/main/java/dominio/entidades/Ruleta.java
        int indice = new Random().nextInt(eventos.length);
        return this.eventoRuleta = eventos[indice];
    }
}
