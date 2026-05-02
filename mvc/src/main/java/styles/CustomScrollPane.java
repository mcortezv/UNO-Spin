package styles;
import javax.swing.*;
import java.awt.*;

/**
 * The type Custom scroll pane.
 */
public class CustomScrollPane extends JScrollPane {

    /**
     * Instantiates a new Custom scroll pane.
     *
     * @param vista              the vista
     * @param politicaVertical   the politica vertical
     * @param politicaHorizontal the politica horizontal
     */
    public CustomScrollPane(Component vista, int politicaVertical, int politicaHorizontal) {
        super(vista, politicaVertical, politicaHorizontal);
        configurarEstilo();
    }

    /**
     * Instantiates a new Custom scroll pane.
     *
     * @param vista the vista
     */
    public CustomScrollPane(Component vista) {
        super(vista);
        configurarEstilo();
    }

    private void configurarEstilo() {
        setBorder(BorderFactory.createEmptyBorder());
        setOpaque(false);
        getViewport().setOpaque(false);
        getVerticalScrollBar().setUI(new ModernScrollBarUI());
        getHorizontalScrollBar().setUI(new ModernScrollBarUI());
        getVerticalScrollBar().setPreferredSize(new Dimension(12, 0));
        getHorizontalScrollBar().setPreferredSize(new Dimension(0, 12));
        getVerticalScrollBar().setOpaque(false);
        getHorizontalScrollBar().setOpaque(false);
    }
}