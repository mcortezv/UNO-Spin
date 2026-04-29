package dto;

public class JugadorDTO {
    private String nombre;
    private int numeroAvatar;
    private int cantidadCartas;
    private boolean esTurnoActual;

    public JugadorDTO() {}

    public JugadorDTO(String nombre, int numeroAvatar, int cantidadCartas, boolean esTurnoActual) {
        this.nombre = nombre;
        this.numeroAvatar = numeroAvatar;
        this.cantidadCartas = cantidadCartas;
        this.esTurnoActual = esTurnoActual;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidadCartas() {
        return cantidadCartas;
    }

    public void setCantidadCartas(int cantidadCartas) {
        this.cantidadCartas = cantidadCartas;
    }

    public int getNumeroAvatar() {
        return numeroAvatar;
    }

    public void setNumeroAvatar(int numeroAvatar) {
        this.numeroAvatar = numeroAvatar;
    }

    public boolean isEsTurnoActual() {
        return esTurnoActual;
    }

    public void setEsTurnoActual(boolean esTurnoActual) {
        this.esTurnoActual = esTurnoActual;
    }
}
