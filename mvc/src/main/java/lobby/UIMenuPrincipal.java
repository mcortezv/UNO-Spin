package lobby;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class UIMenuPrincipal extends JFrame {

    private static final Color AMARILLO = new Color(240, 200, 0);
    private static final Color AZUL_PANEL = new Color(52, 62, 125);
    private final MenuControlador controlador;

    public UIMenuPrincipal(MenuControlador controlador) {
        this.controlador = controlador;
        construirUI();
    }

    private void construirUI() {
        setTitle("UNO-Spin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050, 640);
        setLocationRelativeTo(null);
        setUndecorated(true);

        JPanel fondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new RadialGradientPaint(
                        getWidth() / 2f, getHeight() / 2f, getWidth() / 1.4f,
                        new float[]{0f, 1f},
                        new Color[]{new Color(160, 20, 20), new Color(60, 0, 0)}
                ));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        fondo.setLayout(new GridBagLayout());
        setContentPane(fondo);
        habilitarArrastre(fondo);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(15, 15, 15));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 35, 35));
                g2.setColor(AZUL_PANEL);
                g2.fill(new RoundRectangle2D.Float(20, 20, getWidth() - 40, getHeight() - 40, 24, 24));
            }
        };
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(700, 430));
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(18, 20, 18, 20);

        JLabel titulo = new JLabel("UNO SPIN");
        titulo.setFont(new Font("Arial Black", Font.BOLD, 52));
        titulo.setForeground(Color.WHITE);
        panel.add(titulo, gbc);

        JButton crearPartida = crearBoton("CREAR PARTIDA");
        crearPartida.addActionListener(e -> controlador.crearPartida());
        panel.add(crearPartida, gbc);

        JButton unirse = crearBoton("UNIRSE A PARTIDA");
        unirse.addActionListener(e -> controlador.unirsePartida());
        panel.add(unirse, gbc);

        fondo.add(panel);
    }

    private void habilitarArrastre(Component c) {
        MouseAdapter ma = new MouseAdapter() {
            private Point inicio;
            @Override public void mousePressed(MouseEvent e) { inicio = e.getLocationOnScreen(); }
            @Override public void mouseDragged(MouseEvent e) {
                Point p = e.getLocationOnScreen();
                setLocation(getX() + p.x - inicio.x, getY() + p.y - inicio.y);
                inicio = p;
            }
        };
        c.addMouseListener(ma);
        c.addMouseMotionListener(ma);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed() ? AMARILLO.darker() : AMARILLO);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 22, 22));
                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
            }
        };
        boton.setFont(new Font("Arial Black", Font.BOLD, 20));
        boton.setForeground(new Color(15, 15, 15));
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setOpaque(false);
        boton.setPreferredSize(new Dimension(330, 58));
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return boton;
    }
}
