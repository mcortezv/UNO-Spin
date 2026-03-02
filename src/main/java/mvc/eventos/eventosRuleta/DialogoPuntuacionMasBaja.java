package mvc.eventos.eventosRuleta;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class DialogoPuntuacionMasBaja extends DialogoTablaJugadores {
        private List<Integer> puntajes;

        public DialogoPuntuacionMasBaja(Frame owner, List<String> jugadores, List<Integer> pts) {
            super(owner, "¡PUNTUACION MAS BAJA!", jugadores);
            this.puntajes = pts;
        }

        @Override
        protected String obtenerDescripcion() {
            return "CONTEO DE PUNTOS DE CADA JUGADOR";
        }

        @Override
        protected JComponent crearContenidoJugador(int indice) {
            JLabel lbl = new JLabel(String.valueOf(puntajes.get(indice)), SwingConstants.CENTER);
            lbl.setFont(new Font("Arial", Font.PLAIN, 18));
            return lbl;
        }

        @Override
        protected void alAceptar() {
            int minIdx = puntajes.indexOf(Collections.min(puntajes));
            resultado = nombresJugadores.get(minIdx);
        }
    }
