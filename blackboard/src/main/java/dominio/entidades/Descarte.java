package dominio.entidades;
import dominio.entidades.enums.TipoCarta;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Descarte.
 */
public class Descarte {

    private List<Carta> cartas;

    /**
     * Instantiates a new Descarte.
     */
    public Descarte() {
        cartas = new ArrayList<>();
    }

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
     * Gets ultima carta.
     *
     * @return the ultima carta
     */
    public Carta getUltimaCarta() {
        return cartas.getLast();
    }

    /**
     * Valida la jugada con la carta entrante comparando el color, el numero y
     * si es comodín o no para hacer valida la carta
     *
     * @param entrada Carta entrante a validar la jugada que se realiza
     * @return true si la jugada es valida y false si la jugada es invalida
     */
    public boolean validarCartaEntrante(Carta entrada) {
        if (entrada == null) {
            return false;
        }
        if (entrada.esComodin()) {
            return true;
        }
        Carta cima = getUltimaCarta();

        if (Objects.equals(cima.getColor(), entrada.getColor())) {
            return true;
        }

        boolean cimaConNumero = cima.getNumero() != null;
        boolean entradaConNumero = entrada.getNumero() != null;
        if (cimaConNumero && entradaConNumero && Objects.equals(cima.getNumero(), entrada.getNumero())) {
            return true;
        }

        boolean entradaEsEspecial = entrada.getTipoCarta() != TipoCarta.NUMERICA && entrada.getTipoCarta() != TipoCarta.NUMERO_SPIN && !entrada.esComodin();
        return entradaEsEspecial && entrada.getTipoCarta() == cima.getTipoCarta();
    }
}