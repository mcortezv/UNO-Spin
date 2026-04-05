package servidor;
import Interfaces.IControlServidor;
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
public class ControlServidor implements IObservadorDominio, IControlServidor {
    private IDominio dominio;
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
    public void onEstadoCambiado(IDominio dominio) {
        for (SocketCliente cliente: clientes) {
            EstadoPartidaDTO dto = dominio.obtenerEstadoPartidaJugador(cliente.getIndiceJugador());
            String json = serializer.serialize(dto);
            dispatcher.enviar(json, cliente.getPuerto(), cliente.getIp());
        }
    }
}