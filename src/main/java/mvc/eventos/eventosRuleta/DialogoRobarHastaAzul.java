package mvc.eventos.eventosRuleta;
import java.awt.*;

public class DialogoRobarHastaAzul extends DialogoColorInformativo {
    public DialogoRobarHastaAzul(Frame owner) {
        super(owner, "¡ROBAR CARTAS HASTA AZUL!", new Color(0, 170, 255));
        construirDialogo("¡ROBAR CARTAS HASTA AZUL!");
    }
    @Override
    protected String obtenerDescripcion() { return "TENDRAS QUE ROBAR CARTAS DEL MAZO HASTA OBTENER UNA CARTA CON EL COLOR:"; }
    @Override
    protected void alAceptar() { resultado = "Azul"; }
}