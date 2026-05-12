package factory;
import interfaces.IBlackboard;
import interfaces.IFactoryBlackboard;
import interfaces.IReceptor;
import interfaces.ISerializer;

/**
 * The type Factory blackboard.
 */
public class FactoryBlackboard implements IFactoryBlackboard {
    private Blackboard blackboard;

    private Blackboard crearBlackboard(ISerializer serializer){
        if (blackboard == null){
            this.blackboard = new Blackboard( serializer);
        }
        return blackboard;
    }

    @Override
    public IBlackboard crearBlackboardObservable(ISerializer serializer) {
        return crearBlackboard(serializer);
    }

    @Override
    public IReceptor crearReceptorBlackboard(ISerializer serializer) {
        return crearBlackboard(serializer);
    }
}
