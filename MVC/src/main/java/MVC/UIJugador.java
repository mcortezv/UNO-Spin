package MVC;
import MVC.interfaces.IComponent;
import dto.JugadorRivalDTO;
import javax.swing.Icon;
import javax.swing.*;
import java.awt.*;

public class UIJugador extends JPanel implements IComponent {
    public enum Posicion { TOP, LEFT, RIGHT }

    private static final int AVATAR_SIZE = 46;
    private static final Font FUENTE_NOMBRE = new Font("Arial", Font.BOLD, 12);
    private static final Font FUENTE_CONTEO = new Font("Arial", Font.PLAIN, 11);
    private static final Color COLOR_NOMBRE = Color.WHITE;
    private static final Color COLOR_CONTEO = new Color(210, 210, 210);
    private static final Color COLOR_FONDO_TURN = new Color(255, 200, 0, 50);
    private static final Color COLOR_BORDE_TURN = new Color(255, 200, 0);
    private static final int ARCO_PANEL = 16;

    private final JLabel lblNombre;
    private final JLabel lblCartas;
    private final JLabel lblAvatar;
    private final UIManoRival manoRival;
    private boolean esTurno = false;


    public UIJugador(JugadorRivalDTO dto, Posicion posicion) {
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        UIManoRival.Orientacion ori = (posicion == Posicion.TOP) ? UIManoRival.Orientacion.HORIZONTAL : UIManoRival.Orientacion.VERTICAL;

        this.lblNombre = crearLabel(dto.getNombre(),                 FUENTE_NOMBRE, COLOR_NOMBRE);
        this.lblCartas = crearLabel("Cartas: " + dto.getCantidadCartas(), FUENTE_CONTEO, COLOR_CONTEO);
        this.lblAvatar = crearAvatar(dto.getNumeroAvatar());
        this.manoRival = new UIManoRival(dto.getCantidadCartas(), ori);
        this.esTurno   = dto.isEsTurno();

        construirLayout(posicion);
    }

    private void construirLayout(Posicion posicion) {
        JPanel panelInfo = construirPanelInfo();

        if (posicion == Posicion.TOP) {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            add(manoRival);
            add(Box.createHorizontalStrut(12));
            add(panelInfo);
        } else {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            add(panelInfo);
            add(Box.createVerticalStrut(8));
            add(manoRival);
        }
    }

    private JPanel construirPanelInfo() {
        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.add(lblCartas);
        info.add(Box.createVerticalStrut(4));
        info.add(lblAvatar);
        info.add(Box.createVerticalStrut(3));
        info.add(lblNombre);
        return info;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!esTurno) return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(COLOR_FONDO_TURN);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARCO_PANEL, ARCO_PANEL);

        g2.setColor(COLOR_BORDE_TURN);
        g2.setStroke(new BasicStroke(2f));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ARCO_PANEL, ARCO_PANEL);

        g2.dispose();
    }


    private JLabel crearLabel(String texto, Font fuente, Color color) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(fuente);
        lbl.setForeground(color);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

    private JLabel crearAvatar(int numero) {
        JLabel lbl = new JLabel();
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setPreferredSize(new Dimension(AVATAR_SIZE, AVATAR_SIZE));
        Image img = CargadorAssets.getInstance().getAvatarEscalado(numero, AVATAR_SIZE);
        if (img != null) {
            lbl.setIcon(new ImageIcon(img));
        } else {
            lbl.setIcon(new Icon() {
                public void paintIcon(Component c, Graphics g, int x, int y) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(100, 100, 100));
                    g2.fillOval(x, y, AVATAR_SIZE, AVATAR_SIZE);
                    g2.dispose();
                }
                public int getIconWidth()  { return AVATAR_SIZE; }
                public int getIconHeight() { return AVATAR_SIZE; }
            });
        }

        lbl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 80), 2),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)
        ));

        return lbl;
    }

    public void actualizar(JugadorRivalDTO dto) {
        lblCartas.setText("Cartas: " + dto.getCantidadCartas());
        manoRival.setCantidadCartas(dto.getCantidadCartas());
        setEsTurno(dto.isEsTurno());
    }

    public void setEsTurno(boolean turno) {
        this.esTurno = turno;
        repaint();
    }

    @Override public void execute() {}
}