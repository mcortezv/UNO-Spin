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

        ModeloVistaJugador modeloSebas = new ModeloVistaJugador(0, modelo);
        ModeloVistaJugador modeloGuasave = new ModeloVistaJugador(1, modelo);

        Controlador controladorSebas = new Controlador(modeloSebas);
        Controlador controladorGuasave = new Controlador(modeloGuasave);

        UITurnoJugador ventanaSebas = new UITurnoJugador(controladorSebas, modeloSebas, List.of(0, 0));
        UITurnoJugador ventanaGuasave = new UITurnoJugador(controladorGuasave, modeloGuasave, List.of(880, 400));

        modeloSebas.subscribe(ventanaSebas);
        modeloGuasave.subscribe(ventanaGuasave);

        ventanaSebas.update(modeloSebas);
        ventanaGuasave.update(modeloGuasave);

        ventanaSebas.setVisible(true);
        ventanaGuasave.setVisible(true);
    }
}
