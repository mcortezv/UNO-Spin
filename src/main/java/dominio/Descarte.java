package dominio;
import dto.CartaDTO;
import mappers.CartaMapper;
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

    /**
     * Obtener cartas dto list.
     *
     * @return the list
     */
    public List<CartaDTO> obtenerCartasDTO(){
        return CartaMapper.toDTO(this.cartas);
    }

    /**
     * Validar carta entrante boolean.
     *
     * @param entrada the entrada
     * @return the boolean
     */
    public boolean validarCartaEntrante(Carta entrada){
        Carta ultima = getUltimaCarta();
        if (ultima.getColor() == entrada.getColor() || ultima.getNumero() == entrada.getNumero() ||
                entrada.getTipoCarta() == ultima.getTipoCarta() || entrada.esComodin()){
            return true;
        }
        return false;
    }

    /**
     * Get ultima carta carta.
     *
     * @return the carta
     */
    public Carta getUltimaCarta(){
        return getCartas().getLast();
    }

}
