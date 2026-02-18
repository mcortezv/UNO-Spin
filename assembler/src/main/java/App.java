
import classes.Modelo;
import controllers.Controlador;
import view.VentanaPrueba;

    public class App {

        public static void main(String[] args) {
            Modelo modelo = new Modelo();
            Controlador controlador = new Controlador(modelo);
            VentanaPrueba ventana = new VentanaPrueba(controlador, modelo);
            modelo.subscribe(ventana);
            ventana.update(modelo);
            ventana.setVisible(true);
        }
}
