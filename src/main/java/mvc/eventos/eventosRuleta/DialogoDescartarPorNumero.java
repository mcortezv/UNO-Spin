package mvc.eventos.eventosRuleta;

import mvc.eventos.DialogoEventoRuleta;
import javax.swing.*;
import java.awt.*;

public class DialogoDescartarPorNumero extends DialogoEventoRuleta {
    private int numeroElegido = -1;

    public DialogoDescartarPorNumero(Frame owner) {
        super(owner, "¡DESCARTAR POR NUMERO!");
        construirDialogo("¡DESCARTAR POR NUMERO!");
    }

    @Override
    protected String obtenerDescripcion() { return "TU MANO DESCARTARA TODAS LAS CARTAS DE ESE NUMERO"; }

    @Override
    protected JPanel crearContenidoCentral() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        panel.setOpaque(false);
        for (int i = 0; i <= 9; i++) {
            final int num = i;
            JButton btn = new JButton(String.valueOf(i));
            btn.setPreferredSize(new Dimension(35, 35));
            btn.addActionListener(e -> numeroElegido = num);
            panel.add(btn);
        }
        return panel;
    }

    @Override
    protected void alAceptar() { System.out.println("Descartando cartas con número: " + numeroElegido); }
}