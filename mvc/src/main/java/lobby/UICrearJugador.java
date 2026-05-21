package lobby;

import dto.JugadorDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class UICrearJugador extends JFrame implements ISuscriptorLobby {

    private static final Color AMARILLO = new Color(240, 200, 0);
    private static final Color AZUL_PANEL = new Color(52, 62, 125);

    private final LobbyControlador controlador;

    private JTextField campoNombre;
    private int avatarSeleccionado = 1;

    public UICrearJugador(LobbyControlador controlador) {
        this.controlador = controlador;
        construirUI();
    }

    private void construirUI() {
        setTitle("UNO-Spin — Unirse");
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
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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

        JLabel titulo = new JLabel("CREAR JUGADOR");
        titulo.setFont(new Font("Arial Black", Font.BOLD, 42));
        titulo.setForeground(Color.WHITE);
        gbc.insets = new Insets(30, 20, 20, 20);
        panelAzul.add(titulo, gbc);

        JLabel labelNombre = new JLabel("Tu Nombre:");
        labelNombre.setFont(new Font("Arial", Font.BOLD, 18));
        labelNombre.setForeground(Color.WHITE);
        gbc.insets = new Insets(0, 20, 4, 20);
        panelAzul.add(labelNombre, gbc);

        campoNombre = new JTextField("", 22) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(28, 28, 28));
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 40, 40));
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        campoNombre.setFont(new Font("Arial", Font.PLAIN, 16));
        campoNombre.setForeground(Color.WHITE);
        campoNombre.setBackground(new Color(28, 28, 28));
        campoNombre.setCaretColor(Color.WHITE);
        campoNombre.setOpaque(false);
        campoNombre.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        campoNombre.setPreferredSize(new Dimension(300, 42));
        gbc.insets = new Insets(0, 20, 25, 20);
        panelAzul.add(campoNombre, gbc);

        JPanel filaOpciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 80, 0));
        filaOpciones.setOpaque(false);
        filaOpciones.add(construirSeccionAvatar());
        filaOpciones.add(construirSeccionColor());
        gbc.insets = new Insets(0, 20, 25, 20);
        panelAzul.add(filaOpciones, gbc);

        JButton botonUnirse = crearBotonAmarillo("UNIRSE", 170, 58);
        botonUnirse.setFont(new Font("Arial Black", Font.BOLD, 22));
        botonUnirse.addActionListener(e -> onUnirse());
        gbc.insets = new Insets(0, 20, 30, 20);
        panelAzul.add(botonUnirse, gbc);

        GridBagConstraints gbcInner = new GridBagConstraints();
        gbcInner.insets = new Insets(20, 20, 20, 20);
        marcoOscuro.add(panelAzul, gbcInner);
        fondo.add(marcoOscuro, new GridBagConstraints());
    }

    private JPanel construirSeccionAvatar() {
        JPanel seccion = new JPanel();
        seccion.setOpaque(false);
        seccion.setLayout(new BoxLayout(seccion, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Elige tu Avatar:");
        label.setFont(new Font("Arial", Font.BOLD, 17));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton boton = crearBotonAmarillo("ARMARIO", 130, 48);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);

        seccion.add(label);
        seccion.add(Box.createVerticalStrut(12));
        seccion.add(boton);
        return seccion;
    }

    private JPanel construirSeccionColor() {
        JPanel seccion = new JPanel();
        seccion.setOpaque(false);
        seccion.setLayout(new BoxLayout(seccion, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Elige Color de Cartas:");
        label.setFont(new Font("Arial", Font.BOLD, 17));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel fila = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        fila.setOpaque(false);
        for (int i = 1; i <= 4; i++) {
            fila.add(crearBotonAmarillo(String.valueOf(i), 55, 48));
        }

        seccion.add(label);
        seccion.add(Box.createVerticalStrut(12));
        seccion.add(fila);
        return seccion;
    }

    private void onUnirse() {
        String nombre = campoNombre.getText().trim();
        if (nombre.isEmpty()) return;
        JugadorDTO jugador = new JugadorDTO();
        jugador.setNombre(nombre);
        jugador.setNumeroAvatar(avatarSeleccionado);
        controlador.solicitarUnion(jugador);
    }

    @Override
    public void actualizar(IModeloLobbyLectura modelo) {
        SwingUtilities.invokeLater(() -> {
            switch (modelo.getEstadoUnion()) {
                case PENDIENTE:
                    setVisible(false);
                    break;
                case RECHAZADA:
                    setVisible(true);
                    JOptionPane.showMessageDialog(UICrearJugador.this,
                            "El host rechazó tu solicitud.",
                            "Solicitud rechazada",
                            JOptionPane.WARNING_MESSAGE);
                    break;
            }
        });
    }

    private JButton crearBotonAmarillo(String texto, int ancho, int alto) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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
            @Override public void mousePressed(MouseEvent e)  { inicio = e.getLocationOnScreen(); }
            @Override public void mouseDragged(MouseEvent e)  {
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
        SwingUtilities.invokeLater(() -> new UICrearJugador(controlador).setVisible(true));
    }
}
