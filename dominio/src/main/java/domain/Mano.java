package domain;
import dto.CartaDTO;
import enums.TipoCarta;
import mappers.MapperCarta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public List<CartaDTO>cartaDTOS () {
        return  MapperCarta.toDTO(this.cartas);
    }

    public boolean tieneCarta(Carta carta){
        return cartas.contains(carta);
    }

    public Carta getHighest() {
        if (cartas == null || cartas.isEmpty()) {
            return null;
        }
        Carta cartaMasAlta = null;
        for (Carta carta : cartas) {
            if (carta.getTipoCarta() == TipoCarta.NUMERICA) {
                if (cartaMasAlta == null || carta.getNumero() > cartaMasAlta.getNumero()) {
                    cartaMasAlta = carta;
                }
            }

            if (cartaMasAlta == null) {
                cartaMasAlta = cartas.get(0);
            }
        }
        return cartaMasAlta;
    }

    public List<Carta> descartarCartasPorNumero(int numeroCartaGanador){
        List<Carta> cartasADescartar = new ArrayList<>();
        for (Carta carta : cartas) {
            if (carta.getTipoCarta() == TipoCarta.NUMERICA && carta.getNumero() == numeroCartaGanador) {
                cartasADescartar.add(carta);
            }
        }
        cartas.removeIf(c -> c.getTipoCarta() == TipoCarta.NUMERICA && c.getNumero() == numeroCartaGanador);
        return cartasADescartar;
    }
}
