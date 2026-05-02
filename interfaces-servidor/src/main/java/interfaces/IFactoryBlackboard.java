package interfaces;

/**
 * The interface Factory blackboard.
 */
public interface IFactoryBlackboard {

    /**
     * Crear blackboard blackboard.
     *
     * @param serializer the serializer
     * @return the blackboard
     */
    IBlackboard crearBlackboardObservable(ISerializer serializer);

    IReceptor crearReceptorBlackboard(ISerializer serializer);
}
