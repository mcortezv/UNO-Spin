package eventos.eventosRuleta;

import eventos.DialogoEventoRuleta;
import javax.swing.*;
import java.awt.*;

public class DialogoDescartarPorNumero extends DialogoEventoRuleta {
    private int numeroElegido = -1;

    public DialogoDescartarPorNumero(Frame owner) {
        super(owner, "¡DESCARTAR POR NUMERO!");
        construirDialogo("¡DESCARTAR POR NUMERO!");
    }

    @Override
    protected String obtenerDescripcion() {
        return "TU MANO DESCARTARA TODAS LAS CARTAS DE ESE NUMERO";
    }

    @Override
    protected JPanel crearContenidoCentral() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        panel.setOpaque(false);

        ButtonGroup grupo = new ButtonGroup();
        for (int i = 0; i <= 9; i++) {
            final int num = i;
            JToggleButton btn = new JToggleButton(String.valueOf(i));
            btn.setPreferredSize(new Dimension(38, 38));
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.addActionListener(e -> {
                numeroElegido = num;
                btn.setBackground(new Color(255, 200, 0));
            });
            grupo.add(btn);
            panel.add(btn);
        }
        return panel;
    }

    @Override
    protected void alAceptar() {
        resultado = numeroElegido;
    }
}