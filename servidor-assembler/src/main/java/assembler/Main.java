package assembler;
import blackboard.Blackboard;
import conexion.Conexion;
import factory.FactoryBlackboard;
import factory.FactoryConexion;
import factory.FactoryControlServidor;
import interfaces.*;
import servidor.ControlServidor;

public class Main {

    static void main() {

        IFactoryBlackboard factoryBlackboard = new FactoryBlackboard();
        IFactoryControlServidor factoryControlServidor = new FactoryControlServidor();
        IFactoryConexion factoryConexion = new FactoryConexion();

        IReceptor receptorBlackboard = factoryBlackboard.crearBlackboard();
        IReceptor receptorControlServidor = factoryControlServidor.crearControlServidor();
        IDispatcher dispatcherConn = factoryConexion.crearConn();

        Conexion.suscribir(receptorBlackboard);
        Blackboard.suscribir(receptorControlServidor);
        ControlServidor.suscribir(dispatcherConn);

        ControlServidor.iniciar();
    }
}
