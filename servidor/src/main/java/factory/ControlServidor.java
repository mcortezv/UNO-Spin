package factory;

import util.SocketCliente;
import Interfaces.IControlServidor;
import dominio.IDominio;
import dominio.entidades.enums.EstadoPartida;
import static dominio.entidades.enums.TipoAccion.GRITAR_UNO;
import static dominio.entidades.enums.TipoAccion.JUGAR_CARTA;
import static dominio.entidades.enums.TipoAccion.PEDIR_CARTA;
import dto.CartaDTO;
import dto.EstadoPartidaDTO;
import dto.JugadorDTO;
import dto.TipoAccionDTO;
import interfaces.IBlackboard;
import interfaces.IDispatcher;
import interfaces.IReceptor;
import interfaces.ISerializer;
import mappers.CartaMapper;
import mappers.JugadorMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ControlServidor implements IReceptor, IControlServidor {
    private final IBlackboard blackboard;
    private final IDispatcher dispatcher;
    private final ISerializer serializer;
    private final List<SocketCliente> clientes= new ArrayList<>();

    public ControlServidor(IBlackboard blackboard, IDispatcher dispatcher, ISerializer serializer) {
        this.blackboard = blackboard;
        this.dispatcher = dispatcher;
        this.serializer = serializer;
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
        if(Objects.equals(accion.getTipoAccion(), "UNIRSE_PARTIDA")){
            registrarCliente(clientes.size(), accion.getIp(), accion.getPuerto());
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
        List<JugadorDTO> jugadores = blackboard.getJugadores();
        CartaDTO cartaCima = blackboard.getCartaCima();
        List<CartaDTO> mano = blackboard.getManoJugador(indiceJugador);
        String eventoRuleta = "GIRO_PENDIENTE".equals(blackboard.getEstadoPartida())
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
}
