package assembler;
import conexion.Conexion;
import factory.FactoryConexion;
import factory.FactoryMVC;
import implementacion.JsonSerializer;
import interfaces.*;
import mvc.MVC;

public class Main {

    static void main() {

        IFactoryMVC factoryMVC = new FactoryMVC();
        IFactoryConexion factoryConexion = new FactoryConexion();
        ISerializer serializer = new JsonSerializer();

        IReceptor receptorMVC = factoryMVC.crearMVC(serializer);
        IDispatcher dispatcherConn = factoryConexion.crearConn();

        Conexion.suscribir(receptorMVC);
        MVC.suscribir(dispatcherConn);

        MVC.iniciar();
    }
}
