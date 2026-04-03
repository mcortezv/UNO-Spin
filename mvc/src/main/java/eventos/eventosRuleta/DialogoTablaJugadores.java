package eventos.eventosRuleta;
import eventos.DialogoEventoRuleta;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The type Dialogo tabla jugadores.
 */
public abstract class DialogoTablaJugadores extends DialogoEventoRuleta {
    /**
     * The Nombres jugadores.
     */
    protected List<String> nombresJugadores;

    /**
     * Instantiates a new Dialogo tabla jugadores.
     *
     * @param owner     the owner
     * @param titulo    the titulo
     * @param jugadores the jugadores
     */
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

    /**
     * Crear contenido jugador j component.
     *
     * @param indice the indice
     * @return the j component
     */
    protected abstract JComponent crearContenidoJugador(int indice);
}