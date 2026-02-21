package MVC;
import MVC.interfaces.IComponent;
import javax.swing.*;

public class UIJugador implements IComponent {
    private UIMano uiMano;
    private JLabel lblNombre;
    private JLabel lblAvatar;
    private JLabel lblCantidadCartas;

    public UIJugador(UIMano uiMano) {
        this.uiMano = uiMano;
    }

    @Override
    public void execute(){}
}
