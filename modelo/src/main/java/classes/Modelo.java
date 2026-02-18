package classes;

import domain.Carta;
import domain.Jugador;
import domain.Partida;
import domain.Tablero;
import dto.CartaDTO;
import interfaces.IModeloControlador;
import interfaces.IModeloLectura;
import interfaces.ISuscriptor;
import mocks.PartidaMock;
import mocks.TableroMock;

import java.util.ArrayList;
import java.util.List;

public class Modelo implements IModeloControlador, IModeloLectura {

    private Partida partida;
    private Tablero tablero;
    private List<ISuscriptor> suscriptores = new ArrayList<>();

    public Modelo() {
        this.partida = new PartidaMock();
        this.tablero = new TableroMock();
    }

    @Override
    public boolean jugarCarta(CartaDTO carta) {
        carta.getColor();
        notifyObservers();
        return true;
    }

    @Override
    public List<CartaDTO> getDescarte() {
        List<Carta> cartas = tablero.getDescarte().getCartas();
        return tablero.getDescarte().obtenerCartasDTO();
    }

    @Override
    public List<CartaDTO> getManoJugador() {
        Jugador jTurno = getJugadorEnTurno();
        return jTurno.getMano().cartaDTOS();
    }

    @Override
    public Jugador getJugadorEnTurno() {
        return partida.getJugadores().get(partida.getIndiceJugadorActual());
    }

    public void subscribe(ISuscriptor suscriptor) {
        this.suscriptores.add(suscriptor);
    }

    public void unsubscribe(ISuscriptor suscriptor) {
        this.suscriptores.remove(suscriptor);
    }

    private void notifyObservers() {
        for (ISuscriptor suscriptor : suscriptores) {
            suscriptor.update(this);
        }
    }
}
