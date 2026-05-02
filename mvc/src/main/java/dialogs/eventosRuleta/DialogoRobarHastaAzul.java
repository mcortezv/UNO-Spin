package dialogs.eventosRuleta;
import java.awt.*;

/**
 * The type Dialogo robar hasta azul.
 */
public class DialogoRobarHastaAzul extends DialogoColorInformativo {
    /**
     * Instantiates a new Dialogo robar hasta azul.
     *
     * @param owner the owner
     */
    public DialogoRobarHastaAzul(Frame owner) {
        super(owner, "¡ROBAR CARTAS HASTA AZUL!", new Color(0, 170, 255));
        construirDialogo("¡ROBAR CARTAS HASTA AZUL!");
    }

    @Override
    protected String obtenerDescripcion() { return "TENDRAS QUE ROBAR CARTAS DEL MAZO HASTA OBTENER UNA CARTA CON EL COLOR:"; }

    @Override
    protected void alAceptar() { resultado = "Azul"; }
}