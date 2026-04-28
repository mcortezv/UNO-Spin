package assembler;
import conexion.Conexion;
import factory.FactoryConexion;
import factory.FactoryMVC;
import interfaces.IDispatcher;
import interfaces.IFactoryConexion;
import interfaces.IFactoryMVC;
import interfaces.IReceptor;
import mvc.MVC;

public class Main {

    static void main() {

        IFactoryMVC factoryMVC = new FactoryMVC();
        IFactoryConexion factoryConexion = new FactoryConexion();

        IReceptor receptorMVC = factoryMVC.crearMVC();
        IDispatcher dispatcherConn = factoryConexion.crearConn();

        Conexion.suscribir(receptorMVC);
        MVC.suscribir(dispatcherConn);

        MVC.iniciar();
    }
}
