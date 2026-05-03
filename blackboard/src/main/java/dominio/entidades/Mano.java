package dominio.entidades;
import dominio.entidades.enums.TipoCarta;
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
    public Mano() {
        cartas = new ArrayList<>();
    }

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
     * Tiene carta boolean.
     *
     * @param carta the carta
     * @return the boolean
     */
    public boolean tieneCarta(Carta carta) {
        return cartas.contains(carta);
    }

    /**
     * Regresa la carta más alta de la mano del juegador.
     * Recorre las cartas del jugador y las compara para conseguir la más alta, si la carta más alta no fue encontrada
     * entonces regresa la primera carta
     *
     * @return La carta más alta de la mano
     */
    public Carta getMasAlta() {
        if (cartas == null || cartas.isEmpty()) return null;

        Carta cartaMasAlta = null;
        for (Carta carta : cartas) {
            if (carta.getTipoCarta() == TipoCarta.NUMERICA || carta.getTipoCarta() == TipoCarta.NUMERO_SPIN) {
                /*
                if (cartaMasAlta == null || carta.getNumero() > cartaMasAlta.getNumero()) {
                    cartaMasAlta = carta;
                }
                Si la cartaMasAlta es null y lo manejas con OR, lo que va a pasar es que si es nula no va a revisar
                las carta más alta, simplemente va a revisar que es null y va a regresar un null y no estamos manejando
                los nulos
                 */
                if (cartaMasAlta == null || carta.getNumero() > cartaMasAlta.getNumero()) {
                    cartaMasAlta = carta;
                }
            }
        }
        return cartaMasAlta != null ? cartaMasAlta : cartas.get(0);
    }

    /**
     * Descarta las cartas de la mano que tengan el mismo número.
     * Recorre todas las cartas de la mano y compara con el número del parametro y las elimina y regresa las cartas
     * eliminadas que fueron seleccionadas
     *
     * @param numero Numero de las cartas el cual se compara y se desecha
     * @return lista de cartas de las cuales fueron deshechas
     */
    public List<Carta> descartarCartasPorNumero(int numero) {
        List<Carta> aDescartar = new ArrayList<>();
        for (Carta carta : cartas) {
            if ((carta.getTipoCarta() == TipoCarta.NUMERICA || carta.getTipoCarta() == TipoCarta.NUMERO_SPIN)
                    && carta.getNumero() != null
                    && carta.getNumero() == numero) {
                aDescartar.add(carta);
            }
        }
        cartas.removeAll(aDescartar);
        return aDescartar;
    }
}