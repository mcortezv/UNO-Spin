package lobby;

import dto.JugadorDTO;
import dto.SolicitudUnionDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

public class UIPantallaCarga extends JFrame implements ISuscriptorLobby {

    private static final Color AZUL_PANEL = new Color(52, 62, 125);
    private static final Color AMARILLO = new Color(190, 165, 45);
    private static final Color VERDE = new Color(60, 160, 60);
    private static final Color ROJO = new Color(180, 50, 50);

    private final LobbyControlador controlador;

    private JLabel labelTitulo;
    private JLabel labelEstado;
    private DefaultTableModel tableModel;
    private JPanel panelSolicitudes;

    public UIPantallaCarga(LobbyControlador controlador) {
        this.controlador = controlador;
        construirUI();
    }

    private void construirUI() {
        setTitle("Pantalla Carga");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050, 650);
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
        marcoOscuro.setPreferredSize(new Dimension(870, 540));

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
        panelAzul.setPreferredSize(new Dimension(830, 500));
        panelAzul.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;

        labelTitulo = new JLabel("Esperando Respuesta");
        labelTitulo.setFont(new Font("Arial Black", Font.BOLD, 38));
        labelTitulo.setForeground(Color.WHITE);
        gbc.insets = new Insets(20, 20, 6, 20);
        panelAzul.add(labelTitulo, gbc);

        labelEstado = new JLabel(" ");
        labelEstado.setFont(new Font("Arial", Font.BOLD, 15));
        labelEstado.setForeground(new Color(255, 120, 120));
        gbc.insets = new Insets(0, 20, 4, 20);
        panelAzul.add(labelEstado, gbc);

        panelAzul.add(construirTabla(), gbc);

        panelSolicitudes = new JPanel();
        panelSolicitudes.setOpaque(false);
        panelSolicitudes.setLayout(new BoxLayout(panelSolicitudes, BoxLayout.Y_AXIS));
        panelSolicitudes.setVisible(false);
        gbc.insets = new Insets(10, 20, 0, 20);
        panelAzul.add(panelSolicitudes, gbc);

        JButton botonIniciar = crearBotonIniciar();
        botonIniciar.addActionListener(e -> controlador.solicitarInicio());
        gbc.insets = new Insets(15, 20, 20, 20);
        panelAzul.add(botonIniciar, gbc);

        GridBagConstraints gbcInner = new GridBagConstraints();
        gbcInner.insets = new Insets(20, 20, 20, 20);
        marcoOscuro.add(panelAzul, gbcInner);
        fondo.add(marcoOscuro, new GridBagConstraints());
    }

    private JPanel construirTabla() {
        tableModel = new DefaultTableModel(new Object[]{"Jugador", "Avatar"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        JTable tabla = new JTable(tableModel);
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        tabla.setRowHeight(30);
        tabla.setBackground(Color.WHITE);
        tabla.setForeground(Color.BLACK);
        tabla.setGridColor(new Color(200, 200, 200));
        tabla.setShowGrid(true);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(300);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(60);

        JTableHeader header = tabla.getTableHeader();
        header.setBackground(new Color(20, 20, 20));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(header.getWidth(), 36));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tabla.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(400, 130));
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

        JPanel wrapper = new JPanel(new FlowLayout());
        wrapper.setOpaque(false);
        wrapper.add(scroll);
        return wrapper;
    }

    private void actualizarTabla(List<JugadorDTO> jugadores) {
        tableModel.setRowCount(0);
        if (jugadores == null) return;
        int i = 1;
        for (JugadorDTO j : jugadores) {
            tableModel.addRow(new Object[]{j.getNombre(), i++});
        }
    }

    private void actualizarSolicitudes(List<SolicitudUnionDTO> solicitudes) {
        panelSolicitudes.removeAll();
        if (solicitudes == null || solicitudes.isEmpty()) {
            panelSolicitudes.setVisible(false);
            panelSolicitudes.revalidate();
            panelSolicitudes.repaint();
            return;
        }

        JLabel titulo = new JLabel("Solicitudes pendientes:");
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelSolicitudes.add(titulo);
        panelSolicitudes.add(Box.createVerticalStrut(6));

        for (SolicitudUnionDTO s : solicitudes) {
            if (!"PENDIENTE".equals(s.getEstado())) continue;
            String nombre = s.getJugadorDTO() != null ? s.getJugadorDTO().getNombre() : "?";

            JPanel fila = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            fila.setOpaque(false);

            JLabel lblNombre = new JLabel(nombre);
            lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
            lblNombre.setForeground(Color.WHITE);
            lblNombre.setPreferredSize(new Dimension(200, 30));

            JButton btnAceptar = crearBotonPequeno("ACEPTAR", VERDE);
            btnAceptar.addActionListener(e -> controlador.aceptarSolicitud(nombre));

            JButton btnRechazar = crearBotonPequeno("RECHAZAR", ROJO);
            btnRechazar.addActionListener(e -> controlador.rechazarSolicitud(nombre));

            fila.add(lblNombre);
            fila.add(btnAceptar);
            fila.add(btnRechazar);
            panelSolicitudes.add(fila);
        }

        panelSolicitudes.setVisible(true);
        panelSolicitudes.revalidate();
        panelSolicitudes.repaint();
    }

    private JButton crearBotonPequeno(String texto, Color color) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getModel().isPressed() ? color.darker() : color);
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                g2d.dispose();
                super.paintComponent(g);
            }
            @Override protected void paintBorder(Graphics g) {}
        };
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setForeground(Color.WHITE);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setOpaque(false);
        boton.setPreferredSize(new Dimension(90, 28));
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private JButton crearBotonIniciar() {
        JButton boton = new JButton("INICIAR") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getModel().isPressed() ? AMARILLO.darker() : AMARILLO);
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 24, 24));
                g2d.dispose();
                super.paintComponent(g);
            }
            @Override protected void paintBorder(Graphics g) {}
        };
        boton.setFont(new Font("Arial Black", Font.BOLD, 20));
        boton.setForeground(Color.WHITE);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setOpaque(false);
        boton.setPreferredSize(new Dimension(160, 52));
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return boton;
    }

    @Override
    public void actualizar(IModeloLobbyLectura modelo) {
        SwingUtilities.invokeLater(() -> {
            switch (modelo.getEstadoUnion()) {
                case PENDIENTE:
                    labelTitulo.setText("Esperando Respuesta");
                    labelEstado.setText(" ");
                    setVisible(true);
                    break;
                case EN_SALA:
                    labelTitulo.setText("JUGADORES EN SESION");
                    labelEstado.setText(" ");
                    actualizarTabla(modelo.getJugadoresEnSala());
                    actualizarSolicitudes(modelo.getSolicitudesPendientes());
                    setVisible(true);
                    break;
                case SOLICITUD_PENDIENTE:
                    mostrarDialogoConfirmacion();
                    break;
                case RECHAZADA:
                    setVisible(false);
                    break;
                case EN_JUEGO:
                    dispose();
                    break;
                case SOLICITUD_CANCELADA:
                    labelTitulo.setText("JUGADORES EN SESION");
                    labelEstado.setText("Un jugador ha rechazado el inicio del juego");
                    actualizarSolicitudes(modelo.getSolicitudesPendientes());
                    setVisible(true);
                    break;
            }
        });
    }
    
    private void mostrarDialogoConfirmacion(){
        int respuesta= JOptionPane.showConfirmDialog(this, "Un jugador quiere iniciar, confirmas?", "Solicitud de Inicio", JOptionPane.YES_NO_OPTION);
        if(respuesta == JOptionPane.YES_OPTION){
            controlador.confirmarInicio();
        } else{
            controlador.negarInicio();
        }
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

}
