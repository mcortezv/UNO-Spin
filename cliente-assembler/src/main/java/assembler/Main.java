package assembler;
import factory.Conexion;
import factory.FactoryConexion;
import factory.FactoryMVC;
import implementacion.Serializer;
import interfaces.*;

/**
 * The type Main.
 */
public class Main {

    /**
     * Main.
     */
    public static void main(String[] args) {
        int puertoCliente;
        if (args.length > 0) {
            puertoCliente = Integer.parseInt(args[0]);
            System.setProperty("puerto.cliente", String.valueOf(puertoCliente));
        } else {
            puertoCliente = Integer.parseInt(System.getProperty("puerto.cliente", "6000"));
        }

        IFactoryMVC factoryMVC = new FactoryMVC();
        IFactoryConexion factoryConexion = new FactoryConexion();
        ISerializer serializer = new Serializer();

        IDispatcher dispatcherConn = factoryConexion.crearConn(puertoCliente);

        IReceptor receptorMVC = factoryMVC.crearMVC(serializer, dispatcherConn);

        Conexion.suscribir(receptorMVC);
        Conexion.iniciar();
    }
}
