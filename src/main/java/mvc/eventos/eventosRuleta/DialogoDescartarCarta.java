package mvc.eventos.eventosRuleta;

import dto.CartaDTO;
import java.awt.Frame;
import java.util.List;

public class DialogoDescartarCarta extends DialogoCartas {
    public DialogoDescartarCarta(Frame owner, List<CartaDTO> cartas) {
        super(owner, "¡DESCARTAR CARTA!", cartas, true);
        construirDialogo("¡DESCARTAR CARTA!");
    }
    @Override
    protected String obtenerTextoBoton() { return "DESCARTAR"; }
    @Override
    protected void alAceptar() {
        if (cartaSeleccionadaIdx != -1) {
            resultado = cartaSeleccionadaIdx;
        }
    }
}