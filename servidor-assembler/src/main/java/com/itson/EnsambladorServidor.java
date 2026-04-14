package com.itson;

import Interfaces.IControlServidor;
import dominio.entidades.Partida;
import dominio.interfaces.IDominio;
import interfaces.IDispatcher;
import interfaces.IReceptorComponente;
import interfaces.ISerializer;
import servidor.ControlServidor;

public class EnsambladorServidor {
    private final Partida partida;
    private final ControlServidor controlServidor;

    public EnsambladorServidor(ISerializer serializer, IDispatcher dispatcher) {
        this.partida = new Partida();
        this.controlServidor = new ControlServidor(partida, dispatcher, serializer);
    }

    public IReceptorComponente getReceptor() {
        return controlServidor;
    }

    public IDominio getDominio() {
        return partida;
    }

    public IControlServidor getControlServidor() {
        return controlServidor;
    }
}
