package mvc;

import dominio.entidades.enums.EstadoPartida;
import dominio.entidades.enums.TipoEventoRuleta;
import dominio.interfaces.IDominio;
import dto.CartaDTO;
import dto.JugadorDTO;
import interfaces.IModeloControlador;
import interfaces.IModeloLectura;
import interfaces.ISuscriptor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Modelo implements IModeloControlador, IModeloLectura {

    private final IDominio dominio;
    private final List<ISuscriptor> suscriptores = new ArrayList<>();

    private boolean botonUnoPresionado = false;
    private TipoEventoRuleta eventoRuletaActual;
    private int pasoEventoActual = 0;
    private final Set<Integer> jugadoresQueReconocieron = new HashSet<>();
    private final int totalJugadores;

    private boolean ultimaJugadaValida = true;

    public Modelo(IDominio dominio) {
        this.dominio = dominio;
        this.totalJugadores = dominio.getJugadores().size();
    }

    @Override
    public void jugarCarta(CartaDTO cartaDTO) {
        int indiceActual = dominio.getIndiceJugadorActual();

        boolean exito = dominio.aplicarJugada(cartaDTO);
        ultimaJugadaValida = exito;

        if (exito) {
            notifyObservers();
            if (dominio.getCantidadCartasJugador(indiceActual) == 1) {
                if (botonUnoPresionado) {
                    System.out.println("UNO gritado correctamente antes de tirar.");
                    botonUnoPresionado = false;
                } else {
                    System.out.println("3 segundos para gritar UNO...");
                    javax.swing.Timer timer = new javax.swing.Timer(3000, e -> {
                        if (!botonUnoPresionado) {
                            System.out.println("Castigo UNO aplicado.");
                            dominio.aplicarCastigoUno(indiceActual);
                            notifyObservers();
                        } else {
                            System.out.println("UNO gritado en el último momento.");
                        }
                        botonUnoPresionado = false;
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
            } else {
                botonUnoPresionado = false;
            }
        } else {
            notifyObservers();
        }
    }

    @Override
    public void pedirCarta() {
        dominio.robarCartaJugadorActual();
        notifyObservers();
    }

    @Override
    public void girarRuleta() {
        try {
            eventoRuletaActual = dominio.procesarGiroRuleta();
            pasoEventoActual = 1;
            jugadoresQueReconocieron.clear();
            notifyObservers();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void gritarUno() {
        this.botonUnoPresionado = true;
        dominio.gritarUno();
        System.out.println("Botón UNO presionado");
    }

    @Override
    public void limpiarEventoRuleta() {
        eventoRuletaActual = null;
        pasoEventoActual = 0;
        jugadoresQueReconocieron.clear();
    }

    @Override
    public void reconocerEvento(int indiceJugador) {
        jugadoresQueReconocieron.add(indiceJugador);
        if (jugadoresQueReconocieron.size() == totalJugadores) {
            limpiarEventoRuleta();
            notifyObservers();
        }
    }

    @Override
    public void avanzarPasoEvento() {
        pasoEventoActual++;
        jugadoresQueReconocieron.clear();
        notifyObservers();
    }

    @Override
    public void aplicarSeleccionColor(String color) {
        dominio.aplicarSeleccionColor(color);
        notifyObservers();
    }

    @Override
    public void aplicarEventoRuleta(TipoEventoRuleta evento, Object resultado) {
        eventoRuletaActual = null;
        dominio.aplicarEfectoRuleta(evento, resultado);
        dominio.avanzarTurno();
        notifyObservers();
    }

    @Override
    public boolean isUltimaJugadaValida() {
        return ultimaJugadaValida;
    }

    @Override
    public List<CartaDTO> getDescarte() {
        return dominio.getCartasDescarte();
    }

    @Override
    public List<CartaDTO> getManoJugador() {
        return dominio.getManoJugador(dominio.getIndiceJugadorActual());
    }

    @Override
    public CartaDTO getCartaCima() {
        return dominio.getCartaCima();
    }

    @Override
    public String getNombreTurnoActual() {
        return dominio.getJugadores().get(dominio.getIndiceJugadorActual()).getNombre();
    }

    @Override
    public List<JugadorDTO> getJugadoresRivales() {
        return getTodosLosJugadores();
    }

    @Override
    public List<JugadorDTO> getTodosLosJugadores() {
        return dominio.getJugadores();
    }

    @Override
    public boolean isTurnoActivo() {
        return dominio.getEstadoPartida() == EstadoPartida.EN_PROCESO;
    }

    @Override
    public boolean isSpinActivo() {
        return dominio.getEstadoPartida() == EstadoPartida.GIRO_PENDIENTE
                && eventoRuletaActual == null;
    }

    @Override
    public boolean isSeleccionColorPendiente() {
        return dominio.getEstadoPartida() == EstadoPartida.SELECCION_COLOR_PENDIENTE;
    }

    @Override
    public TipoEventoRuleta getEventoRuletaActual() {
        return eventoRuletaActual;
    }

    @Override
    public int getPasoEventoActual() {
        return pasoEventoActual;
    }

    @Override
    public List<CartaDTO> getManoJugadorEspecifico(int indiceJugador) {
        return dominio.getManoJugador(indiceJugador);
    }

    @Override
    public boolean isTurnoActivoEspecifico(int indiceJugador) {
        return dominio.getIndiceJugadorActual() == indiceJugador;
    }

    public void subscribe(ISuscriptor suscriptor) {
        suscriptores.add(suscriptor);
    }

    public void unsubscribe(ISuscriptor suscriptor) {
        suscriptores.remove(suscriptor);
    }

    private void notifyObservers() {
        for (ISuscriptor s : suscriptores) {
            s.update(this);
        }
    }
}