package factory;
import interfaces.*;
import servidor.ControlServidor;

public class FactoryControlServidor implements IFactoryControlServidor {

    @Override
    public IReceptor crearControlServidor(IBlackboard blackboard, IDispatcher dispatcherConn, ISerializer serializer) {
        return new ControlServidor(blackboard, dispatcherConn, serializer);
    }
}
