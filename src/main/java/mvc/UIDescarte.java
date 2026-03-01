package mvc;
import mvc.interfaces.IComponent;
import dto.CartaDTO;
import javax.swing.*;
import java.awt.*;

/**
 * The type Ui descarte.
 */
public class UIDescarte extends JPanel implements IComponent{
    private static final int ANCHO  = UICarta.ANCHO + 16;
    private static final int ALTO   = UICarta.ALTO  + 16;
    private static final int ARCO   = 16;
    private static final int MARGEN = 9;

    private static final Color COLOR_SOMBRA   = new Color(0, 0, 0, 90);
    private static final Color COLOR_VACIO    = new Color(50, 50, 50, 120);
    private static final Color COLOR_BORDE    = Color.WHITE;

    private CartaDTO cartaTope;
    private Image imagenTope;

    /**
     * Instantiates a new Ui descarte.
     */
    public UIDescarte() {
        setOpaque(false);
        setPreferredSize(new Dimension(ANCHO + 6, ALTO + 6));
    }

    /**
     * Sets carta tope.
     *
     * @param carta the carta
     */
    public void setCartaTope(CartaDTO carta) {
        this.cartaTope  = carta;
        this.imagenTope = CargadorAssets.getInstance()
                .getCartaEscalada(carta.getValor(), ANCHO - MARGEN * 2, ALTO - MARGEN * 2);
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        int cx = (getWidth()  - ANCHO) / 2;
        int cy = (getHeight() - ALTO)  / 2;

        g2.setColor(COLOR_SOMBRA);
        g2.fillRoundRect(cx + 5, cy + 5, ANCHO, ALTO, ARCO, ARCO);

        if (cartaTope == null) {
            g2.setColor(COLOR_VACIO);
            g2.fillRoundRect(cx, cy, ANCHO, ALTO, ARCO, ARCO);
            g2.setColor(new Color(120, 120, 120, 120));
            g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                    0, new float[]{6, 4}, 0));
            g2.drawRoundRect(cx + 1, cy + 1, ANCHO - 2, ALTO - 2, ARCO, ARCO);
        } else {
            g2.setColor(obtenerColorAwt(cartaTope.getColor()));
            g2.fillRoundRect(cx, cy, ANCHO, ALTO, ARCO, ARCO);
            g2.setColor(COLOR_BORDE);
            g2.setStroke(new BasicStroke(2.5f));
            g2.drawRoundRect(cx + 1, cy + 1, ANCHO - 2, ALTO - 2, ARCO, ARCO);
            g2.drawImage(imagenTope, cx + MARGEN, cy + MARGEN, this);
        }

        g2.dispose();
    }

    private Color obtenerColorAwt(String colorCarta) {
        if (colorCarta == null) return Color.WHITE;
        return switch (colorCarta.toUpperCase()) {
            case "ROJO" -> new Color(255, 85, 85);
            case "AZUL" -> new Color(85, 85, 255);
            case "VERDE" -> new Color(85, 255, 85);
            case "AMARILLO" -> new Color(255, 215, 0);
            default -> Color.DARK_GRAY;
        };
    }

    @Override public void execute() {}
}
