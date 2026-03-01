package mvc.mock;
import dominio.Carta;
import dominio.Jugador;
import dominio.enums.TipoCarta;
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
        this.partidaMock = new PartidaMock();
    }

    @Override
    public List<CartaDTO> getDescarte() {
        return CartaMapper.toDTO(partidaMock.getTablero().getDescarte().getCartas());
    }

    @Override
    public List<CartaDTO> getManoJugador() {
        return null;
    }

    public List<CartaDTO> getManoJugadorEspecifico(int indiceJugador) {
        return CartaMapper.toDTO(partidaMock.getJugadores().get(indiceJugador).getMano().getCartas());
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
        List<JugadorDTO> todosLosJugadores = new ArrayList<>();
        for (Jugador jugador : partidaMock.getJugadores()) {
            todosLosJugadores.add(JugadorMapper.toDTO(jugador));
        }
        return todosLosJugadores;
    }

    @Override
    public boolean jugarCarta(CartaDTO carta) {
        Carta c = CartaMapper.toEntity(carta);
        if (partidaMock.getTablero().getDescarte().validarCartaEntrante(c)){
            Jugador jugadorActual = partidaMock.getJugadores().get(partidaMock.getIndiceJugadorActual());
            List<Carta> cartasMano = jugadorActual.getMano().getCartas();
            for (int i = 0; i < cartasMano.size(); i++) {
                Carta cartaEnMano = cartasMano.get(i);
                if (cartaEnMano.getColor().equals(c.getColor()) && cartaEnMano.getValor() == c.getValor()) {
                    cartasMano.remove(i);
                    break;
                }
            }
            partidaMock.getTablero().getDescarte().getCartas().add(c);
            partidaMock.avanzarTurno();
            notifyObservers();
            return true;
        }
        return false;
    }

    @Override
    public void pedirCarta() {

    }

    public void pedirCartaMock(int indiceJugador) {
        Carta carta = partidaMock.getTablero().getMazo().getCartas().getLast();
        partidaMock.getTablero().getMazo().getCartas().removeLast();
        partidaMock.getJugadores().get(indiceJugador).getMano().getCartas().add(carta);
    }

    @Override
    public void girarRuleta() {

    }

    @Override
    public boolean isTurnoActivo() {
        return false;
    }

    public boolean isTurnoActivoMock(int indiceJugador) {
        return partidaMock.getIndiceJugadorActual() == indiceJugador;
    }

    @Override
    public boolean isSpinActivo() {
        return partidaMock.getTablero().getDescarte().getUltimaCarta().getTipoCarta().equals(TipoCarta.NUMERO_SPIN);
    }

    public void subscribe(ISuscriptor suscriptor) {
        this.suscriptores.add(suscriptor);
    }

    private void notifyObservers() {
        for (ISuscriptor suscriptor : suscriptores) {
            suscriptor.update(this);
        }
    }
}