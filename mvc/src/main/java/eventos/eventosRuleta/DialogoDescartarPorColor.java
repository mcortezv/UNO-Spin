package eventos.eventosRuleta;
import java.awt.*;

/**
 * The type Dialogo descartar por color.
 */
public class DialogoDescartarPorColor extends DialogoSeleccionColor {

    /**
     * Instantiates a new Dialogo descartar por color.
     *
     * @param owner the owner
     */
    public DialogoDescartarPorColor(Frame owner) {
        super(owner, "¡DESCARTAR POR COLOR!");
        construirDialogo("¡DESCARTAR POR COLOR!");
    }

    @Override
    protected String obtenerDescripcion() { return "TU MANO DESCARTARA TODAS LAS CARTAS DE ESE COLOR"; }

    @Override
    protected void alAceptar() {
        if (colorSeleccionado != null) {
            resultado = colorSeleccionado;
        }
    }
}