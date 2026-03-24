package assembler;

import dispatcherFactory.DispatcherFactory;
import interfaces.IDispatcher;
import mvc.interfaces.IMVCFactory;
import mvc.interfaces.IModeloConexion;

public class Ensamblador {

    private IMVCFactory mvcFactory;

    /**
     * Paso 1: Configura la conexión delegando todo a la DispatcherFactory.
     */
    public IDispatcher configurarConexion() {
        // No manejamos hilos aquí. La fábrica nos entrega un objeto ya funcional.
        return new DispatcherFactory().createDispatcher(); //
    }

    /**
     * Paso 2: Une el sistema de red con el modelo.
     */
    public IModeloConexion configurarModeloConexion(IDispatcher dispatcher) {
        if (this.mvcFactory == null) {
            throw new IllegalStateException("Se requiere una IMVCFactory configurada.");
        }
        return mvcFactory.createModelo(dispatcher); //
    }

    /**
     * Paso 3: Confirmación de conexión exitosa.
     */
    public void conectar(IModeloConexion modeloConexion) {
        // Como la fábrica ya inició los hilos de red, aquí solo validamos el estado.
        System.out.println("Ensamblaje exitoso. El Agente está operando en red."); //
    }

    public void setMvcFactory(IMVCFactory mvcFactory) {
        this.mvcFactory = mvcFactory;
    }
}
