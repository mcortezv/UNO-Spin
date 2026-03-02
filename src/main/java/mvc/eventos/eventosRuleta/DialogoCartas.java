package mvc.eventos.eventosRuleta;

import mvc.eventos.DialogoEventoRuleta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public abstract class DialogoCartas extends DialogoEventoRuleta {

    protected List<ImageIcon> imagenesCartas;
    protected int cartaSeleccionadaIdx = -1;
    private final boolean seleccionable;

    public DialogoCartas(Frame owner, String titulo,
                         List<ImageIcon> cartas, boolean seleccionable) {
        super(owner, "");
        this.imagenesCartas = cartas;
        this.seleccionable = seleccionable;
    }

    @Override
    protected JPanel crearContenidoCentral() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel.setOpaque(false);

        for (int i = 0; i < imagenesCartas.size(); i++) {
            JLabel lblCarta = new JLabel(imagenesCartas.get(i));

            if (seleccionable) {
                final int idx = i;
                lblCarta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                lblCarta.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cartaSeleccionadaIdx = idx;
                        lblCarta.setBorder(
                                BorderFactory.createLineBorder(Color.YELLOW, 3));
                    }
                });
            }

            panel.add(lblCarta);
        }
        return panel;
    }
}

