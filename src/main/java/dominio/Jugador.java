package dominio;
import java.util.List;

/**
 * The type Jugador.
 */
public class Jugador {
    private int id;
    private String nombre;
    private int numeroAvatar;
    private Mano mano;
    private List<String> colorCarta;
    private int puntos;

    /**
     * Instantiates a new Jugador.
     */
    public Jugador() {}

    /**
     * Instantiates a new Jugador.
     *
     * @param numeroAvatar the numero avatar
     * @param colorCarta   the color carta
     * @param id           the id
     * @param mano         the mano
     * @param nombre       the nombre
     * @param puntos       the puntos
     */
    public Jugador(int numeroAvatar, List<String> colorCarta, int id, Mano mano, String nombre, int puntos) {
        this.numeroAvatar = numeroAvatar;
        this.colorCarta = colorCarta;
        this.id = id;
        this.mano = mano;
        this.nombre = nombre;
        this.puntos = puntos;
    }

    /**
     * Gets numero avatar.
     *
     * @return the numero avatar
     */
    public int getNumeroAvatar() {
        return numeroAvatar;
    }

    /**
     * Sets numero avatar.
     *
     * @param numeroAvatar the numero avatar
     */
    public void setNumeroAvatar(int numeroAvatar) {
        this.numeroAvatar = numeroAvatar;
    }

    /**
     * Gets color carta.
     *
     * @return the color carta
     */
    public List<String> getColorCarta() {
        return colorCarta;
    }

    /**
     * Sets color carta.
     *
     * @param colorCarta the color carta
     */
    public void setColorCarta(List<String> colorCarta) {
        this.colorCarta = colorCarta;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets mano.
     *
     * @return the mano
     */
    public Mano getMano() {
        return mano;
    }

    /**
     * Sets mano.
     *
     * @param mano the mano
     */
    public void setMano(Mano mano) {
        this.mano = mano;
    }

    /**
     * Gets nombre.
     *
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets nombre.
     *
     * @param nombre the nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Gets puntos.
     *
     * @return the puntos
     */
    public int getPuntos() {
        return puntos;
    }

    /**
     * Sets puntos.
     *
     * @param puntos the puntos
     */
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}
