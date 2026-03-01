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
    public JugadorDTO(){}

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
       return nombre;}

    /**
     * Gets cantidad cartas.
     *
     * @return the cantidad cartas
     */
    public int getCantidadCartas() {
       return cantidadCartas;
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
     * Is es turno actual boolean.
     *
     * @return the boolean
     */
    public boolean isEsTurnoActual() {
       return esTurnoActual;
   }
}
