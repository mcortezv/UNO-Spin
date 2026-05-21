package dto;

import java.util.List;

/**
 * The type Jugador dto.
 */
public class JugadorDTO {
    private String nombre;
    private int numeroAvatar;
    private int colorCartas; // Nueva variable: Almacena el ID del color seleccionado (1 al 8)
    private List<String> coloresVisuales;
    
    private int cantidadCartas;
    private int puntos;
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
     * @param colorCartas    the color cartas (1 al 8)
     * @param cantidadCartas the cantidad cartas
     * @param esTurnoActual  the es turno actual
     */
    public JugadorDTO(String nombre, int numeroAvatar, int colorCartas, int cantidadCartas, boolean esTurnoActual) {
        this.nombre = nombre;
        this.numeroAvatar = numeroAvatar;
        this.colorCartas = colorCartas;
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

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
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
     * Gets color cartas.
     *
     * @return the color cartas
     */
    public int getColorCartas() {
        return colorCartas;
    }

    /**
     * Sets color cartas.
     *
     * @param colorCartas the color cartas to set
     */
    public void setColorCartas(int colorCartas) {
        this.colorCartas = colorCartas;
    }

    public List<String> getColoresVisuales() {
        return coloresVisuales;
    }

    public void setColoresVisuales(List<String> coloresVisuales) {
        this.coloresVisuales = coloresVisuales;
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
