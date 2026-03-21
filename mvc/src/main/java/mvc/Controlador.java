package mvc;

import dominio.entidades.enums.TipoEventoRuleta;
import dto.CartaDTO;
import interfaces.IControlador;
import interfaces.IModeloControlador;

public class Controlador implements IControlador {

    private final IModeloControlador modelo;

    public Controlador(IModeloControlador modelo) {
        this.modelo = modelo;
    }

    @Override
    public void jugarCarta(CartaDTO carta) {
        modelo.jugarCarta(carta);
    }

    @Override
    public void onCartaJugada(String valorCarta) {
        System.out.println("Carta jugada: " + valorCarta);
    }

    @Override
    public void onPedirCarta() {
        System.out.println("Pedir carta del mazo");
        modelo.pedirCarta();
    }

    @Override
    public void onUnoGritado() {
        modelo.gritarUno();
    }

    @Override
    public void onSpinCompletado() {
        System.out.println("Spin completado");
        modelo.girarRuleta();
    }

    @Override
    public void onResultadoEvento(TipoEventoRuleta evento, Object resultado) {
        System.out.println("Enviando evento al modelo: " + evento + " | Resultado: " + resultado);
        modelo.aplicarEventoRuleta(evento, resultado);
    }

    @Override
    public void onReconocerEvento() {
        modelo.limpiarEventoRuleta();
    }

    @Override
    public void aplicarEventoRuleta(TipoEventoRuleta evento, Object resultado) {
        modelo.aplicarEventoRuleta(evento, resultado);
    }

    @Override
    public void avanzarTurno() { }

    @Override
    public void onSeleccionColor(String color) {
        modelo.aplicarSeleccionColor(color);
    }
}