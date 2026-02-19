package MVC;
import MVC.interfaces.IModeloControlador;
import MVC.interfaces.IModeloLectura;
import MVC.interfaces.ISuscriptor;
import domain.Carta;
import domain.Jugador;
import domain.Partida;
import domain.Tablero;
import dto.CartaDTO;
import java.util.ArrayList;
import java.util.List;

public class Modelo implements IModeloControlador, IModeloLectura {
    private final Partida partida;
    private final Tablero tablero;
    private final List<ISuscriptor> suscriptores = new ArrayList<>();

    public Modelo() {
        this.partida = new PartidaMock();
        this.tablero = new TableroMock();
    }

    @Override
    public void cartaSeleccionada(CartaDTO cartaDTO) {

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
