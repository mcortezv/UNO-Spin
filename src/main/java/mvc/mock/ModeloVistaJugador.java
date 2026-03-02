package mvc.mock;
import dto.CartaDTO;
import dto.EventoRuletaDTO;
import dto.JugadorDTO;
import mvc.Modelo;
import mvc.interfaces.IModeloControlador;
import mvc.interfaces.IModeloLectura;
import mvc.interfaces.ISuscriptor;

import java.util.ArrayList;
import java.util.List;
import dominio.enums.TipoEventoRuleta;

public class ModeloVistaJugador implements IModeloLectura, IModeloControlador, ISuscriptor {
    private final List<ISuscriptor> suscriptores = new ArrayList<>();
    private IModeloLectura modeloLectura;
    private IModeloControlador modeloControlador;
    private int vistaJugador;

    public ModeloVistaJugador(int vistaJugador, Modelo modelo) {
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
        if (isTurnoActivo()) {
            modeloControlador.pedirCarta();
        }
        notifyObservers();
    }

    @Override
    public EventoRuletaDTO girarRuleta() {
        EventoRuletaDTO eventoDTO= modeloControlador.girarRuleta();
        notifyObservers();
        return eventoDTO;
    }

    @Override
    public void gritarUno() {
        modeloControlador.gritarUno();
    }


    @Override
    public List<CartaDTO> getDescarte() {
        return modeloLectura.getDescarte();
    }

    @Override
    public List<CartaDTO> getManoJugador() {
        return modeloLectura.getManoJugadorEspecifico(vistaJugador);
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
        return ((Modelo) modeloLectura).isTurnoActivoEspecifico(vistaJugador);
    }

    @Override
    public boolean isSpinActivo() {
        return modeloLectura.isSpinActivo();
    }

    @Override
    public List<CartaDTO> getManoJugadorEspecifico(int indiceJugador) {
        return List.of();
    }

    @Override
    public boolean isTurnoActivoEspecifico(int indiceJugador) {
        return false;
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

    @Override
    public TipoEventoRuleta getEventoRuletaActual() {
        return modeloLectura.getEventoRuletaActual();
    }

    @Override
    public void limpiarEventoRuleta() {
        modeloControlador.limpiarEventoRuleta();
    }
}
