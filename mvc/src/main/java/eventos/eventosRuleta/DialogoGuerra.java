package eventos.eventosRuleta;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DialogoGuerra extends DialogoTablaJugadores {
    private List<ImageIcon> cartasAltas;

    public DialogoGuerra(Frame owner, List<String> jugadores) {
        super(owner, "¡GUERRA!", jugadores);
        this.cartasAltas = new ArrayList<>();
        for (int i = 0; i < jugadores.size(); i++) {
            this.cartasAltas.add(new ImageIcon());
        }
        construirDialogo("¡GUERRA!");
    }

    @Override
    protected String obtenerDescripcion() { return "CARTA MAS ALTA DE CADA JUGADOR"; }

    @Override
    protected JComponent crearContenidoJugador(int indice) {
        return new JLabel(cartasAltas.get(indice));
    }

    @Override
    protected void alAceptar() { }
}