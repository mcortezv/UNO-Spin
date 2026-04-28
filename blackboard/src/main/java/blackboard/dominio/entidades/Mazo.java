package blackboard.dominio.entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type Mazo.
 */
public class Mazo {
    private List<Carta> cartas;

    /**
     * Instantiates a new Mazo.
     */
    public Mazo() {
        cartas = new ArrayList<>();
    }

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

    /**
     * Mezclar.
     */
    public void mezclar() {
        Collections.shuffle(this.cartas);
    }

    /**
     * Robar carta carta.
     * Toma la ultima carta de la bajara y la descarta
     *
     * @return the last carta
     * @throws Exception the exception Cuando el mazo de las cartas se encuentra vacio, significa que no hay más cartas
     * en el mazo
     *
     */
    public Carta robarCarta() throws Exception {
        if (this.cartas.isEmpty()) {
            throw new Exception("El mazo no tiene cartas");
        }
        int ultimaPosicion = cartas.size() - 1;
        return cartas.remove(ultimaPosicion);
    }

}
