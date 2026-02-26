package dto;

public class JugadorDTO {
    private final String nombre;
    private final String avatar;
    private final int cantidadCartas;
    private final boolean esTurnoActual;

    public JugadorDTO(String nombre, String avatar, int cantidadCartas, boolean esTurnoActual) {
        this.nombre = nombre;
        this.avatar = avatar;
        this.cantidadCartas = cantidadCartas;
        this.esTurnoActual = esTurnoActual;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getCantidadCartas() {
        return cantidadCartas;
    }

    public boolean isEsTurnoActual() {
        return esTurnoActual;
    }
}
