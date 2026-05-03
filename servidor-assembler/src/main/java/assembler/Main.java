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
        IDispatcher dispatcherConn = factoryConexion.crearConn(puertoServidor);
        IReceptor receptor = factoryBlackboard.crearReceptorBlackboard(serializer);
        IBlackboardObservador controlServidor = factoryControlServidor.crearControlServidor(blackboard, dispatcherConn, serializer);

        if (controlServidor instanceof IReceptor receptorControlServidor) {
            Conexion.suscribir(receptorControlServidor);
        }
        Conexion.suscribir(receptor);
        Blackboard.suscribir(controlServidor);

        Conexion.iniciar();
    }
}
