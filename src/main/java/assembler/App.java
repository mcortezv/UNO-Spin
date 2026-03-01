package assembler;
import mvc.Controlador;
import mvc.mock.ModeloMock;
import mvc.UITurnoJugador;
import mvc.mock.ModeloVistaJugador;
import java.util.List;

/**
 * The type App.
 */
public class App {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        //Modelo modelo = new Modelo();

        ModeloMock modelo = new ModeloMock();
        Controlador controlador = new Controlador(modelo);

        UITurnoJugador ventanaSebas = new UITurnoJugador(controlador, modelo, List.of(0, 0));
        UITurnoJugador ventanaGuasave = new UITurnoJugador(controlador, modelo, List.of(880, 400));

        modelo.subscribe(ventanaSebas);
        modelo.subscribe(ventanaGuasave);

        ModeloVistaJugador vistaSebas = new ModeloVistaJugador(0, modelo);
        ModeloVistaJugador vistaGuasave = new ModeloVistaJugador(1, modelo);
        ventanaSebas.update(vistaSebas);
        ventanaGuasave.update(vistaGuasave);

        ventanaSebas.setVisible(true);
        ventanaGuasave.setVisible(true);
    }
}
