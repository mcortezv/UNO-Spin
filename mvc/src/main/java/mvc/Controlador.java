package mvc;
import dto.CartaDTO;
import dto.ConfiguracionPartidaDTO;
import dto.JugadorDTO;
import interfaces.IModeloControlador;

public class Controlador {

    private final IModeloControlador modelo;

    public Controlador(IModeloControlador modelo) {
        this.modelo = modelo;
    }

    public void unirsePartida(JugadorDTO jugador, ConfiguracionPartidaDTO configuracion) {
        modelo.unirsePartida(jugador, configuracion);
    }

    public void confirmarInicio(JugadorDTO jugador) {
        modelo.confirmarInicio(jugador);
    }

    public void jugarCarta(CartaDTO carta) {
        modelo.jugarCarta(carta);
    }

    public void onPedirCarta() {
        modelo.pedirCarta();
    }

    public void onUnoGritado() {
        modelo.gritarUno();
    }

    public void onSpinCompletado() {
        modelo.girarRuleta();
    }

    public void onResultadoEvento(String evento, Object resultado) {
        modelo.aplicarEventoRuleta(evento, resultado);
    }

    public void onReconocerEvento() {
        modelo.limpiarEventoRuleta();
    }

    public void aplicarEventoRuleta(String evento, Object resultado) {
        modelo.aplicarEventoRuleta(evento, resultado);
    }

    public void onSeleccionColor(String color) {
        modelo.aplicarSeleccionColor(color);
    }
}
