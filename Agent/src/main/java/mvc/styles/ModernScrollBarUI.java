package mvc.styles;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class ModernScrollBarUI extends BasicScrollBarUI {

    @Override
    protected JButton createDecreaseButton(int orientacion) {
        return crearBotonVacio();
    }

    @Override
    protected JButton createIncreaseButton(int orientacion) {
        return crearBotonVacio();
    }

    private JButton crearBotonVacio() {
        JButton boton = new JButton();
        boton.setPreferredSize(new Dimension(0, 0));
        boton.setMinimumSize(new Dimension(0, 0));
        boton.setMaximumSize(new Dimension(0, 0));
        return boton;
    }

    @Override
    protected void paintTrack(Graphics g, JComponent componente, Rectangle limitesPista) {
        g.setColor(Color.WHITE);
        g.fillRect(limitesPista.x, limitesPista.y, limitesPista.width, limitesPista.height);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent componente, Rectangle limitesBoton) {
        if (limitesBoton.isEmpty() || !scrollbar.isEnabled()) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.BLACK);

        g2.fillRoundRect(limitesBoton.x, limitesBoton.y, limitesBoton.width, limitesBoton.height, 10, 10);
        g2.dispose();
    }
}