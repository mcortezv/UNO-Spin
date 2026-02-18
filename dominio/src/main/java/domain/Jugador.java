package domain;
import java.util.List;

/**
 * The type Jugador.
 */
public class Jugador {
    private int id;
    private String nombre;
    private String avatar;
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
     * @param avatar     the avatar
     * @param colorCarta the color carta
     * @param id         the id
     * @param mano       the mano
     * @param nombre     the nombre
     * @param puntos     the puntos
     */
    public Jugador(String avatar, List<String> colorCarta, int id, Mano mano, String nombre, int puntos) {
        this.avatar = avatar;
        this.colorCarta = colorCarta;
        this.id = id;
        this.mano = mano;
        this.nombre = nombre;
        this.puntos = puntos;
    }

    /**
     * Gets avatar.
     *
     * @return the avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * Sets avatar.
     *
     * @param avatar the avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
