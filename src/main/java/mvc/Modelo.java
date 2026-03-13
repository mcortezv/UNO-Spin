package mvc;

import dominio.enums.EstadoPartida;
import dominio.interfaces.IDominio;
import dto.JugadorDTO;
import mappers.CartaMapper;
import mvc.interfaces.IModeloControlador;
import mvc.interfaces.IModeloLectura;
import mvc.interfaces.ISuscriptor;
import dominio.Carta;
import dto.CartaDTO;
import dto.EventoRuletaDTO;
import dominio.enums.TipoEventoRuleta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The type Modelo.
 */
public class Modelo implements IModeloControlador, IModeloLectura {
    private IDominio dominio;
    private final List<ISuscriptor> suscriptores = new ArrayList<>();
    private boolean botonUnoPresionado = false;
    private TipoEventoRuleta eventoRuletaActual;
    int pasoEventoActual = 0;
    Set<Integer> jugadoresQueReconocieron = new HashSet<>();
    int totalJugadores;

    public Modelo(IDominio dominio) {
        this.dominio = dominio;
        this.totalJugadores = dominio.getJugadores().size();
    }

    @Override
    public boolean jugarCarta(CartaDTO cartaDTO) {
        dominio.Jugador jugadorQueTira = dominio.getJugadores().get(dominio.getIndiceJugadorActual());
        Carta carta = CartaMapper.toEntity(cartaDTO);
        boolean exito = dominio.aplicarJugada(carta);
        if (exito) {
            notifyObservers();
            if (jugadorQueTira.getMano().getCartas().size() == 1) {
                if (botonUnoPresionado) {
                    System.out.println(jugadorQueTira.getNombre() + " gritó UNO antes de tirar");
                    botonUnoPresionado = false;
                } else {
                    System.out.println(jugadorQueTira.getNombre() + " tiene 3 segundos para gritar UNO");
                    javax.swing.Timer timer = new javax.swing.Timer(3000, e -> {
                        if (!botonUnoPresionado) {
                            System.out.println("Castigo para " + jugadorQueTira.getNombre());
                            try {
                                Carta castigo = dominio.getTablero().getMazo().robarCarta();
                                jugadorQueTira.getMano().getCartas().add(castigo);
                                notifyObservers();
                            } catch (Exception ex) {
                                System.out.println("Mazo vacío");
                            }
                        } else {
                            System.out.println(jugadorQueTira.getNombre() + " gritó UNO en el último segundo");
                        }
                        botonUnoPresionado = false;
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
            } else {
                botonUnoPresionado = false;
            }
        }
        return exito;
    }

    @Override
    public void pedirCarta() {
        dominio.robarCartaJugadorActual();
        notifyObservers();
    }

    @Override
    public EventoRuletaDTO girarRuleta() {
        try {
            eventoRuletaActual = dominio.procesarGiroRuleta();
            pasoEventoActual = 1;
            jugadoresQueReconocieron.clear();
            notifyObservers();
            return null;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
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
    public void limpiarEventoRuleta() {
        this.eventoRuletaActual = null;
        pasoEventoActual = 0;
        jugadoresQueReconocieron.clear();
    }

    @Override
    public void reconocerEvento(int indiceJugador) {
        jugadoresQueReconocieron.add(indiceJugador);
        if(jugadoresQueReconocieron.size() == this.totalJugadores) {
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
    public void aplicarEventoRuleta(TipoEventoRuleta evento, Object resultado) {
        this.eventoRuletaActual = null;
        if (resultado != null || evento == TipoEventoRuleta.INTERCAMBIO_DE_MANOS || evento == TipoEventoRuleta.GUERRA || evento == TipoEventoRuleta.CASI_UNO) {
            dominio.aplicarEfectoRuleta(evento, resultado);
            dominio.avanzarTurno();
        }
        notifyObservers();
    }

    @Override
    public List<CartaDTO> getDescarte() {
        return CartaMapper.toDTO(dominio.getTablero().getDescarte().getCartas());
    }

    @Override
    public List<CartaDTO> getManoJugador() {
        return CartaMapper.toDTO(dominio.getJugadores().get(dominio.getIndiceJugadorActual()).getMano().getCartas());
    }

    @Override
    public CartaDTO getCartaCima() {
        return CartaMapper.toDTO(dominio.getTablero().getDescarte().getUltimaCarta());
    }

    @Override
    public String getNombreTurnoActual() {
        return dominio.getJugadores().get(dominio.getIndiceJugadorActual()).getNombre();
    }

    @Override
    public List<JugadorDTO> getJugadoresRivales() {
        List<JugadorDTO> todosLosJugadores = new ArrayList<>();
        for (dominio.Jugador jugador : dominio.getJugadores()) {
            todosLosJugadores.add(mappers.JugadorMapper.toDTO(jugador));
        }
        return todosLosJugadores;
    }

    @Override
    public boolean isTurnoActivo() {
        return dominio.getEstadoPartida() == EstadoPartida.EN_PROCESO;
    }

    @Override
    public boolean isSpinActivo() {
        return dominio.getEstadoPartida() == EstadoPartida.GIRO_PENDIENTE
                && this.eventoRuletaActual == null;
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

    @Override
    public List<CartaDTO> getManoJugadorEspecifico(int indiceJugador) {
        return CartaMapper.toDTO(dominio.getJugadores().get(indiceJugador).getMano().getCartas());
    }

    @Override
    public boolean isTurnoActivoEspecifico(int indiceJugador) {
        return dominio.getIndiceJugadorActual() == indiceJugador;
    }

    @Override
    public void gritarUno() {
        this.botonUnoPresionado = true;
        System.out.println("Botón UNO presionado");
    }
}