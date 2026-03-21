package eventos.eventosRuleta;

import eventos.DialogoEventoRuleta;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class DialogoSeleccionColor extends DialogoEventoRuleta {

    protected String colorSeleccionado;
    private final Map<JButton, String> botonesColor = new LinkedHashMap<>();

    public DialogoSeleccionColor(Frame owner, String titulo) {
        super(owner, titulo);
    }

    @Override
    protected JPanel crearContenidoCentral() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panel.setOpaque(false);

        Object[][] colores = {
                {Color.RED, "Rojo"}, {new Color(0,150,0), "Verde"},
                {new Color(255,200,0), "Amarillo"}, {new Color(0,170,255), "Azul"}
        };

        ButtonGroup group = new ButtonGroup();
        for (Object[] c : colores) {
            JButton btn = crearCirculo((Color) c[0], 45);
            btn.addActionListener(e -> {
                colorSeleccionado = (String) c[1];
                botonesColor.keySet().forEach(b -> b.setBorder(null));
                btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            });
            botonesColor.put(btn, (String) c[1]);
            panel.add(btn);
        }
        return panel;
    }

    protected JButton crearCirculo(Color color, int size) {
        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.fillOval(2, 2, getWidth()-4, getHeight()-4);
                g2.dispose();
            }
        };
        btn.setPreferredSize(new Dimension(size, size));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(true);
        btn.setBorder(null);
        return btn;
    }
}

