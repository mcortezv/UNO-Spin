package MVC.styles;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Button extends JButton {
    private final Color fondoBase;

    public Button(String texto, Color fondoBase) {
        super(texto);
        this.fondoBase = fondoBase;

        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 13));
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Cambia de color dependiendo de si el ratón está encima o presionando
        Color fondo = getModel().isPressed()
                ? fondoBase.darker()
                : getModel().isRollover()
                ? fondoBase.brighter()
                : fondoBase;

        g2.setColor(fondo);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);

        g2.setColor(new Color(255, 255, 255, 60));
        g2.setStroke(new BasicStroke(1f));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);

        g2.dispose();

        // Dibuja el texto del botón encima de nuestro fondo
        super.paintComponent(g);
    }
}
