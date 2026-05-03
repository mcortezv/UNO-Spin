package dialogs.eventosRuleta;

import dialogs.DialogoEventoRuleta;
import javax.swing.*;
import java.awt.*;

/**
 * The type Dialogo color informativo.
 */
public class DialogoColorInformativo extends DialogoEventoRuleta {
    private final Color colorMostrar;
    private final String nombreColor;

    /**
     * Instantiates a new Dialogo color informativo.
     *
     * @param owner       the owner
     * @param titulo      the titulo
     * @param color       the color
     * @param nombreColor el nombre del color para el resultado
     */
    public DialogoColorInformativo(Frame owner, String titulo, Color color, String nombreColor) {
        super(owner, titulo);
        this.colorMostrar = color;
        this.nombreColor = nombreColor;
        construirDialogo(titulo);
    }

    @Override
    protected String obtenerDescripcion() {
        return "TENDRAS QUE ROBAR CARTAS DEL MAZO HASTA OBTENER UNA CARTA CON EL COLOR:";
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

    @Override
    protected void alAceptar() {
        resultado = nombreColor;
    }
}