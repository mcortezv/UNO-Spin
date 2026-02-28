package mvc;
import mvc.interfaces.IModeloControlador;
import mvc.interfaces.IModeloLectura;
import mvc.interfaces.ISuscriptor;
import dominio.Jugador;
import dto.CartaDTO;
import dto.JugadorRivalDTO;
import java.util.ArrayList;
import java.util.List;

public class ModeloMock implements IModeloControlador, IModeloLectura {
    private final List<ISuscriptor> suscriptores = new ArrayList<>();


    @Override
    public List<CartaDTO> getDescarte() {
        return List.of();
    }

    @Override
    public List<CartaDTO> getManoJugador() {
        return List.of();
    }

    @Override
    public Jugador getJugadorEnTurno() {
        return null;
    }

    @Override
    public List<CartaDTO> getManoJugadorActual() {
        return List.of(
                new CartaDTO("ROJO",     "5"),
                new CartaDTO("AMARILLO", "0"),
                new CartaDTO("AZUL",     "mas_dos"),
                new CartaDTO("VERDE",    "bloqueo"),
                new CartaDTO("ROJO",     "mas_dos"),
                new CartaDTO("VERDE",    "4"),
                new CartaDTO("AMARILLO",    "0"),
                new CartaDTO("VERDE",    "3"),
                new CartaDTO("AZUL",     "7"),
                new CartaDTO("ROJO",    "9")
        );
    }

    @Override
    public CartaDTO getCartaCima() {
        return new CartaDTO("AZUL", "1");
    }

    @Override
    public String getNombreTurnoActual() {
        return "Jugador 3";
    }

    @Override
    public List<JugadorRivalDTO> getJugadoresRivales() {
        return List.of(
                new JugadorRivalDTO("JUGADOR 4", 4, 7, false),
                new JugadorRivalDTO("JUGADOR 2", 2, 6, false),
                new JugadorRivalDTO("JUGADOR 1", 1, 5, false)
        );
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
}