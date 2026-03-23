package servidor;
import dominio.interfaces.IDominio;
import dominio.interfaces.IObservadorDominio;
import dto.CartaDTO;
import dto.EstadoPartidaDTO;
import dto.JugadorDTO;
import interfaces.IDispatcher;
import interfaces.ISerializer;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Control servidor.
 */
public class ControlServidor implements IObservadorDominio {
    private final IDominio dominio;
    private final IDispatcher dispatcher;
    private final ISerializer serializer;
    private final List<SocketCliente> clientes;

    /**
     * Instantiates a new Control servidor.
     *
     * @param dominio    the dominio
     * @param dispatcher the dispatcher
     * @param serializer the serializer
     */
    public ControlServidor(IDominio dominio, IDispatcher dispatcher, ISerializer serializer) {
        this.dominio = dominio;
        this.dispatcher = dispatcher;
        this.serializer = serializer;
        this.clientes = new ArrayList<>();
    }

    /**
     * Registrar cliente.
     *
     * @param indiceJugador the indice jugador
     * @param ip            the ip
     * @param puerto        the puerto
     */
    public void registrarCliente(int indiceJugador, String ip, int puerto) {
        clientes.removeIf(c -> c.getIndiceJugador() == indiceJugador);
        clientes.add(new SocketCliente(indiceJugador, ip, puerto));
    }

    /**
     * Desconectar cliente.
     *
     * @param indiceJugador the indice jugador
     */
    public void desconectarCliente(int indiceJugador) {
        clientes.removeIf(c -> c.getIndiceJugador() == indiceJugador);
    }

    @Override
    public void onEstadoCambiado() {
        int indiceActual = dominio.getIndiceJugadorActual();
        String estado = dominio.getEstadoPartida().name();
        CartaDTO cartaCima = dominio.getCartaCima();
        List<JugadorDTO> jugadores = dominio.getJugadores();

        for (SocketCliente cliente : clientes) {
            int indice = cliente.getIndiceJugador();
            boolean esTurno = indiceActual == indice;
            EstadoPartidaDTO dto = new EstadoPartidaDTO(indiceActual, estado, cartaCima, jugadores, dominio.getManoJugador(indice), esTurno);
            dispatcher.enviar(serializer.serialize(dto), cliente.getPuerto(), cliente.getIp());
        }
    }
}