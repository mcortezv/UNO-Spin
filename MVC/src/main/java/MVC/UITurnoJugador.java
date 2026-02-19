package MVC;
import MVC.interfaces.IComponent;
import javax.swing.*;

public class UITurnoJugador extends JFrame implements IComponent {
    private Controlador controlador;
    private UITablero tablero;
    private UIMano mano;
    private UIDescarte descarte;
    private UIMazo mazo;

    public UITurnoJugador(Controlador controlador) {
        this.controlador = controlador;
    }

    @Override
    public void execute(){}
}
