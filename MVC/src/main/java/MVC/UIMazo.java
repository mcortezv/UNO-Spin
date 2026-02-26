package MVC;
import MVC.interfaces.IComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UIMazo extends JPanel implements IComponent {
    private static final int CAPAS = 5;
    private static final int OFFSET = 2;
    private static final int ARCO = 14;

    private static final Color COLOR_CARTA = new Color(22, 22, 22);
    private static final Color COLOR_BORDE = new Color(170, 170, 170);
    private static final Color COLOR_SOMBRA = new Color(0, 0, 0, 70);
    private static final Color COLOR_HOVER = new Color(255, 220, 0, 80);
    private static final Color COLOR_HOVER_B = new Color(255, 220, 0);

    private final Image logoUno;
    private boolean hover = false;
    private Runnable onPedirCarta;

    public UIMazo() {
        this.logoUno = CargadorAssets.getInstance().getReverso().getImage();
        int w = UICarta.ANCHO + (CAPAS - 1) * OFFSET + 4;
        int h = UICarta.ALTO + (CAPAS - 1) * OFFSET + 4;
        setPreferredSize(new Dimension(w, h));
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registrarListeners();
    }

    private void registrarListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (onPedirCarta != null) onPedirCarta.run();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g2.setColor(COLOR_SOMBRA);
        g2.fillRoundRect((CAPAS - 1) * OFFSET + 3, (CAPAS - 1) * OFFSET + 3,
                UICarta.ANCHO, UICarta.ALTO, ARCO, ARCO);

        for (int capa = CAPAS - 1; capa >= 1; capa--) {
            int ox = capa * OFFSET;
            int oy = capa * OFFSET;
            pintarCartaReversoSimple(g2, ox, oy);
        }

        pintarCartaFrontal(g2);

        g2.dispose();
    }

    private void pintarCartaReversoSimple(Graphics2D g2, int x, int y) {
        g2.setColor(COLOR_CARTA);
        g2.fillRoundRect(x, y, UICarta.ANCHO, UICarta.ALTO, ARCO, ARCO);
        g2.setColor(COLOR_BORDE);
        g2.setStroke(new BasicStroke(1f));
        g2.drawRoundRect(x, y, UICarta.ANCHO, UICarta.ALTO, ARCO, ARCO);
    }

    private void pintarCartaFrontal(Graphics2D g2) {
        g2.setColor(COLOR_CARTA);
        g2.fillRoundRect(0, 0, UICarta.ANCHO, UICarta.ALTO, ARCO, ARCO);

        g2.setColor(new Color(210, 210, 210));
        g2.setStroke(new BasicStroke(1.8f));
        g2.drawRoundRect(0, 0, UICarta.ANCHO, UICarta.ALTO, ARCO, ARCO);

        int mg = 8;
        g2.drawImage(logoUno, mg, mg, UICarta.ANCHO - mg * 2, UICarta.ALTO - mg * 2, null);

        if (hover) {
            g2.setColor(COLOR_HOVER);
            g2.fillRoundRect(0, 0, UICarta.ANCHO, UICarta.ALTO, ARCO, ARCO);
            g2.setColor(COLOR_HOVER_B);
            g2.setStroke(new BasicStroke(2.5f));
            g2.drawRoundRect(0, 0, UICarta.ANCHO, UICarta.ALTO, ARCO, ARCO);
        }
    }

    public void setOnPedirCarta(Runnable callback) {
        this.onPedirCarta = callback;
    }

    @Override
    public void execute() {
    }
}
