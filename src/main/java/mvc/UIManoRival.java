package mvc;
import mvc.interfaces.IComponent;
import javax.swing.*;
import java.awt.*;

public class UIManoRival extends JPanel implements IComponent {
    public enum Orientacion {HORIZONTAL, VERTICAL}

    private static final int SOLAPAMIENTO = 16;
    private static final int MAX_VISIBLES = 4;
    private static final int ARCO = 12;
    private static final Color COLOR_CARTA_REVERSO = new Color(22, 22, 22);
    private static final Color COLOR_BORDE_REVERSO = new Color(180, 180, 180);
    private static final Color COLOR_BADGE_FONDO = new Color(210, 35, 35);
    private static final Color COLOR_BADGE_TEXTO = Color.WHITE;
    private static final Font FUENTE_BADGE = new Font("Arial", Font.BOLD, 12);

    private int cantidadCartas;
    private final Orientacion orientacion;
    private final Image logoUno;

    public UIManoRival(int cantidadCartas, Orientacion orientacion) {
        this.cantidadCartas = cantidadCartas;
        this.orientacion = orientacion;
        this.logoUno = CargadorAssets.getInstance().getReverso().getImage();
        setOpaque(false);
        recalcularTamano();
    }

    private void recalcularTamano() {
        int visibles = Math.max(1, Math.min(cantidadCartas, MAX_VISIBLES));
        if (orientacion == Orientacion.HORIZONTAL) {
            int ancho = UICarta.ANCHO + SOLAPAMIENTO * (visibles - 1) + 4;
            setPreferredSize(new Dimension(ancho, UICarta.ALTO + 4));
        } else {
            int alto = UICarta.ALTO + SOLAPAMIENTO * (visibles - 1) + 4;
            setPreferredSize(new Dimension(UICarta.ANCHO + 4, alto));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        int visibles = Math.max(1, Math.min(cantidadCartas, MAX_VISIBLES));

        for (int i = visibles - 1; i >= 0; i--) {
            int x = orientacion == Orientacion.HORIZONTAL ? i * SOLAPAMIENTO : 2;
            int y = orientacion == Orientacion.HORIZONTAL ? 2 : i * SOLAPAMIENTO;
            pintarCartaReverso(g2, x, y, i == 0);
        }

        pintarBadge(g2);

        g2.dispose();
    }

    private void pintarCartaReverso(Graphics2D g2, int x, int y, boolean esFrente) {
        g2.setColor(new Color(0, 0, 0, 50));
        g2.fillRoundRect(x + 3, y + 3, UICarta.ANCHO - 2, UICarta.ALTO - 2, ARCO, ARCO);

        g2.setColor(COLOR_CARTA_REVERSO);
        g2.fillRoundRect(x, y, UICarta.ANCHO - 2, UICarta.ALTO - 2, ARCO, ARCO);

        g2.setColor(esFrente ? new Color(220, 220, 220) : COLOR_BORDE_REVERSO);
        g2.setStroke(new BasicStroke(esFrente ? 1.8f : 1.0f));
        g2.drawRoundRect(x, y, UICarta.ANCHO - 2, UICarta.ALTO - 2, ARCO, ARCO);

        if (esFrente) {
            int mg = 7;
            g2.drawImage(logoUno, x + mg, y + mg, UICarta.ANCHO - mg * 2 - 2, UICarta.ALTO - mg * 2 - 2, null);
        }
    }

    private void pintarBadge(Graphics2D g2) {
        String texto = String.valueOf(cantidadCartas);
        g2.setFont(FUENTE_BADGE);
        FontMetrics fm = g2.getFontMetrics();
        int tw = fm.stringWidth(texto);
        int th = fm.getAscent();
        int bw = Math.max(tw + 12, 24);
        int bh = th + 6;

        int bx = getWidth() - bw - 1;
        int by = 1;

        g2.setColor(COLOR_BADGE_FONDO);
        g2.fillRoundRect(bx, by, bw, bh, bh, bh);

        g2.setColor(new Color(255, 255, 255, 150));
        g2.setStroke(new BasicStroke(1f));
        g2.drawRoundRect(bx, by, bw, bh, bh, bh);

        g2.setColor(COLOR_BADGE_TEXTO);
        g2.drawString(texto, bx + (bw - tw) / 2, by + th + 1);
    }

    public void setCantidadCartas(int cantidad) {
        this.cantidadCartas = cantidad;
        recalcularTamano();
        revalidate();
        repaint();
    }

    @Override public void execute() {}
}
