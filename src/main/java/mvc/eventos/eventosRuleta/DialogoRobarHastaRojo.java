package mvc.eventos.eventosRuleta;
import java.awt.*;

public class DialogoRobarHastaRojo extends DialogoColorInformativo {
    public DialogoRobarHastaRojo(Frame owner) {
        super(owner, "¡ROBAR CARTAS HASTA ROJO!", Color.RED);
        construirDialogo("¡ROBAR CARTAS HASTA ROJO!");
    }
    @Override
    protected String obtenerDescripcion() { return "TENDRAS QUE ROBAR CARTAS DEL MAZO HASTA OBTENER UNA CARTA CON EL COLOR:"; }
    @Override
    protected void alAceptar() { resultado = "Rojo"; }
}