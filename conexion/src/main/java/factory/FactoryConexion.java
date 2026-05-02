package factory;

import interfaces.IDispatcher;
import interfaces.IFactoryConexion;

/**
 * The type Factory conexion.
 */
public class FactoryConexion implements IFactoryConexion {

    @Override
    public IDispatcher crearConn(int puerto) {
        Conexion conexion = new Conexion(puerto);
        return conexion.getDispatcher();
    }
}
