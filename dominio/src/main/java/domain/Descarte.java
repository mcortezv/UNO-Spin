package domain;
import dto.CartaDTO;
import mappers.MapperCarta;

import java.util.List;

/**
 * The type Descarte.
 */
public class Descarte {
    private List<Carta> cartas;

    /**
     * Instantiates a new Descarte.
     */
    public Descarte() {}

    /**
     * Instantiates a new Descarte.
     *
     * @param cartas the cartas
     */
    public Descarte(List<Carta> cartas) {
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

    public List<CartaDTO> obtenerCartasDTO(){
        return MapperCarta.toDTO(this.cartas);
    }
}
