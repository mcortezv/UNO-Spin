package mvc.eventos.eventosRuleta;

import dto.CartaDTO;
import mvc.CargadorAssets;
import mvc.eventos.DialogoEventoRuleta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public abstract class DialogoCartas extends DialogoEventoRuleta {

    protected List<CartaDTO> cartas;

    protected int cartaSeleccionadaIdx = -1;
    private final boolean seleccionable;

    public DialogoCartas(Frame owner, String titulo,
                         List<CartaDTO> cartas, boolean seleccionable) {
        super(owner, "");
        this.cartas = cartas;
        this.seleccionable = seleccionable;
    }

    @Override
    protected JPanel crearContenidoCentral() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel.setOpaque(false);

        for (int i = 0; i < cartas.size(); i++) {
            CartaDTO carta = cartas.get(i);
            Image img = CargadorAssets.getInstance()
                    .getCartaEscalada(carta.getValor(), 60, 86);
            JLabel lblCarta = new JLabel(new ImageIcon(img));

            if (seleccionable) {
                final int idx = i;
                lblCarta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                lblCarta.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cartaSeleccionadaIdx = idx;
                        for (Component c : panel.getComponents()) {
                            ((JComponent) c).setBorder(null);
                        }
                        lblCarta.setBorder(
                                BorderFactory.createLineBorder(new Color(255, 200, 0), 3));
                    }
                });
            }
            panel.add(lblCarta);
        }
        return panel;
    }
}

