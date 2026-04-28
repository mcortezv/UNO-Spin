package servidor;
import Interfaces.IControlServidor;
import dominio.entidades.enums.EstadoPartida;
import dominio.interfaces.IDominio;
import dto.CartaDTO;
import dto.EstadoPartidaDTO;
import dto.EventoRuletaDTO;
import dto.JugadorDTO;
import dto.TipoAccionDTO;
import interfaces.IDispatcher;
import interfaces.IReceptor;
import interfaces.ISerializer;
import mappers.CartaMapper;
import mappers.EventoRuletaMapper;
import mappers.JugadorMapper;
import java.util.ArrayList;
import java.util.List;

public class ControlServidor implements IReceptor, IControlServidor {
    private static ControlServidor instance;
    private final List<IDispatcher> suscriptores = new ArrayList<>();
    private final IDominio dominio;
    private final IDispatcher dispatcher;
    private final ISerializer serializer;
    private final List<SocketCliente> clientes;

    ControlServidor(IDominio dominio, IDispatcher dispatcher, ISerializer serializer) {
        this.dominio = dominio;
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
        TipoAccionDTO accion = serializer.deserialize(json, TipoAccionDTO.class);
        switch (accion.getTipoAccion()) {
            case JUGAR_CARTA -> dominio.aplicarJugada(CartaMapper.toEntity(accion.getCartaDTO()));
            case PEDIR_CARTA -> dominio.robarCartaJugadorActual();
            case GRITAR_UNO -> dominio.gritarUno();
        }
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
        List<JugadorDTO> jugadores = JugadorMapper.toDTO(dominio.getJugadores());
        CartaDTO cartaCima = CartaMapper.toDTO(dominio.getCartaCima());
        List<CartaDTO> mano = CartaMapper.toDTO(dominio.getManoJugador(indiceJugador));
        EventoRuletaDTO eventoRuleta = null;
        if (dominio.getEstadoPartida() == EstadoPartida.GIRO_PENDIENTE) {
            eventoRuleta = EventoRuletaMapper.toDTO(dominio.getEventoRuleta());
        }
        return new EstadoPartidaDTO(
                dominio.getIndiceJugadorActual(),
                dominio.getEstadoPartida().name(),
                cartaCima,
                jugadores,
                mano,
                (dominio.getIndiceJugadorActual() == indiceJugador),
                eventoRuleta,
                dominio.isUltimaJugadaValida());
    }
}
