package MVC.styles;

import javax.swing.*;
import java.awt.*;

public class CustomScrollPane extends JScrollPane {

    public CustomScrollPane(Component vista, int politicaVertical, int politicaHorizontal) {
        super(vista, politicaVertical, politicaHorizontal);
        configurarEstilo();
    }

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