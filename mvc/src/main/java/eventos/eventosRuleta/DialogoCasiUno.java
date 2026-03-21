package eventos.eventosRuleta;

import dto.CartaDTO;
import java.awt.Frame;
import java.util.List;

public class DialogoCasiUno extends DialogoCartas {
    public DialogoCasiUno(Frame owner, List<CartaDTO> cartas) {
        super(owner, "¡CASI UNO!", cartas, false);
        construirDialogo("¡CASI UNO!");
    }

    @Override
    protected String obtenerDescripcion() { return "¡QUEDATE CON DOS CARTAS!"; }
    @Override
    protected void alAceptar() { }
}