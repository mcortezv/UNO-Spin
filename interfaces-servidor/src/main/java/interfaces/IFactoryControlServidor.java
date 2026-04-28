package interfaces;

public interface IFactoryControlServidor {

    IReceptor crearControlServidor(IBlackboard blackboard, IDispatcher dispatcherConn, ISerializer serializer);
}
