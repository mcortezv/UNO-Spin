package assembler;
import mvc.Controlador;
import mvc.ModeloMock;
import mvc.UITurnoJugador;
import javax.swing.*;
import java.awt.*;

public class App {

        public static void main(String[] args) {
            //Modelo modelo = new Modelo();
            ModeloMock modelo = new ModeloMock();
            Controlador controlador = new Controlador(modelo);
            UITurnoJugador ventana = new UITurnoJugador(controlador, modelo);
            modelo.subscribe(ventana);
            ventana.update(modelo);
            ventana.setVisible(true);
        }
}
