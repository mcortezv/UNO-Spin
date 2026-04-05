package ensamblador;

import interfaces.IDispatcher;
import interfaces.IModeloConexion;
import interfaces.ISerializer;
import mvc.Modelo;

public class EnsambladorJugador {

    IModeloConexion conexion;
    public void ensamblar(ISerializer serializer, IDispatcher dispatcher){
        IModeloConexion jugador = new Modelo(serializer, dispatcher);
        this.conexion = jugador;
    }
}
