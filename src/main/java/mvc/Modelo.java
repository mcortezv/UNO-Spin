package mvc;
import dominio.interfaces.IDominio;
import dto.JugadorDTO;
import mappers.JugadorMapper;
import mvc.interfaces.IModeloControlador;
import mvc.interfaces.IModeloLectura;
import mvc.interfaces.ISuscriptor;
import dominio.Carta;
import dominio.Partida;
import dominio.Tablero;
import dto.CartaDTO;
import mvc.mock.PartidaMock;
import mvc.mock.TableroMock;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Modelo.
 */
public class Modelo implements IModeloControlador, IModeloLectura {
    private IDominio iDominio;
    private final Partida partida;
    private final Tablero tablero;
    private final List<ISuscriptor> suscriptores = new ArrayList<>();

    /**
     * Instantiates a new Modelo.
     */
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
        return null;
    }

    @Override
    public JugadorDTO getJugadorEnTurno() {
        return JugadorMapper.toDTO(partida.getJugadores().get(partida.getIndiceJugadorActual()));
    }

    @Override
    public List<CartaDTO> getManoJugadorActual() {
        return List.of();
    }

    @Override
    public CartaDTO getCartaCima() {
        return null;
    }

    @Override
    public String getNombreTurnoActual() {
        return "";
    }

    @Override
    public List<JugadorDTO> getJugadoresRivales() {
        return List.of();
    }

    @Override
    public boolean isSpinActivo() {
        return false;
    }

    /**
     * Subscribe.
     *
     * @param suscriptor the suscriptor
     */
    public void subscribe(ISuscriptor suscriptor) {
        this.suscriptores.add(suscriptor);
    }

    /**
     * Unsubscribe.
     *
     * @param suscriptor the suscriptor
     */
    public void unsubscribe(ISuscriptor suscriptor) {
        this.suscriptores.remove(suscriptor);
    }

    private void notifyObservers() {
        for (ISuscriptor suscriptor : suscriptores) {
            suscriptor.update(this);
        }
    }

    @Override
    public PartidaMock getPartidaMock() {
        return null;
    }
}
