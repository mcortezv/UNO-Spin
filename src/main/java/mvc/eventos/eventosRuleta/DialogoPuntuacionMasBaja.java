package mvc.eventos.eventosRuleta;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DialogoPuntuacionMasBaja extends DialogoTablaJugadores {
    private final List<Integer> puntajes;


    public DialogoPuntuacionMasBaja(Frame owner, List<String> jugadores, List<Integer> puntajesReales) {
        super(owner, "¡PUNTUACION MAS BAJA!", jugadores);
        this.puntajes = new ArrayList<>(puntajesReales);
        construirDialogo("¡PUNTUACION MAS BAJA!");
    }

    @Override
    protected String obtenerDescripcion() {
        return "CONTEO DE PUNTOS DE CADA JUGADOR";
    }

    @Override
    protected JComponent crearContenidoJugador(int indice) {
        JLabel lbl = new JLabel(String.valueOf(puntajes.get(indice)), SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        return lbl;
    }

    @Override
    protected void alAceptar() {
        int minPuntaje = Collections.min(puntajes);
        int minIdx = puntajes.indexOf(minPuntaje);
        resultado = nombresJugadores.get(minIdx);
    }
}