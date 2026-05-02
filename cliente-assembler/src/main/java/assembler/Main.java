package assembler;
import factory.Conexion;
import factory.FactoryConexion;
import factory.FactoryMVC;
import implementacion.JsonSerializer;
import interfaces.*;

/**
 * The type Main.
 */
public class Main {

    /**
     * Main.
     */
    public static void main(String[] args) {
        int puertoCliente = args.length > 0 ? Integer.parseInt(args[0]) : 6000;
        System.setProperty("puerto.cliente", String.valueOf(puertoCliente));

        IFactoryMVC factoryMVC = new FactoryMVC();
        IFactoryConexion factoryConexion = new FactoryConexion();
        ISerializer serializer = new JsonSerializer();

        IDispatcher dispatcherConn = factoryConexion.crearConn(puertoCliente);

        Conexion.iniciar();

        IReceptor receptorMVC = factoryMVC.crearMVC(serializer, dispatcherConn);

        Conexion.suscribir(receptorMVC);
    }
}