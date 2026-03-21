package mock;

import dominio.Carta;
import dominio.Jugador;
import dominio.enums.TipoCarta;
import dominio.enums.TipoEventoRuleta;
import dominio.mappers.CartaMapper;
import dominio.mappers.JugadorMapper;
import dto.JugadorDTO;
import interfaces.IModeloControlador;
import interfaces.IModeloLectura;
import interfaces.ISuscriptor;
import dto.CartaDTO;

import java.util.ArrayList;
import java.util.List;

public class ModeloMock implements IModeloControlador, IModeloLectura {

    private final List<ISuscriptor> suscriptores = new ArrayList<>();
    private final PartidaMock partidaMock;
    private boolean ultimaJugadaValida = true;

    public ModeloMock() {
        this.partidaMock = new PartidaMock();
    }


    @Override
    public boolean isUltimaJugadaValida() {
        return ultimaJugadaValida;
    }

    @Override
    public List<CartaDTO> getDescarte() {
        return CartaMapper.toDTO(partidaMock.getTablero().getDescarte().getCartas());
    }

    @Override
    public List<CartaDTO> getManoJugador() {
        return new ArrayList<>();
    }

    @Override
    public List<CartaDTO> getManoJugadorEspecifico(int indiceJugador) {
        return CartaMapper.toDTO(partidaMock.getJugadores().get(indiceJugador).getMano().getCartas());
    }

    @Override
    public boolean isTurnoActivoEspecifico(int indiceJugador) {
        return partidaMock.getIndiceJugadorActual() == indiceJugador;
    }

    @Override
    public TipoEventoRuleta getEventoRuletaActual() {
        return null;
    }

    @Override
    public int getPasoEventoActual() {
        return 0;
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
        return getTodosLosJugadores();
    }

    @Override
    public List<JugadorDTO> getTodosLosJugadores() {
        List<JugadorDTO> todos = new ArrayList<>();
        for (Jugador jugador : partidaMock.getJugadores()) {
            todos.add(JugadorMapper.toDTO(jugador));
        }
        return todos;
    }

    @Override
    public boolean isTurnoActivo() {
        return false;
    }

    @Override
    public boolean isSpinActivo() {
        return partidaMock.getTablero().getDescarte().getUltimaCarta()
                .getTipoCarta().equals(TipoCarta.NUMERO_SPIN);
    }


    @Override
    public void jugarCarta(CartaDTO carta) {
        Carta c = CartaMapper.toEntity(carta);
        if (partidaMock.getTablero().getDescarte().validarCartaEntrante(c)) {
            Jugador jugadorActual = partidaMock.getJugadores().get(partidaMock.getIndiceJugadorActual());
            List<Carta> cartasMano = jugadorActual.getMano().getCartas();
            for (int i = 0; i < cartasMano.size(); i++) {
                Carta cartaEnMano = cartasMano.get(i);
                if (cartaEnMano.getColor().equals(c.getColor())
                        && cartaEnMano.getValor() == c.getValor()) {
                    cartasMano.remove(i);
                    break;
                }
            }
            partidaMock.getTablero().getDescarte().getCartas().add(c);
            partidaMock.avanzarTurno();
            ultimaJugadaValida = true;
            notifyObservers();
        } else {
            ultimaJugadaValida = false;
            notifyObservers();
        }
    }

    @Override
    public void pedirCarta() {
    }

    @Override
    public void girarRuleta() {
    }

    @Override
    public void gritarUno() {
    }

    @Override
    public void limpiarEventoRuleta() {
    }

    @Override
    public void reconocerEvento(int indiceJugador) {
    }

    @Override
    public void avanzarPasoEvento() {
    }

    @Override
    public void aplicarEventoRuleta(TipoEventoRuleta evento, Object resultado) {
    }

    @Override
    public boolean isSeleccionColorPendiente() {
        return false;
    }

    @Override
    public void aplicarSeleccionColor(String color) {
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