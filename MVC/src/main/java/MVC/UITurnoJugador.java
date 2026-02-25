package MVC;
import MVC.interfaces.IComponent;
import MVC.interfaces.IModeloLectura;
import MVC.interfaces.ISuscriptor;

import javax.swing.*;

public class UITurnoJugador extends JFrame implements IComponent, ISuscriptor {
    private final Controlador controlador;
    private final IModeloLectura lectura;
    private UITablero tablero;
    private UIMano mano;
    private UIDescarte descarte;
    private UIMazo mazo;

    public UITurnoJugador(Controlador controlador, IModeloLectura modeloLectura) {
        this.controlador = controlador;
        this.lectura = modeloLectura;
    }

    @Override
    public void execute(){}

    @Override
    public void update(IModeloLectura modelo) {

    }
}
