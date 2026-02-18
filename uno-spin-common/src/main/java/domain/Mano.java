package domain;
import java.util.List;

/**
 * The type Mano.
 */
public class Mano {
    private List<Carta> cartas;

    /**
     * Instantiates a new Mano.
     */
    public Mano() {}

    /**
     * Instantiates a new Mano.
     *
     * @param cartas the cartas
     */
    public Mano(List<Carta> cartas) {
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
