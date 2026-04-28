package servidor;
import Interfaces.IControlServidor;
import dto.CartaDTO;
import dto.EstadoPartidaDTO;
import dto.EventoRuletaDTO;
import dto.JugadorDTO;
import interfaces.IBlackboard;
import interfaces.IDispatcher;
import interfaces.IReceptor;
import interfaces.ISerializer;
import java.util.ArrayList;
import java.util.List;

public class ControlServidor implements IReceptor, IControlServidor {
    private static ControlServidor instance;
    private final List<IDispatcher> suscriptores = new ArrayList<>();
    private final IBlackboard blackboard;
    private final IDispatcher dispatcher;
    private final ISerializer serializer;
    private final List<SocketCliente> clientes;

    ControlServidor(IBlackboard blackboard, IDispatcher dispatcher, ISerializer serializer) {
        this.blackboard = blackboard;
        this.dispatcher = dispatcher;
        this.serializer = serializer;
        this.clientes = new ArrayList<>();
        instance = this;
    }

    public static void suscribir(IDispatcher receptor) {
        instance.suscriptores.add(receptor);
    }

    public static void iniciar() {

    }

    public void registrarCliente(int indiceJugador, String ip, int puerto) {
        clientes.removeIf(c -> c.getIndiceJugador() == indiceJugador);
        clientes.add(new SocketCliente(indiceJugador, ip, puerto));
    }

    public void desconectarCliente(int indiceJugador) {
        clientes.removeIf(c -> c.getIndiceJugador() == indiceJugador);
    }

    @Override
    public void recibirMensaje(String json) {
        broadcastEstado();
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
        EventoRuletaDTO eventoRuleta = null;
        if (blackboard.getEstadoPartida().equals("GIRO_PENDIENTE")) {
            eventoRuleta = blackboard.getEventoRuleta();
        }
        return new EstadoPartidaDTO(
                blackboard.getIndiceJugadorActual(),
                blackboard.getEstadoPartida(),
                cartaCima,
                jugadores,
                mano,
                (blackboard.getIndiceJugadorActual() == indiceJugador),
                eventoRuleta,
                blackboard.isUltimaJugadaValida());
    }
}
