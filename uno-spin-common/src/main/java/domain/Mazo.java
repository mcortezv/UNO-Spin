package domain;
import java.util.List;

/**
 * The type Mazo.
 */
public class Mazo {
    private List<Carta> cartas;

    /**
     * Instantiates a new Mazo.
     */
    public Mazo() {}

    /**
     * Instantiates a new Mazo.
     *
     * @param cartas the cartas
     */
    public Mazo(List<Carta> cartas) {
        this.cartas = cartas;
    }

    /**
     * Gets cartas.
     *
     * @return the cartas
     */
    public List<Carta> getCartas() {
        return cartas;
    }

    /**
     * Sets cartas.
     *
     * @param cartas the cartas
     */
    public void setCartas(List<Carta> cartas) {
        this.cartas = cartas;
    }
}
