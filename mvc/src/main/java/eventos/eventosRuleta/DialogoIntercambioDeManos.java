package eventos.eventosRuleta;

import eventos.DialogoEventoRuleta;
import javax.swing.*;
import java.awt.*;

public class DialogoIntercambioDeManos extends DialogoEventoRuleta {

    public DialogoIntercambioDeManos(Frame owner) {
        super(owner, "¡INTERCAMBIO DE MANOS!");
        construirDialogo("¡INTERCAMBIO DE MANOS!");
    }

    @Override
    protected String obtenerDescripcion() { return "TODOS LOS JUGADORES PASAN SUS<br>CARTAS AL JUGADOR DE SU IZQUIERDA"; }

    @Override
    protected JPanel crearContenidoCentral() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);
        JLabel icono = new JLabel(new ImageIcon("recursos/intercambio_icono.png"));
        panel.add(icono);
        return panel;
    }

    @Override
    protected void alAceptar() { }
}