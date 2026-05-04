package factory;
import interfaces.*;
import dto.CartaDTO;
import dto.EstadoPartidaDTO;
import dto.JugadorDTO;
import dto.TipoEventoRuletaDTO;
import dto.TipoAccionDTO;
import util.SocketCliente;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Control servidor.
 */
public class ControlServidor implements IBlackboardObservador, IReceptor {
    private IBlackboard blackboard;
    private final IDispatcher dispatcher;
    private final ISerializer serializer;
    private final Map<String, SocketCliente> clientesPorNombre = new LinkedHashMap<>();

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
        System.out.println("[CS] broadcastEstado jugadores=" + jugadores.size() + " registrados=" + clientesPorNombre.keySet());
        for (int i = 0; i < jugadores.size(); i++) {
            JugadorDTO jugador = jugadores.get(i);
            SocketCliente cliente = clientesPorNombre.get(jugador.getNombre());
            if (cliente == null) {
                System.out.println("[CS] SIN SOCKET para jugador=" + jugador.getNombre());
                continue;
            }

            System.out.println("[CS] Enviando estado[" + i + "] a " + jugador.getNombre() + " -> " + cliente.getIp() + ":" + cliente.getPuerto());
            EstadoPartidaDTO dto = buildEstadoPartida(i);
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
    public void recibirMensaje(String json) {
        TipoAccionDTO accion = serializer.deserialize(json, TipoAccionDTO.class);
        if (!"UNIRSE_PARTIDA".equals(accion.getTipoAccion())) {
            return;
        }
        if (accion.getJugadorDTO() == null) {
            return;
        }

        String nombre = accion.getJugadorDTO().getNombre();
        if (nombre == null || nombre.isBlank() || clientesPorNombre.containsKey(nombre)) {
            return;
        }

        int indiceJugador = clientesPorNombre.size();
        clientesPorNombre.put(nombre, new SocketCliente(indiceJugador, accion.getIp(), accion.getPuerto()));
        System.out.println("[CS] Registrado: " + nombre + " -> " + accion.getIp() + ":" + accion.getPuerto());
    }

    @Override
    public void update(IBlackboard blackboard) {
        this.blackboard = blackboard;
        broadcastEstado();
    }
}
