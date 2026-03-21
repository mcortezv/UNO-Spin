package eventos.eventosRuleta;

import eventos.DialogoEventoRuleta;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public abstract class DialogoTablaJugadores extends DialogoEventoRuleta {

    protected List<String> nombresJugadores;

    public DialogoTablaJugadores(Frame owner, String titulo, List<String> jugadores) {
        super(owner, titulo);
        this.nombresJugadores = jugadores;
    }

    @Override
    protected JPanel crearContenidoCentral() {
        JPanel panel = new JPanel(new GridLayout(2, nombresJugadores.size(), 20, 5));
        panel.setOpaque(false);

        for (String nombre : nombresJugadores) {
            JLabel lbl = new JLabel(nombre, SwingConstants.CENTER);
            lbl.setFont(new Font("Arial", Font.BOLD, 11));
            panel.add(lbl);
        }

        for (int i = 0; i < nombresJugadores.size(); i++) {
            panel.add(crearContenidoJugador(i));
        }

        return panel;
    }

    protected abstract JComponent crearContenidoJugador(int indice);
}