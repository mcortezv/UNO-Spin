package MVC;
import MVC.interfaces.IComponent;
import MVC.interfaces.IModeloLectura;
import MVC.interfaces.ISuscriptor;
import MVC.styles.Button;
import dto.CartaDTO;
import dto.JugadorRivalDTO;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UITurnoJugador extends JFrame implements IComponent, ISuscriptor {

    private static final int ancho_ventana = 1040;
    private static final int alto_ventana = 680;

    private final Controlador controlador;
    private final IModeloLectura lectura;

    private final UITablero tablero;
    private final UIMano mano;
    private UIJugador uiJugadorArriba;
    private UIJugador uiJugadorIzq;
    private UIJugador uiJugadorDer;

    private final JLabel lblTurno;
    private final JButton btnTirarCarta;
    private final JButton btnUno;

    private final PanelFondo panelFondo;

    private final JPanel slotTop;
    private final JPanel slotLeft;
    private final JPanel slotRight;

    public UITurnoJugador(Controlador controlador, IModeloLectura modeloLectura) {
        super("UNO-SPIN");
        this.controlador = controlador;
        this.lectura = modeloLectura;

        this.tablero = new UITablero();
        this.mano = new UIMano();
        this.lblTurno = crearLabelTurno();
        this.btnTirarCarta = new Button("Tirar Carta", new Color(45,45,45));
        this.btnUno = new Button("UNO", new Color(185, 25,25));
        this.btnUno.setFont(new Font("Arial", Font.BOLD, 16));

        this.slotTop = crearSlot();
        this.slotLeft = crearSlot();
        this.slotRight = crearSlot();

        this.panelFondo = new PanelFondo();

        configurarVentana();
        construirLayout();
        conectarEventos();

    }


    private void construirLayout() {
        panelFondo.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(8, 0, 0, 0);
        panelFondo.add(slotTop, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;

        gbc.gridx = 0;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 8, 0, 0);
        panelFondo.add(slotLeft, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill  = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        panelFondo.add(tablero, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.0;
        gbc.fill  = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(0, 0, 0, 8);
        panelFondo.add(slotRight, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill   = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 8, 0);
        panelFondo.add(construirZonaJugadorActivo(), gbc);

        setContentPane(panelFondo);
    }

    private JPanel construirZonaJugadorActivo() {
        JPanel zona = new JPanel(new BorderLayout(10, 0));
        zona.setOpaque(false);
        zona.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));

        zona.add(lblTurno, BorderLayout.WEST);

        int altoMano = UICarta.ALTO + UICarta.ELEVACION + 22;
        mano.setPreferredSize(new Dimension(0, altoMano));
        zona.add(mano, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botones.setOpaque(false);
        botones.add(btnTirarCarta);
        botones.add(btnUno);
        zona.add(botones, BorderLayout.EAST);

        return zona;
    }

    private void conectarEventos() {
        btnTirarCarta.addActionListener(e -> {
            CartaDTO seleccionada = mano.getCartaSeleccionada();
            if (seleccionada != null) {
                controlador.onCartaJugada(seleccionada.getValor());
            }
        });

        btnUno.addActionListener(e -> controlador.onUnoGritado());
        tablero.setOnPedirCarta(controlador::onPedirCarta);
        tablero.setOnGiroCompleto(controlador::onSpinCompletado);
    }


    @Override
    public void update(IModeloLectura modelo) {
        SwingUtilities.invokeLater(() -> actualizarVista(modelo));
    }

    private void actualizarVista(IModeloLectura modelo) {
        lblTurno.setText("Turno: " + modelo.getNombreTurnoActual());
        mano.setCartas(modelo.getManoJugadorActual());
        if (modelo.getCartaCima() != null) {
            tablero.setCartaCima(modelo.getCartaCima());
        }
        if (modelo.isSpinActivo() && !tablero.getRuleta().isGirando()) {
            tablero.getRuleta().girar();
        }

        List<JugadorRivalDTO> rivales = modelo.getJugadoresRivales();
        actualizarRival(rivales, 0, slotTop,   UIJugador.Posicion.TOP);
        actualizarRival(rivales, 1, slotLeft,  UIJugador.Posicion.LEFT);
        actualizarRival(rivales, 2, slotRight, UIJugador.Posicion.RIGHT);
    }

    private void actualizarRival(List<JugadorRivalDTO> rivales, int idx, JPanel slot, UIJugador.Posicion posicion) {
        slot.removeAll();
        if (idx < rivales.size() && rivales.get(idx) != null) {
            JugadorRivalDTO dto = rivales.get(idx);
            if (jugadorPorPosicion(posicion) == null) {
                UIJugador uj = new UIJugador(dto, posicion);
                setJugadorPorPosicion(posicion, uj);
                slot.add(uj);
            } else {
                jugadorPorPosicion(posicion).actualizar(dto);
                slot.add(jugadorPorPosicion(posicion));
            }
        }
        slot.revalidate();
        slot.repaint();
    }

    private UIJugador jugadorPorPosicion(UIJugador.Posicion p) {
        return switch (p) {
            case TOP   -> uiJugadorArriba;
            case LEFT  -> uiJugadorIzq;
            case RIGHT -> uiJugadorDer;
        };
    }

    private void setJugadorPorPosicion(UIJugador.Posicion p, UIJugador uj) {
        switch (p) {
            case TOP   -> uiJugadorArriba   = uj;
            case LEFT  -> uiJugadorIzq  = uj;
            case RIGHT -> uiJugadorDer = uj;
        }
    }

    private JPanel crearSlot() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        return p;
    }

    private JLabel crearLabelTurno() {
        JLabel lbl = new JLabel("Turno: ...");
        lbl.setFont(new Font("Arial", Font.BOLD, 13));
        lbl.setForeground(Color.WHITE);
        lbl.setOpaque(true);
        lbl.setBackground(new Color(0, 0, 0, 160));
        lbl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 70), 1),
                BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        return lbl;
    }


    private void configurarVentana() {
        setSize(ancho_ventana, alto_ventana);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    @Override public void execute() {}

    private static class PanelFondo extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            float cx = getWidth() / 2f;
            float cy = getHeight() / 2f;
            float radio = (float) Math.sqrt(cx * cx + cy * cy) * 1.1f;
            RadialGradientPaint grad = new RadialGradientPaint(
                    cx, cy, radio,
                    new float[]{0f, 1f},
                    new Color[]{new Color(180, 35, 35), new Color(75, 5, 5)}
            );
            g2.setPaint(grad);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
    }
}
