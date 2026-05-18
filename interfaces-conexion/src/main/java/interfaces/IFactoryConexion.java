package interfaces;

/**
 * The interface Factory conexion.
 */
public interface IFactoryConexion {

    /**
     * Crear conn dispatcher.
     *
     * @param puerto the puerto
     * @return the dispatcher
     */
    IDispatcher crearConn(int puerto);
}
