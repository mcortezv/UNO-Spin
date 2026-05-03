package dialogs.eventosRuleta;
import dto.CartaDTO;
import java.awt.Frame;
import java.util.List;

/**
 * The type Dialogo casi uno.
 */
public class DialogoCasiUno extends DialogoCartas {

    /**
     * Instantiates a new Dialogo casi uno.
     *
     * @param owner  the owner
     * @param cartas the cartas
     */
    public DialogoCasiUno(Frame owner, List<CartaDTO> cartas) {
        super(owner, "¡CASI UNO!", cartas, false);
        construirDialogo("¡CASI UNO!");
    }

    @Override
    protected String obtenerDescripcion() { return "¡QUEDATE CON DOS CARTAS!"; }

    @Override
    protected void alAceptar() { }
}
