package assembler;
import blackboard.Blackboard;
import conexion.Conexion;
import blackboard.FactoryBlackboard;
import factory.FactoryConexion;
import factory.FactoryControlServidor;
import implementacion.JsonSerializer;
import interfaces.*;
import servidor.ControlServidor;

public class Main {

    static void main() {

        IFactoryBlackboard factoryBlackboard = new FactoryBlackboard();
        IFactoryControlServidor factoryControlServidor = new FactoryControlServidor();
        IFactoryConexion factoryConexion = new FactoryConexion();
        ISerializer serializer = new JsonSerializer();

        IBlackboard blackboard = factoryBlackboard.crearBlackboard(serializer);
        IDispatcher dispatcherConn = factoryConexion.crearConn();
        IReceptor receptorControlServidor = factoryControlServidor.crearControlServidor(blackboard, dispatcherConn, serializer);

        Conexion.suscribir(blackboard);
        Blackboard.suscribir(receptorControlServidor);
        ControlServidor.suscribir(dispatcherConn);

        ControlServidor.iniciar();
    }
}
