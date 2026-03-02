package mvc.eventos.eventosRuleta;

import java.awt.*;

public class DialogoDescartarPorColor extends DialogoSeleccionColor {
    public DialogoDescartarPorColor(Frame owner) {
        super(owner, "¡DESCARTAR POR COLOR!");
    }

    @Override
    protected String obtenerDescripcion() {
        return "TU MANO DESCARTARA TODAS LAS CARTAS DE ESE COLOR";
    }

    @Override
    protected void alAceptar() {
        resultado = colorSeleccionado;
    }
}
