package interfaces;

/**
 * The interface Factory control servidor.
 */
public interface IFactoryControlServidor {

    /**
     * Crear control servidor receptor.
     *
     * @param blackboard     the blackboard
     * @param dispatcherConn the dispatcher conn
     * @param serializer     the serializer
     * @return the receptor
     */
    IReceptor crearControlServidor(IBlackboard blackboard, IDispatcher dispatcherConn, ISerializer serializer);
}
