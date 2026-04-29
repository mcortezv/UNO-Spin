package factory;
import conexion.Conexion;
import interfaces.IDispatcher;
import interfaces.IFactoryConexion;

public class FactoryConexion implements IFactoryConexion {

    @Override
    public IDispatcher crearConn(int puerto) {
        Conexion conexion = new Conexion(puerto);
        return conexion.getDispatcher();
    }
}
