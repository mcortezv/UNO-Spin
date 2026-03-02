package mvc.eventos.eventosRuleta;

import dto.CartaDTO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DialogoMostrarMano extends DialogoCartas {
    private String nombreJugador;

    public DialogoMostrarMano(Frame owner, String jugador, List<CartaDTO> cartas) {
        super(owner, "¡MOSTRAR MANO!", cartas, false);
        this.nombreJugador = jugador;
    }

    @Override
    protected String obtenerDescripcion() {
        return "CARTAS DE " + nombreJugador.toUpperCase();
    }

    @Override
    protected void alAceptar() {
    }
}
