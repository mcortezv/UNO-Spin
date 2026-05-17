package styles;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelFondo extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RadialGradientPaint gradiente = new RadialGradientPaint(
                getWidth() / 2f, getHeight() / 2f, getWidth() / 1.4f,
                new float[]{0f, 1f},
                new Color[]{new Color(160, 20, 20), new Color(60, 0, 0)}
        );
        g2d.setPaint(gradiente);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    public static void habilitarArrastre(JFrame frame, Component c) {
        MouseAdapter ma = new MouseAdapter() {
            private Point inicio;
            @Override public void mousePressed(MouseEvent e)  { inicio = e.getLocationOnScreen(); }
            @Override public void mouseDragged(MouseEvent e)  {
                Point p = e.getLocationOnScreen();
                frame.setLocation(frame.getX() + p.x - inicio.x, frame.getY() + p.y - inicio.y);
                inicio = p;
            }
        };
        c.addMouseListener(ma);
        c.addMouseMotionListener(ma);
    }
}
