package eventos.eventosRuleta;

import dto.CartaDTO;
import java.awt.Frame;
import java.util.List;

public class DialogoMostrarMano extends DialogoCartas {
    private final String nombreJugador;

    public DialogoMostrarMano(Frame owner, String jugador, List<CartaDTO> cartas) {
        super(owner, "¡MOSTRAR MANO!", cartas, false);
        this.nombreJugador = jugador;
        construirDialogo("¡MOSTRAR MANO!");
    }

    @Override
    protected String obtenerDescripcion() { return "CARTAS DE " + nombreJugador.toUpperCase(); }
    @Override
    protected void alAceptar() { }
}