package dto;
import java.util.List;

public class PartidaDTO {
    private List<CartaDTO> descarte;
    private List<CartaDTO> manoJugador;
    private CartaDTO cartaCima;
    private String nombreTurnoActual;
    private List<JugadorDTO> jugadoresRivales;
    private boolean turnoActivo;
    private boolean spinActivo;

    public PartidaDTO() {
    }

    public PartidaDTO(List<CartaDTO> descarte, List<CartaDTO> manoJugador, CartaDTO cartaCima, String nombreTurnoActual, List<JugadorDTO> jugadoresRivales, boolean spinActivo, boolean turnoActivo) {
        this.descarte = descarte;
        this.manoJugador = manoJugador;
        this.cartaCima = cartaCima;
        this.nombreTurnoActual = nombreTurnoActual;
        this.jugadoresRivales = jugadoresRivales;
        this.spinActivo = spinActivo;
        this.turnoActivo = turnoActivo;
    }

    public List<CartaDTO> getDescarte() {
        return descarte;
    }

    public void setDescarte(List<CartaDTO> descarte) {
        this.descarte = descarte;
    }

    public List<CartaDTO> getManoJugador() {
        return manoJugador;
    }

    public void setManoJugador(List<CartaDTO> manoJugador) {
        this.manoJugador = manoJugador;
    }

    public CartaDTO getCartaCima() {
        return cartaCima;
    }

    public void setCartaCima(CartaDTO cartaCima) {
        this.cartaCima = cartaCima;
    }

    public List<JugadorDTO> getJugadoresRivales() {
        return jugadoresRivales;
    }

    public void setJugadoresRivales(List<JugadorDTO> jugadoresRivales) {
        this.jugadoresRivales = jugadoresRivales;
    }

    public String getNombreTurnoActual() {
        return nombreTurnoActual;
    }

    public void setNombreTurnoActual(String nombreTurnoActual) {
        this.nombreTurnoActual = nombreTurnoActual;
    }

    public boolean isTurnoActivo() {
        return turnoActivo;
    }

    public void setTurnoActivo(boolean turnoActivo) {
        this.turnoActivo = turnoActivo;
    }

    public boolean isSpinActivo() {
        return spinActivo;
    }

    public void setSpinActivo(boolean spinActivo) {
        this.spinActivo = spinActivo;
    }
}
