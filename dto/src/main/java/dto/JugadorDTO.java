package dto;

/**
 * The type Jugador dto.
 */
public class JugadorDTO {
    private String nombre;
    private int numeroAvatar;
    private int cantidadCartas;
    private boolean esTurnoActual;

    /**
     * Instantiates a new Jugador dto.
     */
    public JugadorDTO() {}

    /**
     * Instantiates a new Jugador dto.
     *
     * @param nombre         the nombre
     * @param numeroAvatar   the numero avatar
     * @param cantidadCartas the cantidad cartas
     * @param esTurnoActual  the es turno actual
     */
    public JugadorDTO(String nombre, int numeroAvatar, int cantidadCartas, boolean esTurnoActual) {
        this.nombre = nombre;
        this.numeroAvatar = numeroAvatar;
        this.cantidadCartas = cantidadCartas;
        this.esTurnoActual = esTurnoActual;
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
     * Gets cantidad cartas.
     *
     * @return the cantidad cartas
     */
    public int getCantidadCartas() {
        return cantidadCartas;
    }

    /**
     * Sets cantidad cartas.
     *
     * @param cantidadCartas the cantidad cartas
     */
    public void setCantidadCartas(int cantidadCartas) {
        this.cantidadCartas = cantidadCartas;
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
     * Is es turno actual boolean.
     *
     * @return the boolean
     */
    public boolean isEsTurnoActual() {
        return esTurnoActual;
    }

    /**
     * Sets es turno actual.
     *
     * @param esTurnoActual the es turno actual
     */
    public void setEsTurnoActual(boolean esTurnoActual) {
        this.esTurnoActual = esTurnoActual;
    }
}
