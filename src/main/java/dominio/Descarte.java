package dominio;
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

    public boolean validarCartaEntrante(Carta entrada){
        Carta ultima = getUltimaCarta();
        if (ultima.getColor() == entrada.getColor() || ultima.getNumero() == entrada.getNumero() || entrada.getTipoCarta() == ultima.getTipoCarta()){
            return true;
        }else if(entrada.esComodin()){
            return true;
        }
        return false;
    }

    public Carta getUltimaCarta(){
        return getCartas().getLast();
    }

}
