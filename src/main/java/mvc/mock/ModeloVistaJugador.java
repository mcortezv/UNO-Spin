package mvc.mock;
import dto.CartaDTO;
import dto.JugadorDTO;
import mvc.interfaces.IModeloControlador;
import mvc.interfaces.IModeloLectura;
import mvc.interfaces.ISuscriptor;

import java.util.ArrayList;
import java.util.List;

public class ModeloVistaJugador implements IModeloLectura, IModeloControlador, ISuscriptor {
    private final List<ISuscriptor> suscriptores = new ArrayList<>();
    private IModeloLectura modeloLectura;
    private IModeloControlador modeloControlador;
    private int vistaJugador;

    public ModeloVistaJugador(int vistaJugador, ModeloMock modelo) {
        this.vistaJugador = vistaJugador;
        this.modeloLectura = modelo;
        this.modeloControlador = modelo;
        modelo.subscribe(this);
    }


    @Override
    public boolean jugarCarta(CartaDTO carta) {
        if (modeloControlador.jugarCarta(carta)){
            notifyObservers();
            return true;
        }
        return false;
    }

    @Override
    public void pedirCarta() {
        ((ModeloMock) modeloControlador).pedirCartaMock(vistaJugador);
        notifyObservers();
    }

    @Override
    public void girarRuleta() {
        modeloControlador.girarRuleta();
    }


    @Override
    public List<CartaDTO> getDescarte() {
        return modeloLectura.getDescarte();
    }

    @Override
    public List<CartaDTO> getManoJugador() {
        return ((ModeloMock) modeloLectura).getManoJugadorEspecifico(vistaJugador);
    }

    @Override
    public CartaDTO getCartaCima() {
        return modeloLectura.getCartaCima();
    }

    @Override
    public String getNombreTurnoActual() {
        return modeloLectura.getNombreTurnoActual();
    }

    @Override
    public List<JugadorDTO> getJugadoresRivales() {
        List<JugadorDTO> listaJugadoresRivales = modeloLectura.getJugadoresRivales();
        if (vistaJugador == 0) {
            return List.of(listaJugadoresRivales.get(1));
        } else {
            return List.of(listaJugadoresRivales.get(0));
        }
    }

    @Override
    public boolean isTurnoActivo() {
        return ((ModeloMock) modeloLectura).isTurnoActivoMock(vistaJugador);
    }

    @Override
    public boolean isSpinActivo() {
        return modeloLectura.isSpinActivo();
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
    public void update(IModeloLectura modelo) {
        notifyObservers();
    }

}
