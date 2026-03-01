package mvc.mock;
import dominio.Jugador;
import dto.JugadorDTO;
import mappers.CartaMapper;
import mappers.JugadorMapper;
import mvc.interfaces.IModeloControlador;
import mvc.interfaces.IModeloLectura;
import mvc.interfaces.ISuscriptor;
import dto.CartaDTO;
import java.util.ArrayList;
import java.util.List;

public class ModeloMock implements IModeloControlador, IModeloLectura {
    private final List<ISuscriptor> suscriptores = new ArrayList<>();
    private PartidaMock partidaMock;

    public ModeloMock() {
        partidaMock = new PartidaMock();
    }

    @Override
    public List<CartaDTO> getDescarte() {
        return CartaMapper.toDTO(partidaMock.getTablero().getDescarte().getCartas());
    }

    @Override
    public List<CartaDTO> getManoJugador() {
        return CartaMapper.toDTO(partidaMock.getJugadores().get(0).getMano().getCartas());
    }

    @Override
    public JugadorDTO getJugadorEnTurno() {
        return JugadorMapper.toDTO(partidaMock.getJugadores().get(partidaMock.getIndiceJugadorActual()));
    }

    @Override
    public List<CartaDTO> getManoJugadorActual() {
        Jugador jugadorActual = partidaMock.getJugadores().get(partidaMock.getIndiceJugadorActual());
        return CartaMapper.toDTO(jugadorActual.getMano().getCartas());
    }

    @Override
    public CartaDTO getCartaCima() {
        return CartaMapper.toDTO(partidaMock.getTablero().getDescarte().getUltimaCarta());
    }

    @Override
    public String getNombreTurnoActual() {
        return partidaMock.getJugadores().get(partidaMock.getIndiceJugadorActual()).getNombre();
    }

    @Override
    public List<JugadorDTO> getJugadoresRivales() {
        List<JugadorDTO> jugadoresRivales = new ArrayList<>();
        for (Jugador jugador : partidaMock.getJugadores()) {
            jugadoresRivales.add(JugadorMapper.toDTO(jugador));
        }
        return jugadoresRivales;
    }

    @Override
    public boolean isSpinActivo() {
        return false;
    }

    @Override
    public void cartaSeleccionada(CartaDTO cartaDTO) {

    }

    @Override
    public boolean jugarCarta(CartaDTO carta) {
        notifyObservers();
        return true;
    }

    public void subscribe(ISuscriptor suscriptor) {
        this.suscriptores.add(suscriptor);
    }

    private void notifyObservers() {
        for (ISuscriptor suscriptor : suscriptores) {
            suscriptor.update(this);
        }
    }

    @Override
    public PartidaMock getPartidaMock() {
        return partidaMock;
    }
}