package dominio;
import dominio.enums.TipoCarta;
import dto.CartaDTO;
import mappers.CartaMapper;
import java.util.ArrayList;
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

    /**
     * Carta dtos list.
     *
     * @return the list
     */
    public List<CartaDTO>cartaDTOS () {
        return  CartaMapper.toDTO(this.cartas);
    }

    /**
     * Tiene carta boolean.
     *
     * @param carta the carta
     * @return the boolean
     */
    public boolean tieneCarta(Carta carta){
        return cartas.contains(carta);
    }

    /**
     * Gets highest.
     *
     * @return the highest
     */
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

    /**
     * Descartar cartas por numero list.
     *
     * @param numeroCartaGanador the numero carta ganador
     * @return the list
     */
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
