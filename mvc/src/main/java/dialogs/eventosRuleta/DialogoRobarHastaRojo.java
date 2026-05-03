package dialogs.eventosRuleta;
import java.awt.*;

/**
 * The type Dialogo robar hasta rojo.
 */
public class DialogoRobarHastaRojo extends DialogoColorInformativo {

    /**
     * Instantiates a new Dialogo robar hasta rojo.
     *
     * @param owner the owner
     */
    public DialogoRobarHastaRojo(Frame owner) {
        super(owner, "¡ROBAR CARTAS HASTA ROJO!", Color.RED,"Rojo");
        construirDialogo("¡ROBAR CARTAS HASTA ROJO!");
    }

    @Override
    protected String obtenerDescripcion() { return "TENDRAS QUE ROBAR CARTAS DEL MAZO HASTA OBTENER UNA CARTA CON EL COLOR:"; }

    @Override
    protected void alAceptar() { resultado = "Rojo"; }
}