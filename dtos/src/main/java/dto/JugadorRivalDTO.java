package dto;

public class JugadorRivalDTO {

    private String nombre;
    private int numeroAvatar;
    private int cantidadCartas;
    private boolean esTurno;

    public JugadorRivalDTO(String nombre, int numeroAvatar, int cantidadCartas, boolean esTurno) {
        this.nombre = nombre;
        this.numeroAvatar = numeroAvatar;
        this.cantidadCartas = cantidadCartas;
        this.esTurno = esTurno;
    }

    public JugadorRivalDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroAvatar() {
        return numeroAvatar;
    }

    public void setNumeroAvatar(int numeroAvatar) {
        this.numeroAvatar = numeroAvatar;
    }

    public int getCantidadCartas() {
        return cantidadCartas;
    }

    public void setCantidadCartas(int cantidadCartas) {
        this.cantidadCartas = cantidadCartas;
    }

    public boolean isEsTurno() {
        return esTurno;
    }

    public void setEsTurno(boolean esTurno) {
        this.esTurno = esTurno;
    }
}

