package controllers;

import dto.CartaDTO;
import interfaces.IModeloControlador;

public class Controlador {
    IModeloControlador modelo;

    public Controlador(IModeloControlador modelo) {
        this.modelo = modelo;
    }

    public void jugarCarta(CartaDTO carta) {
        modelo.jugarCarta(carta);
    }
}
