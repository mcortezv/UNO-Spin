package eventos.eventosRuleta;
import dto.CartaDTO;
import java.awt.Frame;
import java.util.List;

/**
 * The type Dialogo descartar carta.
 */
public class DialogoDescartarCarta extends DialogoCartas {

    /**
     * Instantiates a new Dialogo descartar carta.
     *
     * @param owner  the owner
     * @param cartas the cartas
     */
    public DialogoDescartarCarta(Frame owner, List<CartaDTO> cartas) {
        super(owner, "¡DESCARTAR CARTA!", cartas, true);
        construirDialogo("¡DESCARTAR CARTA!");
    }

    @Override
    protected String obtenerTextoBoton() { return "DESCARTAR"; }

    @Override
    protected void alAceptar() {
        if (cartaSeleccionadaIdx != -1) {
            resultado = cartaSeleccionadaIdx;
        }
    }
}