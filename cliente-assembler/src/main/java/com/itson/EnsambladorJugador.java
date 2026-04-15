package com.itson;
import interfaces.IDispatcher;
import interfaces.IModeloConexion;
import mvc.Modelo;

public class EnsambladorJugador {

    IModeloConexion conexion;
    public void ensamblar(interfaces.ISerializer serializer, IDispatcher dispatcher){
        this.conexion = new Modelo(serializer, dispatcher);
    }

}
