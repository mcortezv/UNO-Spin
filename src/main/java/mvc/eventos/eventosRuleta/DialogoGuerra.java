package mvc.eventos.eventosRuleta;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DialogoGuerra extends DialogoTablaJugadores {
        private List<ImageIcon> cartasAltas;

        public DialogoGuerra(Frame owner, List<String> jugadores, List<ImageIcon> cartas) {
            super(owner, "¡GUERRA!", jugadores);
            this.cartasAltas = cartas;
        }

        @Override
        protected String obtenerDescripcion() {
            return "CARTA MAS ALTA DE CADA JUGADOR";
        }

        @Override
        protected JComponent crearContenidoJugador(int indice) {
            return new JLabel(cartasAltas.get(indice));
        }

        @Override
        protected void alAceptar() {
        }
    }
