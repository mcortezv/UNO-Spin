package factory;
import dominio.entidades.Jugador;
import dominio.entidades.Partida;
import dominio.IDominio;
import dominio.entidades.enums.EstadoPartida;
import interfaces.IBlackboard;
import interfaces.IFactoryBlackboard;
import interfaces.IReceptor;
import interfaces.ISerializer;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Factory blackboard.
 */
public class FactoryBlackboard implements IFactoryBlackboard {
    private Blackboard blackboard;

    private Blackboard crearBlackboard(ISerializer serializer){
        if (blackboard == null){
            EstadoPartida estadoPartida = EstadoPartida.NO_INICIADA;
            int indiceJugadorActual = 0;
            List<Jugador> jugadores = new ArrayList<>();
            boolean sentidoHorario = true;

            IDominio dominio = new Partida(estadoPartida, indiceJugadorActual, jugadores, sentidoHorario);
            this.blackboard = new Blackboard(dominio, serializer);
        }
        return blackboard;
    }

    @Override
    public IBlackboard crearBlackboardObservable(ISerializer serializer) {
        return crearBlackboard(serializer);
    }

    @Override
    public IReceptor crearReceptorBlackboard(ISerializer serializer) {
        return crearBlackboard(serializer);
    }
}
