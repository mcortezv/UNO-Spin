package mvc;
import mvc.interfaces.IModeloLectura;
import mvc.interfaces.ISuscriptor;
import dto.CartaDTO;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The type Ventana prueba.
 */
public class VentanaPrueba extends JFrame implements ISuscriptor {
    private final Controlador controlador;
    private IModeloLectura modeloLectura;
    private final JLabel lblDescarte;
    private final JPanel panelMano;

    /**
     * Instantiates a new Ventana prueba.
     *
     * @param controlador   the controlador
     * @param modeloLectura the modelo lectura
     */
    public VentanaPrueba(Controlador controlador, IModeloLectura modeloLectura) {
        this.controlador = controlador;
        this.modeloLectura = modeloLectura;
        setTitle("Prueba Arquitectura UNO");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        lblDescarte = new JLabel("Descarte: [Vacio]", SwingConstants.CENTER);
        lblDescarte.setFont(new Font("Arial", Font.BOLD, 24));
        add(lblDescarte, BorderLayout.CENTER);
        panelMano = new JPanel();
        panelMano.setLayout(new FlowLayout());
        panelMano.setBackground(Color.LIGHT_GRAY);
        add(panelMano, BorderLayout.SOUTH);
    }

    @Override
    public void update(IModeloLectura modelo) {
        System.out.println("La vista detectó un cambio y se está actualizando...");

        List<CartaDTO> descarte = modelo.getDescarte();
        if (descarte != null && !descarte.isEmpty()) {
            CartaDTO cartaCentro = descarte.get(0);
            lblDescarte.setText("DESCARTE: " + cartaCentro.getValor() + " " + cartaCentro.getColor());
        }
        panelMano.removeAll();

        List<CartaDTO> mano = modelo.getManoJugador();
        if (mano != null) {
            for (CartaDTO carta : mano) {
                JButton btnCarta = new JButton("Jugar: " + carta.getColor() + " " + carta.getValor());
                btnCarta.addActionListener(e -> {
                    System.out.println("Clic en botón: " + carta.getColor());
                    controlador.jugarCarta(carta);
                });
                panelMano.add(btnCarta);
            }
        }
        panelMano.revalidate();
        panelMano.repaint();
    }
}
