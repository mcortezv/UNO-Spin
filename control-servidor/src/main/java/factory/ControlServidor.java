package factory;
import interfaces.*;
import dto.CartaDTO;
import dto.EstadoPartidaDTO;
import dto.JugadorDTO;
import dto.TipoEventoRuletaDTO;
import util.SocketCliente;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Control servidor.
 */
public class ControlServidor implements IBlackboardObservador, IControlServidor {
    private IBlackboard blackboard;
    private final IDispatcher dispatcher;
    private final ISerializer serializer;
    private final List<SocketCliente> clientes = new ArrayList<>();

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
    public void registrarCliente(int indiceJugador, String ip, int puerto) {
        clientes.removeIf(c -> c.getIndiceJugador() == indiceJugador);
        clientes.add(new SocketCliente(indiceJugador, ip, puerto));
    }

    @Override
    public void desconectarCliente(int indiceJugador) {
        clientes.removeIf(c -> c.getIndiceJugador() == indiceJugador);
    }

    private void broadcastEstado() {
        for (SocketCliente cliente : clientes) {
            EstadoPartidaDTO dto = buildEstadoPartida(cliente.getIndiceJugador());
            String json = serializer.serialize(dto);
            dispatcher.enviar(json, cliente.getPuerto(), cliente.getIp());
        }
    }

    private EstadoPartidaDTO buildEstadoPartida(int indiceJugador) {
        List<JugadorDTO> jugadores = blackboard.getJugadores();
        CartaDTO cartaCima = blackboard.getCartaCima();
        List<CartaDTO> mano = blackboard.getManoJugador(indiceJugador);
        TipoEventoRuletaDTO eventoRuleta = "GIRO_PENDIENTE".equals(blackboard.getEstadoPartida())
                ? blackboard.getEventoRuleta()
                : null;
        return new EstadoPartidaDTO(
                blackboard.getIndiceJugadorActual(),
                blackboard.getEstadoPartida(),
                cartaCima,
                jugadores,
                mano,
                blackboard.getIndiceJugadorActual() == indiceJugador,
                eventoRuleta,
                blackboard.isUltimaJugadaValida());
    }

    @Override
    public void update(IBlackboard blackboard) {
        this.blackboard = blackboard;
        broadcastEstado();
    }
}
