package dialogs;
import javax.swing.*;
import java.awt.*;

/**
 * The type Dialogo evento ruleta.
 */
public abstract class DialogoEventoRuleta extends JDialog {
    /**
     * The Panel contenido.
     */
    protected JPanel panelContenido;
    /**
     * The Resultado.
     */
    protected Object resultado;
    /**
     * The Solo lectura.
     */
    protected boolean soloLectura = false;

    /**
     * Instantiates a new Dialogo evento ruleta.
     *
     * @param owner  the owner
     * @param titulo the titulo
     */
    public DialogoEventoRuleta(Frame owner, String titulo) {
        super(owner, true);
        setUndecorated(true);
        setSize(420, 280);
        setLocationRelativeTo(owner);
    }

    /**
     * Construir dialogo.
     *
     * @param titulo the titulo
     */
    protected void construirDialogo(String titulo) {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(new Color(255, 255, 255, 240));
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        panelPrincipal.add(crearEncabezado(), BorderLayout.NORTH);

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

        panelContenido = crearContenidoCentral();

        JPanel centro = new JPanel();
        centro.setOpaque(false);
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.add(lblTitulo);

        if (obtenerDescripcion() != null) {
            JLabel desc = new JLabel(
                    "<html><center>" + obtenerDescripcion() + "</center></html>",
                    SwingConstants.CENTER);
            desc.setFont(new Font("Arial", Font.PLAIN, 14));
            desc.setAlignmentX(CENTER_ALIGNMENT);
            centro.add(Box.createVerticalStrut(10));
            centro.add(desc);
        }

        centro.add(Box.createVerticalStrut(15));
        centro.add(panelContenido);

        panelPrincipal.add(centro, BorderLayout.CENTER);

        JButton btnAceptar = crearBotonAceptar();
        btnAceptar.addActionListener(e -> {
            alAceptar();
            if (soloLectura || resultado != null) {
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor selecciona una opción antes de aceptar.");
            }
        });

        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        panelBoton.add(btnAceptar);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
    }

    /**
     * Crear contenido central j panel.
     *
     * @return the j panel
     */
    protected abstract JPanel crearContenidoCentral();

    /**
     * Al aceptar.
     */
    protected abstract void alAceptar();

    /**
     * Obtener descripcion string.
     *
     * @return the string
     */
    protected String obtenerDescripcion() { return null; }

    /**
     * Obtener texto boton string.
     *
     * @return the string
     */
    protected String obtenerTextoBoton() { return "ACEPTAR"; }

    private JPanel crearEncabezado() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JSeparator sep = new JSeparator();
        header.add(sep, BorderLayout.CENTER);
        JButton btnCerrar = new JButton("✕");
        btnCerrar.setBorderPainted(false);
        btnCerrar.setContentAreaFilled(false);
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 18));
        btnCerrar.addActionListener(e -> dispose());
        header.add(btnCerrar, BorderLayout.EAST);
        return header;
    }

    private JButton crearBotonAceptar() {
        JButton btn = new JButton(obtenerTextoBoton());
        btn.setBackground(new Color(255, 215, 0));
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 30, 8, 30));
        return btn;
    }

    /**
     * Gets resultado.
     *
     * @return the resultado
     */
    public Object getResultado() {
        return resultado;
    }

    /**
     * Sets solo lectura.
     *
     * @param soloLectura the solo lectura
     */
    public void setSoloLectura(boolean soloLectura) {
        this.soloLectura = soloLectura;
    }
}