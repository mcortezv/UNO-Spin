package MVC;
import MVC.interfaces.IComponent;
import dto.CartaDTO;

import javax.swing.*;
import java.awt.*;

public class UITablero extends JPanel implements IComponent {

    private final UIRuleta   ruleta;
    private final UIDescarte descarte;
    private final UIMazo     mazo;

    private final JLabel lblGirar;
    private final JLabel lblPedir;

    public UITablero() {
        this.ruleta   = new UIRuleta();
        this.descarte = new UIDescarte();
        this.mazo     = new UIMazo();
        this.lblGirar = crearLabelAccion("GIRAR");
        this.lblPedir = crearLabelAccion("PEDIR");

        setOpaque(false);
        setLayout(new GridBagLayout());
        construirLayout();
    }


    private void construirLayout() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill   = GridBagConstraints.NONE;

        gbc.gridy  = 0;
        gbc.insets = new Insets(4, 20, 2, 20);

        gbc.gridx = 0; add(ruleta,   gbc);
        gbc.gridx = 1; add(descarte, gbc);
        gbc.gridx = 2; add(mazo,     gbc);

        gbc.gridy  = 1;
        gbc.insets = new Insets(0, 20, 4, 20);

        gbc.gridx = 0; add(lblGirar, gbc);

        gbc.gridx = 1;
        add(Box.createRigidArea(new Dimension(descarte.getPreferredSize().width, 10)), gbc);

        gbc.gridx = 2; add(lblPedir, gbc);
    }


    private JLabel crearLabelAccion(String texto) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 13));
        lbl.setForeground(Color.WHITE);
        lbl.setOpaque(true);
        lbl.setBackground(new Color(0, 0, 0, 130));
        lbl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 80, 150), 1),
                BorderFactory.createEmptyBorder(3, 14, 3, 14)
        ));
        return lbl;
    }


    public void setCartaCima(CartaDTO carta) {
        descarte.setCartaTope(carta);
    }

    public UIRuleta getRuleta() {
        return ruleta;
    }

    public UIDescarte getDescarte() {
        return descarte;
    }

    public UIMazo getMazo() {
        return mazo;
    }

    public void setOnPedirCarta(Runnable accion) {
        this.mazo.setOnPedirCarta(accion);
    }

    public void setOnGiroCompleto(Runnable accion) {
        this.ruleta.setOnGiroCompleto(accion);
    }
    @Override public void execute() {}
}
