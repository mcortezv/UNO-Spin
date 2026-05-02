package dto;

import java.util.List;

/**
 * The type Jugador dto.
 */
public class JugadorDTO {
    private String nombre;
    private int numeroAvatar;
    private int cantidadCartas;
    private boolean esTurnoActual;
    private List<CartaDTO> cartasMano;

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
    public JugadorDTO(String nombre, int numeroAvatar, int cantidadCartas, boolean esTurnoActual, List<CartaDTO> cartasMano) {
        this.nombre = nombre;
        this.numeroAvatar = numeroAvatar;
        this.cantidadCartas = cantidadCartas;
        this.esTurnoActual = esTurnoActual;
        this.cartasMano = cartasMano;
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

    public List<CartaDTO> getCartasMano() {
        return cartasMano;
    }

    public void setCartasMano(List<CartaDTO> cartasMano) {
        this.cartasMano = cartasMano;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
