package factory;
import dominio.entidades.Jugador;
import dominio.entidades.Partida;
import dominio.IDominio;
import dominio.entidades.enums.EstadoPartida;
import interfaces.IBlackboard;
import interfaces.IFactoryBlackboard;
import interfaces.ISerializer;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Factory blackboard.
 */
public class FactoryBlackboard implements IFactoryBlackboard {

    @Override
    public IBlackboard crearBlackboard(ISerializer serializer) {

        EstadoPartida estadoPartida = EstadoPartida.NO_INICIADA;
        int indiceJugadorActual = 0;
        List<Jugador> jugadores = new ArrayList<>();
        boolean sentidoHorario = true;

        IDominio dominio = new Partida(estadoPartida, indiceJugadorActual, jugadores, sentidoHorario);
        return new Blackboard(dominio, serializer);
    }
}
