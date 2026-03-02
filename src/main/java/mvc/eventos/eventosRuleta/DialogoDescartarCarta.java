package mvc.eventos.eventosRuleta;

import dto.CartaDTO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DialogoDescartarCarta extends DialogoCartas {
    public DialogoDescartarCarta(Frame owner, List<CartaDTO> cartas) {
        super(owner, "¡DESCARTAR CARTA!", cartas, true);
    }
    @Override
    protected String obtenerTextoBoton() {
        return "DESCARTAR";
    }

    @Override
    protected void alAceptar() {
        resultado = cartaSeleccionadaIdx;
    }
}
