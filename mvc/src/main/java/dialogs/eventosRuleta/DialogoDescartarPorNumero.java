package dialogs.eventosRuleta;
import dialogs.DialogoEventoRuleta;
import javax.swing.*;
import java.awt.*;

/**
 * The type Dialogo descartar por numero.
 */
public class DialogoDescartarPorNumero extends DialogoEventoRuleta {
    private int numeroElegido = -1;

    /**
     * Instantiates a new Dialogo descartar por numero.
     *
     * @param owner the owner
     */
    public DialogoDescartarPorNumero(Frame owner) {
        super(owner, "¡DESCARTAR POR NUMERO!");
        construirDialogo("¡DESCARTAR POR NUMERO!");
    }

    @Override
    protected String obtenerDescripcion() {
        return "TU MANO DESCARTARA TODAS LAS CARTAS DE ESE NUMERO";
    }

    @Override
    protected JPanel crearContenidoCentral() {
        JPanel panel = new JPanel(new GridLayout(2, 5, 8, 8));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(250, 90));

        ButtonGroup grupo = new ButtonGroup();
        for (int i = 0; i <= 9; i++) {
            final int num = i;
            JToggleButton btn = new JToggleButton(String.valueOf(i));
            btn.setPreferredSize(new Dimension(42, 38));
            btn.setMargin(new Insets(0, 0, 0, 0));
            btn.setHorizontalAlignment(SwingConstants.CENTER);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.addActionListener(e -> {
                numeroElegido = num;
                btn.setBackground(new Color(255, 200, 0));
            });
            grupo.add(btn);
            panel.add(btn);
        }
        return panel;
    }

    @Override
    protected void alAceptar() {
        resultado = numeroElegido;
    }
}
