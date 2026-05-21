package lobby;

import dto.JugadorDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

public class UICrearJugador extends JFrame implements ISuscriptorLobby {

    private static final Color AMARILLO   = new Color(240, 200, 0);
    private static final Color AZUL_PANEL = new Color(52, 62, 125);

    // 8 colores disponibles
    private static final Color[] COLORES = {
        new Color(30,  100, 195),  // Azul
        new Color(204,  37,  37),  // Rojo
        new Color(46,  153,  56),  // Verde
        new Color(218, 180,   0),  // Amarillo
        new Color(142,  68, 173),  // Morado
        new Color(230, 126,  34),  // Naranja
        new Color(232,  67, 147),  // Rosa
        new Color(0,   180, 180)   // Cian
    };
    private static final String[] NOMBRES = {
        "Azul", "Rojo", "Verde", "Amarillo", "Morado", "Naranja", "Rosa", "Cian"
    };
    private static final String[] NOMBRES_DOMINIO = {
        "AZUL", "ROJO", "VERDE", "AMARILLO", "MORADO", "NARANJA", "ROSA", "CIAN"
    };

    private final LobbyControlador controlador;

    private JTextField campoNombre;
    private int avatarSeleccionado = 1;
    
    private final List<Integer> coloresSeleccionados = new ArrayList<>(List.of(-1, -1, -1, -1));
    
    private JLabel visualizadorAvatar;
    private JButton[] botonesRanuras; 

    public UICrearJugador(LobbyControlador controlador) {
        this.controlador = controlador;
        construirUI();
    }

    private void construirUI() {
        setTitle("UNO-Spin — Crear Jugador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050, 640);
        setLocationRelativeTo(null);
        setUndecorated(true);

        JPanel fondo = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
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

        JPanel marcoOscuro = panelRedondeado(new Color(15, 15, 15), 35);
        marcoOscuro.setLayout(new GridBagLayout());
        marcoOscuro.setPreferredSize(new Dimension(880, 510));

        JPanel panelAzul = panelRedondeado(AZUL_PANEL, 22);
        panelAzul.setPreferredSize(new Dimension(840, 470));
        panelAzul.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor    = GridBagConstraints.CENTER;

        // Título
        JLabel titulo = new JLabel("CREAR JUGADOR");
        titulo.setFont(new Font("Arial Black", Font.BOLD, 40));
        titulo.setForeground(Color.WHITE);
        gbc.insets = new Insets(24, 20, 16, 20);
        panelAzul.add(titulo, gbc);

        // Campo nombre
        JLabel lblNombre = new JLabel("Tu Nombre:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 17));
        lblNombre.setForeground(Color.WHITE);
        gbc.insets = new Insets(0, 20, 4, 20);
        panelAzul.add(lblNombre, gbc);

        campoNombre = crearCampoNombre();
        gbc.insets = new Insets(0, 20, 20, 20);
        panelAzul.add(campoNombre, gbc);

        // Fila avatar + colores
        JPanel filaOpciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 0));
        filaOpciones.setOpaque(false);
        filaOpciones.add(construirSeccionAvatar());
        filaOpciones.add(construirSeccionColorPorRanuras());
        gbc.insets = new Insets(0, 20, 20, 20);
        panelAzul.add(filaOpciones, gbc);

        // Botón UNIRSE
        JButton botonUnirse = crearBotonAmarillo("UNIRSE", 170, 54);
        botonUnirse.setFont(new Font("Arial Black", Font.BOLD, 22));
        botonUnirse.addActionListener(e -> onUnirse());
        gbc.insets = new Insets(0, 20, 28, 20);
        panelAzul.add(botonUnirse, gbc);

        GridBagConstraints gbcI = new GridBagConstraints();
        gbcI.insets = new Insets(20, 20, 20, 20);
        marcoOscuro.add(panelAzul, gbcI);
        fondo.add(marcoOscuro, new GridBagConstraints());
    }

    private JTextField crearCampoNombre() {
        JTextField campo = new JTextField(22) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(28, 28, 28));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 40, 40));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        campo.setFont(new Font("Arial", Font.PLAIN, 16));
        campo.setForeground(Color.WHITE);
        campo.setBackground(new Color(28, 28, 28));
        campo.setCaretColor(Color.WHITE);
        campo.setOpaque(false);
        campo.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        campo.setPreferredSize(new Dimension(300, 42));
        return campo;
    }


    private JPanel construirSeccionAvatar() {
        JPanel seccion = new JPanel(new BorderLayout(0, 8));
        seccion.setOpaque(false);

        JLabel lbl = new JLabel("Elige tu Avatar:", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 16));
        lbl.setForeground(Color.WHITE);

        visualizadorAvatar = new JLabel("", SwingConstants.CENTER);
        visualizadorAvatar.setPreferredSize(new Dimension(80, 80));
        actualizarImagenAvatar();

        JButton btnArmario = crearBotonAmarillo("ARMARIO", 130, 46);
        btnArmario.addActionListener(e -> abrirArmario());

        seccion.add(lbl,              BorderLayout.NORTH);
        seccion.add(visualizadorAvatar, BorderLayout.CENTER);
        seccion.add(btnArmario,       BorderLayout.SOUTH);
        return seccion;
    }

    private void abrirArmario() {
        JLayeredPane lp = getLayeredPane();
        if (lp == null) return;

        JPanel panel = new JPanel(new BorderLayout(10, 14)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(20, 20, 25, 248));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 26, 26));
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        int w = 420, h = 440;
        panel.setBounds((getWidth() - w) / 2, (getHeight() - h) / 2, w, h);

        JLabel tit = new JLabel("SELECCIONA UN AVATAR", SwingConstants.CENTER);
        tit.setFont(new Font("Arial Black", Font.BOLD, 17));
        tit.setForeground(Color.WHITE);
        panel.add(tit, BorderLayout.NORTH);

        JPanel rejilla = new JPanel(new GridLayout(3, 3, 12, 12));
        rejilla.setOpaque(false);

        for (int i = 1; i <= 9; i++) {
            final int num = i;
            JButton btn = new JButton();
            ImageIcon ico = buscarIcono(num, 80, 80);
            if (ico != null) btn.setIcon(ico);
            else { btn.setText("" + i); btn.setForeground(Color.WHITE); btn.setFont(new Font("Arial Black", Font.BOLD, 16)); }

            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createLineBorder(
                num == avatarSeleccionado ? AMARILLO : new Color(60, 60, 70), 3, true));
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.addActionListener(ev -> {
                avatarSeleccionado = num;
                actualizarImagenAvatar();
                lp.remove(panel);
                lp.repaint();
            });
            rejilla.add(btn);
        }
        panel.add(rejilla, BorderLayout.CENTER);

        JButton btnCancelar = crearBotonAmarillo("CANCELAR", 120, 34);
        btnCancelar.setFont(new Font("Arial Black", Font.BOLD, 12));
        btnCancelar.addActionListener(ev -> { lp.remove(panel); lp.repaint(); });
        JPanel sur = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sur.setOpaque(false);
        sur.add(btnCancelar);
        panel.add(sur, BorderLayout.SOUTH);

        lp.add(panel, JLayeredPane.DRAG_LAYER);
        lp.revalidate();
        lp.repaint();
    }

    private void actualizarImagenAvatar() {
        ImageIcon ico = buscarIcono(avatarSeleccionado, 72, 72);
        if (ico != null) { visualizadorAvatar.setIcon(ico); visualizadorAvatar.setText(""); }
        else             { visualizadorAvatar.setIcon(null); visualizadorAvatar.setText("[" + avatarSeleccionado + "]"); visualizadorAvatar.setForeground(Color.LIGHT_GRAY); }
    }

    private ImageIcon buscarIcono(int n, int w, int h) {
        String ruta = "/avatares/avatar_" + n + ".png";
        java.net.URL url = getClass().getResource(ruta);
        if (url == null) url = Thread.currentThread().getContextClassLoader().getResource(ruta);
        if (url == null) return null;
        return new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
    }

    private JPanel construirSeccionColorPorRanuras() {
        JPanel seccion = new JPanel();
        seccion.setOpaque(false);
        seccion.setLayout(new BoxLayout(seccion, BoxLayout.Y_AXIS));

        JLabel lbl = new JLabel("Asigna tus 4 Colores de Cartas:", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 16));
        lbl.setForeground(Color.WHITE);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel filaRanuras = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        filaRanuras.setOpaque(false);

        botonesRanuras = new JButton[4];

        for (int i = 0; i < 4; i++) {
            final int numeroRanura = i;
            JButton btn = new JButton() {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    int colorIdx = coloresSeleccionados.get(numeroRanura);
                    
                    // Si no tiene color asignado, se dibuja gris punteado, si tiene, se pinta su color
                    if (colorIdx == -1) {
                        g2.setColor(new Color(40, 40, 50));
                        g2.fill(new Ellipse2D.Float(4, 4, getWidth() - 8, getHeight() - 24));
                        g2.setColor(Color.LIGHT_GRAY);
                        g2.setFont(new Font("Arial", Font.BOLD, 22));
                        g2.drawString("?", getWidth()/2 - 6, getHeight()/2 - 2);
                    } else {
                        g2.setColor(COLORES[colorIdx]);
                        g2.fill(new Ellipse2D.Float(4, 4, getWidth() - 8, getHeight() - 24));
                    }
                    g2.dispose();

                    // Etiqueta inferior ("Color 1", "Color 2", etc.)
                    g.setFont(new Font("Arial", Font.BOLD, 11));
                    g.setColor(Color.WHITE);
                    FontMetrics fm = g.getFontMetrics();
                    String text = "Color " + (numeroRanura + 1);
                    g.drawString(text, (getWidth() - fm.stringWidth(text)) / 2, getHeight() - 4);
                }
                @Override protected void paintBorder(Graphics g) {}
            };

            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setOpaque(false);
            btn.setPreferredSize(new Dimension(75, 85));
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            // Al dar clic a la ranura, abrimos el selector de los 8 colores en la misma pantalla
            btn.addActionListener(e -> abrirSelectorDeColorParaRanura(numeroRanura));

            botonesRanuras[i] = btn;
            filaRanuras.add(btn);
        }

        seccion.add(lbl);
        seccion.add(Box.createVerticalStrut(15));
        seccion.add(filaRanuras);
        return seccion;
    }

    private void abrirSelectorDeColorParaRanura(int ranuraIdx) {
        JLayeredPane lp = getLayeredPane();
        if (lp == null) return;

        JPanel panelSelector = new JPanel(new BorderLayout(10, 10)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(25, 25, 35, 250));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20));
            }
        };
        panelSelector.setOpaque(false);
        panelSelector.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        int w = 360, h = 220;
        panelSelector.setBounds((getWidth() - w) / 2, (getHeight() - h) / 2, w, h);

        JLabel tituloSel = new JLabel("SELECCIONA EL COLOR " + (ranuraIdx + 1), SwingConstants.CENTER);
        tituloSel.setFont(new Font("Arial Black", Font.BOLD, 14));
        tituloSel.setForeground(Color.WHITE);
        panelSelector.add(tituloSel, BorderLayout.NORTH);

        // Rejilla de los 8 colores completos
        JPanel rejilla8Colores = new JPanel(new GridLayout(2, 4, 12, 12));
        rejilla8Colores.setOpaque(false);

        for (int i = 0; i < COLORES.length; i++) {
            final int colorIdx = i;
            JButton btnColorOpcion = new JButton() {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(COLORES[colorIdx]);
                    g2.fill(new Ellipse2D.Float(2, 2, getWidth() - 4, getHeight() - 16));
                    g2.dispose();

                    g.setFont(new Font("Arial", Font.BOLD, 9));
                    g.setColor(Color.LIGHT_GRAY);
                    FontMetrics fm = g.getFontMetrics();
                    g.drawString(NOMBRES[colorIdx], (getWidth() - fm.stringWidth(NOMBRES[colorIdx])) / 2, getHeight() - 2);
                }
                @Override protected void paintBorder(Graphics g) {}
            };
            btnColorOpcion.setContentAreaFilled(false);
            btnColorOpcion.setOpaque(false);
            btnColorOpcion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            btnColorOpcion.addActionListener(ev -> {
                coloresSeleccionados.set(ranuraIdx, colorIdx); 
                botonesRanuras[ranuraIdx].repaint();          
                lp.remove(panelSelector);
                lp.repaint();
            });

            rejilla8Colores.add(btnColorOpcion);
        }
        panelSelector.add(rejilla8Colores, BorderLayout.CENTER);

        lp.add(panelSelector, JLayeredPane.DRAG_LAYER);
        lp.revalidate();
        lp.repaint();
    }

    
    private void onUnirse() {
        String nombre = campoNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor escribe tu nombre.", "Nombre requerido", JOptionPane.WARNING_MESSAGE);
            campoNombre.requestFocusInWindow();
            return;
        }
        
        for (int i = 0; i < coloresSeleccionados.size(); i++) {
            if (coloresSeleccionados.get(i) == -1) {
                JOptionPane.showMessageDialog(this, "Por favor asigna un color a la ranura " + (i + 1) + ".", "Colores incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        JugadorDTO jugador = new JugadorDTO();
        jugador.setNumeroAvatar(avatarSeleccionado);

        int primerColor = coloresSeleccionados.get(0);
        jugador.setColorCartas(primerColor + 1);

        StringBuilder coloresDominio = new StringBuilder();
        for (int idx : coloresSeleccionados) {
            if (coloresDominio.length() > 0) coloresDominio.append(",");
            coloresDominio.append(NOMBRES_DOMINIO[idx]);
        }
        
        jugador.setNombre(nombre + "\u0000" + coloresDominio.toString());

        controlador.solicitarUnion(jugador);
    }

    @Override
    public void actualizar(IModeloLobbyLectura modelo) {
        SwingUtilities.invokeLater(() -> {
            switch (modelo.getEstadoUnion()) {
                case PENDIENTE -> setVisible(false);
                case RECHAZADA -> {
                    setVisible(true);
                    JOptionPane.showMessageDialog(this, "El host rechazó tu solicitud.", "Solicitud rechazada", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private JPanel panelRedondeado(Color color, int arco) {
        return new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arco, arco));
            }
            { setOpaque(false); }
        };
    }

    private JButton crearBotonAmarillo(String texto, int ancho, int alto) {
        JButton btn = new JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed() ? new Color(195, 155, 0) : AMARILLO);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20));
                g2.dispose();
                FontMetrics fm = g.getFontMetrics();
                int x = (getWidth()  - fm.stringWidth(getText())) / 2;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g.setColor(getForeground());
                g.setFont(getFont());
                g.drawString(getText(), x, y);
            }
            @Override protected void paintBorder(Graphics g) {}
        };
        btn.setFont(new Font("Arial Black", Font.BOLD, 15));
        btn.setForeground(new Color(15, 15, 15));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setPreferredSize(new Dimension(ancho, alto));
        btn.setMinimumSize(new Dimension(ancho, alto));
        btn.setMaximumSize(new Dimension(ancho, alto));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void habilitarArrastre(Component c) {
        MouseAdapter ma = new MouseAdapter() {
            private Point inicio;
            @Override public void mousePressed(MouseEvent e) {
                Component bajo = SwingUtilities.getDeepestComponentAt(e.getComponent(), e.getX(), e.getY());
                if (bajo instanceof JTextField || bajo instanceof JButton) { inicio = null; return; }
                inicio = e.getLocationOnScreen();
            }
            @Override public void mouseDragged(MouseEvent e) {
                if (inicio == null) return;
                Point p = e.getLocationOnScreen();
                setLocation(getX() + p.x - inicio.x, getY() + p.y - inicio.y);
                inicio = p;
            }
            @Override public void mouseReleased(MouseEvent e) { inicio = null; }
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