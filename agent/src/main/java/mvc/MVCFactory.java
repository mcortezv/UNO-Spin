package mvc;

import interfaces.IDispatcher;
import interfaces.ISerializer;
import mvc.interfaces.IMVCFactory;
import mvc.interfaces.IModeloConexion;

public class MVCFactory implements IMVCFactory {

    private final ISerializer serializer;

    public MVCFactory(ISerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public IModeloConexion createModelo(IDispatcher dispatcher) {
        if (dispatcher == null) {
            throw new IllegalArgumentException("El dispatcher no puede ser null.");
        }
        return new Modelo(dispatcher, serializer);
    }
}
