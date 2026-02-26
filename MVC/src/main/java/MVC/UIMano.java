package MVC;

import MVC.interfaces.IComponent;
import MVC.styles.CustomScrollPane;
import dto.CartaDTO;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class UIMano extends JPanel implements IComponent {

    private static final int GAP_ENTRE_CARTAS = 6;
    private static final int PADDING_V = 8;
    private static final int PADDING_H = 12;

    private final JPanel panelCartas;
    private final CustomScrollPane scrollPane;

    private final List<UICarta> cartasUI = new ArrayList<>();
    private Consumer<CartaDTO> onCartaClickada;

    public UIMano() {
        setLayout(new BorderLayout());
        setOpaque(false);

        panelCartas = construirPanelCartas();
        scrollPane  = construirScrollPane();
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel construirPanelCartas() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, GAP_ENTRE_CARTAS, PADDING_V));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0, PADDING_H, 0, PADDING_H));
        return panel;
    }

    private CustomScrollPane construirScrollPane() {
        CustomScrollPane sp = new CustomScrollPane(
                panelCartas,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        sp.getHorizontalScrollBar().setUnitIncrement(UICarta.ANCHO / 2);
        return sp;
    }

    public void setCartas(List<CartaDTO> cartas) {
        panelCartas.removeAll();
        cartasUI.clear();

        for (CartaDTO dto : cartas) {
            UICarta uiCarta = new UICarta(dto);
            uiCarta.addOnSeleccionListener(() -> manejarSeleccion(uiCarta));
            cartasUI.add(uiCarta);
            panelCartas.add(uiCarta);
        }

        panelCartas.revalidate();
        panelCartas.repaint();
    }

//    public void marcarCartasJugables(List<CartaDTO> jugables) {
//        cartasUI.forEach(ui -> {
//            boolean esJugable = jugables.stream().anyMatch(j ->
//                    j.getValor().equals(ui.getCarta().getValor()) &&
//                            j.getColor() == ui.getCarta().getColor()
//            );
//            ui.setJugable(esJugable);
//        });
//    }

    public CartaDTO getCartaSeleccionada() {
        return cartasUI.stream()
                .filter(UICarta::isSeleccionada)
                .findFirst()
                .map(UICarta::getCarta)
                .orElse(null);
    }

//    public void setOnCartaClickada(Consumer<CartaDTO> callback) {
//        this.onCartaClickada = callback;
//    }

    private void manejarSeleccion(UICarta cartaClickada) {
        cartasUI.stream()
                .filter(ui -> ui != cartaClickada && ui.isSeleccionada())
                .forEach(ui -> ui.setSeleccionada(false));
        if (onCartaClickada != null && cartaClickada.isSeleccionada()) {
            onCartaClickada.accept(cartaClickada.getCarta());
        }
    }

    @Override public void execute() {}
}