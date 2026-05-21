package op;
import dto.CartaDTO;
<<<<<<<< HEAD:agent/src/main/java/mvc/UICarta.java
import interfaces.IComponent;
========

>>>>>>>> origin/main:mvc/src/main/java/op/UICarta.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Ui carta.
 */
public class UICarta extends JPanel {

    /**
     * The constant ANCHO.
     */
    public static final int ANCHO = 78;
    /**
     * The constant ALTO.
     */
    public static final int ALTO = 112;
    /**
     * The constant ELEVACION.
     */
    public static final int ELEVACION = 14;
    private static final int ARCO = 14;

    private static final Color COLOR_BORDE_HOVER = new Color(255, 230, 0);
    private static final Color COLOR_BORDE_SELECTED = new Color(255, 200, 0);
    private static final Color COLOR_OVERLAY_DIS = new Color(0, 0, 0, 110);
    private static final Color COLOR_SOMBRA = new Color(0, 0, 0, 70);

    private static final BasicStroke STROKE_BORDE_BLANCO = new BasicStroke(2.0f);
    private static final BasicStroke STROKE_BORDE_EFECTO = new BasicStroke(3.0f);

    private final CartaDTO carta;
    private final Image imagenArte;
    private boolean hover = false;
    private boolean seleccionada = false;
    private final boolean jugable = true;

    private final List<Runnable> onSeleccionListeners = new ArrayList<>();

    /**
     * Instantiates a new Ui carta.
     *
     * @param carta the carta
     */
    public UICarta(CartaDTO carta) {
        this.carta = carta;

        Image img = null;
        ImageIcon icon = CargadorAssets.getInstance().getCarta(carta);
        if (icon != null && icon.getImage() != null) {
            img = icon.getImage().getScaledInstance(ANCHO - 10, ALTO - 10, Image.SCALE_SMOOTH);
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(img, 0);
            try {
                tracker.waitForAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.imagenArte = img;

        setOpaque(false);
        setPreferredSize(new Dimension(ANCHO, ALTO + ELEVACION));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registrarMouseListeners();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        configurarCalidad(g2);

        int offsetY = (hover || seleccionada) ? 0 : ELEVACION;

        pintarSombra(g2, offsetY);
       // pintarFondoColor(g2, offsetY);
        pintarImagen(g2, offsetY);
        pintarTextoSinImagen(g2, offsetY);
        pintarBordeBlanco(g2, offsetY);

        if (hover || seleccionada) {
            pintarBordeEfecto(g2, offsetY);
        }
        if (!jugable) {
            pintarOverlayDeshabilitado(g2, offsetY);
        }

        g2.dispose();
    }

    private static Color resolverColorCarta(String color) {
        if (color == null) return new Color(30, 30, 30);
        return switch (color.toUpperCase()) {
            case "ROJO"     -> new Color(200, 40, 40);
            case "AZUL"     -> new Color(30, 90, 200);
            case "AMARILLO" -> new Color(210, 170, 0);
            case "VERDE"    -> new Color(30, 150, 60);
            default         -> new Color(30, 30, 30);
        };
    }

    private void pintarSombra(Graphics2D g2, int y) {
        g2.setColor(COLOR_SOMBRA);
        g2.fillRoundRect(4, y + 4, ANCHO - 2, ALTO - 2, ARCO, ARCO);
    }
<<<<<<<< HEAD:agent/src/main/java/mvc/UICarta.java
//
//    private void pintarFondoColor(Graphics2D g2, int y) {
//        g2.setColor(ColorCarta.toAWT(carta.getColor()));
//        g2.fillRoundRect(1, y + 1, ANCHO - 2, ALTO - 2, ARCO, ARCO);
//    }
========

    private void pintarFondoColor(Graphics2D g2, int y) {
        g2.setColor(resolverColorCarta(carta.getColor()));
        g2.fillRoundRect(1, y + 1, ANCHO - 2, ALTO - 2, ARCO, ARCO);
    }
>>>>>>>> origin/main:mvc/src/main/java/op/UICarta.java

    private void pintarImagen(Graphics2D g2, int y) {
        if (imagenArte == null) return;
        int margen = 5;
        g2.drawImage(imagenArte, margen, y + margen, this);
    }

    private void pintarTextoSinImagen(Graphics2D g2, int y) {
        if (imagenArte != null) return;
        String texto = resolverTextoTipo(carta.getTipoCarta());
        if (texto == null) return;
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g2.getFontMetrics();
        int tx = (ANCHO - fm.stringWidth(texto)) / 2;
        int ty = y + (ALTO + fm.getAscent() - fm.getDescent()) / 2;
        g2.drawString(texto, tx, ty);
    }

    private static String resolverTextoTipo(String tipo) {
        if (tipo == null) return null;
        return switch (tipo) {
            case "REVERSA"      -> "↺";
            case "CAMBIO_COLOR" -> "★";
            case "TOMA_CUATRO"  -> "+4";
            default             -> null;
        };
    }

    private void pintarBordeBlanco(Graphics2D g2, int y) {
        g2.setColor(Color.WHITE);
        g2.setStroke(STROKE_BORDE_BLANCO);
        g2.drawRoundRect(2, y + 2, ANCHO - 4, ALTO - 4, ARCO, ARCO);
    }

    private void pintarBordeEfecto(Graphics2D g2, int y) {
        g2.setColor(seleccionada ? COLOR_BORDE_SELECTED : COLOR_BORDE_HOVER);
        g2.setStroke(STROKE_BORDE_EFECTO);
        g2.drawRoundRect(1, y + 1, ANCHO - 2, ALTO - 2, ARCO, ARCO);
    }

    private void pintarOverlayDeshabilitado(Graphics2D g2, int y) {
        g2.setColor(COLOR_OVERLAY_DIS);
        g2.fillRoundRect(1, y + 1, ANCHO - 2, ALTO - 2, ARCO, ARCO);
    }

    private void configurarCalidad(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    }

    private void registrarMouseListeners() {
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                if (jugable) {
                    hover = true;
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (!jugable) return;
                seleccionada = !seleccionada;
                repaint();
                onSeleccionListeners.forEach(Runnable::run);
            }
        });
    }

    /**
     * Gets carta.
     *
     * @return the carta
     */
    public CartaDTO getCarta() {
        return carta;
    }

    /**
     * Is seleccionada boolean.
     *
     * @return the boolean
     */
    public boolean isSeleccionada() {
        return seleccionada;
    }

    /**
     * Sets seleccionada.
     *
     * @param s the s
     */
    public void setSeleccionada(boolean s) {
        this.seleccionada = s;
        repaint();
    }

    /**
     * Add on seleccion listener.
     *
     * @param listener the listener
     */
    public void addOnSeleccionListener(Runnable listener) {
        onSeleccionListeners.add(listener);
    }

}