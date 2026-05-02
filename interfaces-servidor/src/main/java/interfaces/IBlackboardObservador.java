package interfaces;

/**
 * The interface Blackboard observador.
 */
public interface IBlackboardObservador {

    /**
     * Recibir mensaje.
     *
     * @param blackboard the blackboard
     */
    void update(IBlackboard blackboard);
}