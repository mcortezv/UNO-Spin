package factory;
import dto.EstadoPartidaDTO;
import interfaces.IBlackboard;
import interfaces.IBlackboardObservador;
import interfaces.IDispatcher;
import interfaces.ISerializer;

/**
 * The type Control servidor.
 */
public class ControlServidor implements IBlackboardObservador {
    private IBlackboard blackboard;
    private final IDispatcher dispatcher;
    private final ISerializer serializer;

    /**
     * Instantiates a new Control servidor.
     *
     * @param blackboard the blackboard
     * @param dispatcher the dispatcher
     * @param serializer the serializer
     */
    ControlServidor(IBlackboard blackboard, IDispatcher dispatcher, ISerializer serializer) {
        this.blackboard = blackboard;
        this.dispatcher = dispatcher;
        this.serializer = serializer;
    }

    @Override
    public void update(IBlackboard blackboard) {
        this.blackboard = blackboard;
        for (String nombre : blackboard.getNombresConectados()) {
            EstadoPartidaDTO dto = blackboard.getEstadoParaJugador(nombre);
            if (dto == null) continue;
            String ip = blackboard.getIpJugador(nombre);
            int puerto = blackboard.getPuertoJugador(nombre);
            if (ip == null || puerto == 0) continue;
            dispatcher.enviar(serializer.serialize(dto), puerto, ip);
        }
    }
}
