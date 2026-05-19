package lobby;

import dto.ConfiguracionPartidaDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class UICrearPartida extends JFrame {

    private static final Color AMARILLO = new Color(240, 200, 0);
    private static final Color AZUL_PANEL = new Color(52, 62, 125);

    private JTextField campoMinimo, campoMaximo, campoComodines, campoAccion, campoTiempo;
    private final LobbyControlador controlador;

    public UICrearPartida(LobbyControlador controlador) {
        this.controlador = controlador;
        construirUI();
    }

    private void construirUI() {
        setTitle("UNO-Spin — Crear Partida");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050, 620);
        setLocationRelativeTo(null);
        setUndecorated(true);

        JPanel fondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                RadialGradientPaint gradiente = new RadialGradientPaint(
                        getWidth() / 2f, getHeight() / 2f, getWidth() / 1.4f,
                        new float[]{0f, 1f},
                        new Color[]{new Color(160, 20, 20), new Color(60, 0, 0)}
                );
                g2d.setPaint(gradiente);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        fondo.setLayout(new GridBagLayout());
        setContentPane(fondo);
        habilitarArrastre(fondo);

        JPanel marcoOscuro = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(15, 15, 15));
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 35, 35));
            }
        };
        marcoOscuro.setOpaque(false);
        marcoOscuro.setLayout(new GridBagLayout());
        marcoOscuro.setPreferredSize(new Dimension(870, 490));

        JPanel panelAzul = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(AZUL_PANEL);
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 22, 22));
            }
        };
        panelAzul.setOpaque(false);
        panelAzul.setPreferredSize(new Dimension(830, 450));
        panelAzul.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel titulo = new JLabel("CREAR NUEVA PARTIDA");
        titulo.setFont(new Font("Arial Black", Font.BOLD, 42));
        titulo.setForeground(Color.WHITE);
        gbc.insets = new Insets(30, 20, 20, 20);
        panelAzul.add(titulo, gbc);

        campoMinimo = crearCampo(panelAzul, gbc, "Número Mínimo:");
        campoMaximo = crearCampo(panelAzul, gbc, "Número Máximo:");
        campoComodines = crearCampo(panelAzul, gbc, "Número Comodines:");
        campoAccion = crearCampo(panelAzul, gbc, "Número Cartas Acción:");
        campoTiempo = crearCampo(panelAzul, gbc, "Tiempo Mostrar Cartas (seg):");

        JButton botonCrear = crearBotonAmarillo("CREAR", 170, 58);
        botonCrear.setFont(new Font("Arial Black", Font.BOLD, 22));
        botonCrear.addActionListener(e -> onCrear());
        gbc.insets = new Insets(20, 20, 30, 20);
        panelAzul.add(botonCrear, gbc);

        GridBagConstraints gbcInner = new GridBagConstraints();
        gbcInner.insets = new Insets(20, 20, 20, 20);
        marcoOscuro.add(panelAzul, gbcInner);
        fondo.add(marcoOscuro, new GridBagConstraints());
    }

    private JTextField crearCampo(JPanel panel, GridBagConstraints gbc, String labelText) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        gbc.insets = new Insets(0, 20, 4, 20);
        panel.add(label, gbc);

        JTextField campo = new JTextField();
        campo.setFont(new Font("Arial", Font.PLAIN, 16));
        campo.setForeground(Color.WHITE);
        campo.setBackground(new Color(28, 28, 28));
        campo.setCaretColor(Color.WHITE);
        campo.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        campo.setPreferredSize(new Dimension(300, 42));
        gbc.insets = new Insets(0, 20, 25, 20);
        panel.add(campo, gbc);
        return campo;
    }

    private void onCrear() {
        try {
            int min = Integer.parseInt(campoMinimo.getText().trim());
            int max = Integer.parseInt(campoMaximo.getText().trim());
            int comodines = Integer.parseInt(campoComodines.getText().trim());
            int accion = Integer.parseInt(campoAccion.getText().trim());
            float tiempo = Float.parseFloat(campoTiempo.getText().trim());

            if (min <= 0 || max <=0 || max < min) {
                throw new IllegalArgumentException("Ingresa números válidos");
            }

            ConfiguracionPartidaDTO dto = new ConfiguracionPartidaDTO(min, max, comodines, accion, tiempo);
            controlador.solicitarConfiguracion(dto);

            JOptionPane.showMessageDialog(this,
                    "Partida creada con éxito:\nMin=" + min + ", Max=" + max +
                            ", Comodines=" + comodines + ", Acción=" + accion +
                            ", Tiempo=" + tiempo + " seg",
                    "Partida creada", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Ingresa números válidos",
                    "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private JButton crearBotonAmarillo(String texto, int ancho, int alto) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(getModel().isPressed() ? new Color(195, 155, 0) : AMARILLO);
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20));
                g2d.dispose();
                super.paintComponent(g);
            }
            @Override
            protected void paintBorder(Graphics g) {}
        };
        boton.setFont(new Font("Arial Black", Font.BOLD, 16));
        boton.setForeground(new Color(15, 15, 15));
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setOpaque(false);
        boton.setPreferredSize(new Dimension(ancho, alto));
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return boton;
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

    public static void main(String[] args) {
        LobbyModelo modelo = new LobbyModelo(null, null, null, 0, 0, null);
        LobbyControlador controlador = new LobbyControlador(modelo);
        SwingUtilities.invokeLater(() -> new UICrearPartida(controlador).setVisible(true));
    }
}
