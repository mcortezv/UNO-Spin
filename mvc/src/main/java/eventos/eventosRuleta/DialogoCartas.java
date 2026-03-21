package eventos.eventosRuleta;

import dto.CartaDTO;
import eventos.DialogoEventoRuleta;
import op.UICarta;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DialogoCartas extends DialogoEventoRuleta {

    protected List<CartaDTO> cartas;
    protected int cartaSeleccionadaIdx = -1;
    private final boolean seleccionable;

    private final List<UICarta> cartasUI = new ArrayList<>();

    public DialogoCartas(Frame owner, String titulo,
                         List<CartaDTO> cartas, boolean seleccionable) {
        super(owner, titulo);
        this.cartas = cartas;
        this.seleccionable = seleccionable;
    }

    @Override
    protected JPanel crearContenidoCentral() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 4));
        panel.setOpaque(false);

        for (int i = 0; i < cartas.size(); i++) {
            CartaDTO carta = cartas.get(i);
            UICarta uiCarta = new UICarta(carta);

            if (seleccionable) {
                final int idx = i;
                final UICarta cartaActual = uiCarta;

                uiCarta.addOnSeleccionListener(() -> {
                    for (UICarta otra : cartasUI) {
                        if (otra != cartaActual && otra.isSeleccionada()) {
                            otra.setSeleccionada(false);
                        }
                    }
                    if (cartaActual.isSeleccionada()) {
                        cartaSeleccionadaIdx = idx;
                    } else {
                        cartaSeleccionadaIdx = -1;
                    }
                });
            }

            cartasUI.add(uiCarta);
            panel.add(uiCarta);
        }

        if (cartas.size() > 4) {
            int nuevoAncho = Math.max(420, cartas.size() * (UICarta.ANCHO + 12) + 60);
            setSize(nuevoAncho, getHeight());
        }

        return panel;
    }
}