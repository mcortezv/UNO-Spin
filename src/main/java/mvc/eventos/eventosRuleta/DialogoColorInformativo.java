package mvc.eventos.eventosRuleta;

import mvc.eventos.DialogoEventoRuleta;

import javax.swing.*;
import java.awt.*;

public abstract class DialogoColorInformativo extends DialogoEventoRuleta {

    private final Color colorMostrar;

    public DialogoColorInformativo(Frame owner, String titulo, Color color) {
        super(owner, "");
        this.colorMostrar = color;
    }

    @Override
    protected JPanel crearContenidoCentral() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);

        JPanel circulo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(colorMostrar);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        circulo.setPreferredSize(new Dimension(80, 80));
        circulo.setOpaque(false);
        panel.add(circulo);
        return panel;
    }
}

