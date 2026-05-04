package factory;
import interfaces.*;
import dto.CartaDTO;
import dto.EstadoPartidaDTO;
import dto.JugadorDTO;
import dto.TipoEventoRuletaDTO;
import java.util.List;

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

    private void broadcastEstado() {
        List<JugadorDTO> jugadores = blackboard.getJugadores();
        System.out.println("[CS] broadcastEstado jugadores=" + jugadores.size());
        for (int i = 0; i < jugadores.size(); i++) {
            JugadorDTO jugador = jugadores.get(i);
            String ip = blackboard.getIpJugador(jugador.getNombre());
            int puerto = blackboard.getPuertoJugador(jugador.getNombre());
            if (ip == null) {
                System.out.println("[CS] SIN SOCKET para jugador=" + jugador.getNombre());
                continue;
            }

            System.out.println("[CS] Enviando estado[" + i + "] a " + jugador.getNombre() + " -> " + ip + ":" + puerto);
            EstadoPartidaDTO dto = buildEstadoPartida(i);
            String json = serializer.serialize(dto);
            dispatcher.enviar(json, puerto, ip);
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
