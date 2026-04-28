package blackboard;
import blackboard.dominio.entidades.Jugador;
import blackboard.dominio.entidades.Partida;
import blackboard.dominio.IDominio;
import blackboard.dominio.entidades.enums.EstadoPartida;
import interfaces.IFactoryBlackboard;
import interfaces.ISerializer;
import java.util.ArrayList;
import java.util.List;

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
