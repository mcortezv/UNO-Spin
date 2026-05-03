package assembler;
import factory.Blackboard;
import factory.Conexion;
import factory.FactoryBlackboard;
import factory.FactoryConexion;
import factory.FactoryControlServidor;
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
        int puertoServidor = 5000;

        IFactoryBlackboard factoryBlackboard = new FactoryBlackboard();
        IFactoryControlServidor factoryControlServidor = new FactoryControlServidor();
        IFactoryConexion factoryConexion = new FactoryConexion();
        ISerializer serializer = new JsonSerializer();

        IBlackboard blackboard = factoryBlackboard.crearBlackboardObservable(serializer);
        IReceptor receptor = factoryBlackboard.crearReceptorBlackboard(serializer);

        IDispatcher dispatcherConn = factoryConexion.crearConn(puertoServidor);
        IBlackboardObservador controlServidor = factoryControlServidor.crearControlServidor(blackboard, dispatcherConn, serializer);

        Conexion.suscribir(receptor);
        Blackboard.suscribir(controlServidor);

        Conexion.iniciar();
    }
}