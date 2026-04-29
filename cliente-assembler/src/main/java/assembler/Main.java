package assembler;
import conexion.Conexion;
import factory.FactoryConexion;
import factory.FactoryMVC;
import implementacion.JsonSerializer;
import interfaces.*;

public class Main {

    static void main() {
        int puertoCliente = 6000;

        IFactoryMVC factoryMVC = new FactoryMVC();
        IFactoryConexion factoryConexion = new FactoryConexion();
        ISerializer serializer = new JsonSerializer();

        IDispatcher dispatcherConn = factoryConexion.crearConn(puertoCliente);
        IReceptor receptorMVC = factoryMVC.crearMVC(serializer, dispatcherConn);

        Conexion.suscribir(receptorMVC);

        Conexion.iniciar();
    }
}
