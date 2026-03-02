package mvc.eventos;

import mvc.eventos.eventosRuleta.DialogoSeleccionColor;

import java.awt.*;

public class DialogoElegirColor extends DialogoSeleccionColor {
    public DialogoElegirColor(Frame owner) {
        super(owner, "ELEGIR COLOR");
    }

    @Override
    protected void alAceptar() {
        resultado = colorSeleccionado;
    }
}
