package mvc.mock;

import dto.CartaDTO;
import dto.JugadorDTO;
import mvc.Modelo;
import mvc.interfaces.IModeloControlador;
import mvc.interfaces.IModeloLectura;
import mvc.interfaces.ISuscriptor;
import dominio.enums.TipoEventoRuleta;

import java.util.ArrayList;
import java.util.List;

public class ModeloVistaJugador implements IModeloLectura, IModeloControlador, ISuscriptor {

    private final List<ISuscriptor> suscriptores = new ArrayList<>();
    private final IModeloLectura modeloLectura;
    private final IModeloControlador modeloControlador;
    private final int vistaJugador;

    private boolean eventoYaMostrado = false;
    private TipoEventoRuleta ultimoEventoVisto = null;
    private int ultimoPasoVisto = 0;

    public ModeloVistaJugador(int vistaJugador, Modelo modelo) {
        this.vistaJugador = vistaJugador;
        this.modeloLectura = modelo;
        this.modeloControlador = modelo;
        modelo.subscribe(this);
    }

    @Override
    public void jugarCarta(CartaDTO carta) {
        modeloControlador.jugarCarta(carta);
    }

    @Override
    public void pedirCarta() {
        if (isTurnoActivo()) {
            modeloControlador.pedirCarta();
        }
    }

    @Override
    public void girarRuleta() {
        modeloControlador.girarRuleta();
    }

    @Override
    public void gritarUno() {
        modeloControlador.gritarUno();
    }

    @Override
    public void aplicarSeleccionColor(String color) {
        modeloControlador.aplicarSeleccionColor(color);
    }

    @Override
    public void aplicarEventoRuleta(TipoEventoRuleta evento, Object resultado) {
        eventoYaMostrado = true;
        modeloControlador.aplicarEventoRuleta(evento, resultado);
    }

    @Override
    public void limpiarEventoRuleta() {
        eventoYaMostrado = true;
        modeloControlador.reconocerEvento(vistaJugador);
    }

    @Override
    public void reconocerEvento(int indiceJugador) {
        modeloControlador.reconocerEvento(indiceJugador);
    }

    @Override
    public void avanzarPasoEvento() {
        modeloControlador.avanzarPasoEvento();
    }


    @Override
    public boolean isUltimaJugadaValida() {
        return modeloLectura.isUltimaJugadaValida();
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
        List<JugadorDTO> todos = modeloLectura.getTodosLosJugadores();
        List<JugadorDTO> rivales = new ArrayList<>();
        for (int i = 0; i < todos.size(); i++) {
            if (i != vistaJugador) rivales.add(todos.get(i));
        }
        return rivales;
    }

    @Override
    public List<JugadorDTO> getTodosLosJugadores() {
        return modeloLectura.getTodosLosJugadores();
    }

    @Override
    public boolean isTurnoActivo() {
        return modeloLectura.isTurnoActivoEspecifico(vistaJugador);
    }

    @Override
    public boolean isSpinActivo() {
        return modeloLectura.isSpinActivo()
                && modeloLectura.isTurnoActivoEspecifico(vistaJugador);
    }

    @Override
    public boolean isSeleccionColorPendiente() {
        return modeloLectura.isSeleccionColorPendiente()
                && modeloLectura.isTurnoActivoEspecifico(vistaJugador);
    }

    @Override
    public List<CartaDTO> getManoJugadorEspecifico(int indiceJugador) {
        return modeloLectura.getManoJugadorEspecifico(indiceJugador);
    }

    @Override
    public boolean isTurnoActivoEspecifico(int indiceJugador) {
        return modeloLectura.isTurnoActivoEspecifico(indiceJugador);
    }

    @Override
    public TipoEventoRuleta getEventoRuletaActual() {
        if (eventoYaMostrado) return null;
        return modeloLectura.getEventoRuletaActual();
    }

    @Override
    public int getPasoEventoActual() {
        return modeloLectura.getPasoEventoActual();
    }


    public void subscribe(ISuscriptor suscriptor) {
        suscriptores.add(suscriptor);
    }

    private void notifyObservers() {
        for (ISuscriptor s : suscriptores) s.update(this);
    }

    @Override
    public void update(IModeloLectura modelo) {
        TipoEventoRuleta eventoActual = modeloLectura.getEventoRuletaActual();
        int pasoActual = modeloLectura.getPasoEventoActual();

        if (eventoActual != ultimoEventoVisto || pasoActual != ultimoPasoVisto) {
            eventoYaMostrado = false;
            ultimoEventoVisto = eventoActual;
            ultimoPasoVisto = pasoActual;
        }

        notifyObservers();
    }
}