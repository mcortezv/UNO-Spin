package factory;
import interfaces.*;

/**
 * The type Factory control servidor.
 */
public class FactoryControlServidor implements IFactoryControlServidor {

    @Override
    public IReceptor crearControlServidor(IBlackboard blackboard, IDispatcher dispatcherConn, ISerializer serializer) {
        return new ControlServidor(blackboard, dispatcherConn, serializer);
    }
}
