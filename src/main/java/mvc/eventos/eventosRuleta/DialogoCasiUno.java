package mvc.eventos.eventosRuleta;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DialogoCasiUno extends DialogoCartas {
    public DialogoCasiUno(Frame owner, List<ImageIcon> cartas) {
        super(owner, "¡CASI UNO!", cartas, false);
    }

    @Override
    protected String obtenerDescripcion() {
        return "¡QUEDATE CON DOS CARTAS!";
    }

    @Override
    protected void alAceptar() {
    }
}
