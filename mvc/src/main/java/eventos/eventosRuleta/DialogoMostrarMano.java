package eventos.eventosRuleta;
import dto.CartaDTO;
import java.awt.Frame;
import java.util.List;

/**
 * The type Dialogo mostrar mano.
 */
public class DialogoMostrarMano extends DialogoCartas {
    private final String nombreJugador;

    /**
     * Instantiates a new Dialogo mostrar mano.
     *
     * @param owner   the owner
     * @param jugador the jugador
     * @param cartas  the cartas
     */
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