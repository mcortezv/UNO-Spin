package mvc;
import mvc.interfaces.IComponent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import javax.swing.Timer;

/**
 * The type Ui ruleta.
 */
public class UIRuleta extends JPanel implements IComponent {

    private static final int DIAMETRO = 140;
    private static final int SEGMENTOS = 8;
    private static final int TICK_MS = 16;
    private static final double VEL_INIT = Math.toRadians(14);
    private static final double FRICCION = 0.983;
    private static final double VEL_STOP = Math.toRadians(0.3);

    private static final Color[] COLORES_SEG = {
            new Color(204, 37, 37),
            new Color(30, 100, 195),
            new Color(46, 153, 56),
            new Color(218, 180, 0),
            new Color(204, 37, 37),
            new Color(30, 100, 195),
            new Color(46, 153, 56),
            new Color(218, 180, 0),
    };

    private static final Color COLOR_ANILLO = new Color(35, 18, 5);
    private static final Color COLOR_CENTRO = new Color(220, 185, 0);
    private static final Color COLOR_CENTRO_BORDE = new Color(40, 20, 5);
    private static final Color COLOR_FLECHA = new Color(30, 15, 5);
    private static final Color COLOR_SOMBRA = new Color(0, 0, 0, 80);
    private static final Color COLOR_HOVER = new Color(255, 240, 180, 50);
    private boolean isActive = false;
    private double anguloActual = 0;
    private double velocidad = 0;
    private boolean girando = false;
    private Timer timerGiro;
    private Runnable onGiroCompleto;

    private boolean hover = false;


    /**
     * Instantiates a new Ui ruleta.
     */
    public UIRuleta() {
        int tam = DIAMETRO + 24;
        setPreferredSize(new Dimension(tam, tam + 30));
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registrarListeners();
    }

    private void registrarListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (isActive){
                    hover = true;
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (isActive){
                    hover = false;
                    repaint();
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (isActive){
                    girar();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int cx = getWidth() / 2;
        int cy = (getHeight() - 26) / 2;
        int r = DIAMETRO / 2;

        pintarSombra(g2, cx, cy, r);
        pintarSegmentos(g2, cx, cy, r);
//        pintarSeparadores(g2, cx, cy, r);
        pintarAnilloExterior(g2, cx, cy, r);
        pintarCentro(g2, cx, cy);
        pintarFlecha(g2, cx, cy, r);

        if (hover && !girando) {
            pintarHoverGlow(g2, cx, cy, r);
        }

        g2.dispose();
    }

    private void pintarSombra(Graphics2D g2, int cx, int cy, int r) {
        g2.setColor(COLOR_SOMBRA);
        g2.fillOval(cx - r + 5, cy - r + 5, DIAMETRO, DIAMETRO);
    }

    private void pintarSegmentos(Graphics2D g2, int cx, int cy, int r) {
        double angPorSeg = 360.0 / SEGMENTOS;
        for (int i = 0; i < SEGMENTOS; i++) {
            double inicioGrados = Math.toDegrees(anguloActual) + i * angPorSeg;
            g2.setColor(COLORES_SEG[i]);
            g2.fill(new Arc2D.Double(
                    cx - r, cy - r, DIAMETRO, DIAMETRO,
                    inicioGrados, angPorSeg,
                    Arc2D.PIE
            ));
        }
    }
//
//    private void pintarSeparadores(Graphics2D g2, int cx, int cy, int r) {
//        g2.setColor(COLOR_ANILLO);
//        g2.setStroke(new BasicStroke(1.5f));
//        double angPorSeg = 2 * Math.PI / SEGMENTOS;
//        for (int i = 0; i < SEGMENTOS; i++) {
//            double angulo = anguloActual + i * angPorSeg;
//            int x2 = cx + (int) (r * Math.cos(angulo));
//            int y2 = cy + (int) (r * Math.sin(angulo));
//            g2.drawLine(cx, cy, x2, y2);
//        }
//    }

    private void pintarAnilloExterior(Graphics2D g2, int cx, int cy, int r) {
        g2.setColor(COLOR_ANILLO);
        g2.setStroke(new BasicStroke(4f));
        g2.drawOval(cx - r, cy - r, DIAMETRO, DIAMETRO);
    }

    private void pintarCentro(Graphics2D g2, int cx, int cy) {
        int rc = 18;
        g2.setColor(COLOR_CENTRO);
        g2.fillOval(cx - rc, cy - rc, rc * 2, rc * 2);
        g2.setColor(COLOR_CENTRO_BORDE);
        g2.setStroke(new BasicStroke(2.5f));
        g2.drawOval(cx - rc, cy - rc, rc * 2, rc * 2);
    }

    private void pintarFlecha(Graphics2D g2, int cx, int cy, int r) {
        int largo = r - 14;
        int fx2 = cx + (int) (largo * Math.cos(anguloActual));
        int fy2 = cy + (int) (largo * Math.sin(anguloActual));
        int fxNeg = cx - (int) ((largo * 0.25) * Math.cos(anguloActual));
        int fyNeg = cy - (int) ((largo * 0.25) * Math.sin(anguloActual));

        g2.setColor(COLOR_FLECHA);
        g2.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(fxNeg, fyNeg, fx2, fy2);

        double perp = anguloActual + Math.PI / 2;
        int ax1 = fx2 - (int) (10 * Math.cos(anguloActual)) + (int) (6 * Math.cos(perp));
        int ay1 = fy2 - (int) (10 * Math.sin(anguloActual)) + (int) (6 * Math.sin(perp));
        int ax2 = fx2 - (int) (10 * Math.cos(anguloActual)) - (int) (6 * Math.cos(perp));
        int ay2 = fy2 - (int) (10 * Math.sin(anguloActual)) - (int) (6 * Math.sin(perp));
        g2.fillPolygon(new int[]{fx2, ax1, ax2}, new int[]{fy2, ay1, ay2}, 3);
    }

    private void pintarHoverGlow(Graphics2D g2, int cx, int cy, int r) {
        g2.setColor(COLOR_HOVER);
        g2.fillOval(cx - r, cy - r, DIAMETRO, DIAMETRO);
    }

    /**
     * Girar.
     */
    public void girar() {
        if (girando) return;
        girando = true;
        velocidad = VEL_INIT + Math.random() * Math.toRadians(6);

        timerGiro = new Timer(TICK_MS, e -> {
            anguloActual += velocidad;
            velocidad *= FRICCION;
            repaint();

            if (velocidad < VEL_STOP) {
                ((Timer) e.getSource()).stop();
                girando = false;
                if (onGiroCompleto != null) {
                    onGiroCompleto.run();
                }
            }
        });
        timerGiro.start();
    }

    /**
     * Sets on giro completo.
     *
     * @param callback the callback
     */
    public void setOnGiroCompleto(Runnable callback) {
        this.onGiroCompleto = callback;
    }

    /**
     * Is girando boolean.
     *
     * @return the boolean
     */
    public boolean isGirando() {
        return girando;
    }

    @Override
    public void execute() {
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
