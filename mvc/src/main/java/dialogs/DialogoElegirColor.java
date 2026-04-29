package dialogs;
import dialogs.eventosRuleta.DialogoSeleccionColor;
import java.awt.*;

/**
 * The type Dialogo elegir color.
 */
public class DialogoElegirColor extends DialogoSeleccionColor {

    /**
     * Instantiates a new Dialogo elegir color.
     *
     * @param owner the owner
     */
    public DialogoElegirColor(Frame owner) {
        super(owner, "ELEGIR COLOR");
        construirDialogo("ELEGIR COLOR");
    }

    @Override
    protected void alAceptar() { resultado = colorSeleccionado; }
}