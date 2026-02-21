package MVC;
import MVC.interfaces.IModeloLectura;
import MVC.interfaces.ISuscriptor;
import domain.Descarte;
import dto.CartaDTO;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaPrueba extends JFrame implements ISuscriptor {
    private final Controlador controlador;
    private IModeloLectura modeloLectura;
    private final JLabel lblDescarte;
    private final JPanel panelMano;

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
        panelMano.setLayout(new FlowLayout()); // Pone los botones uno al lado del otro
        panelMano.setBackground(Color.LIGHT_GRAY);
        add(panelMano, BorderLayout.SOUTH);
    }

    @Override
    public void update(IModeloLectura modelo) {
        System.out.println("La vista detectó un cambio y se está actualizando...");

        List<CartaDTO> descarte = modelo.getDescarte();
        if (descarte != null && !descarte.isEmpty()) {
            CartaDTO cartaCentro = descarte.get(0); // Tomamos la primera carta
            lblDescarte.setText("DESCARTE: " + cartaCentro.getNumero() + " " + cartaCentro.getColor());
        }
        panelMano.removeAll();

        List<CartaDTO> mano = modelo.getManoJugador();
        if (mano != null) {
            for (CartaDTO carta : mano) {
                JButton btnCarta = new JButton("Jugar: " + carta.getColor() + " " + carta.getNumero());
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
