package factory;
import interfaces.IFactoryControlServidor;
import interfaces.IReceptor;

public class FactoryControlServidor implements IFactoryControlServidor {

    @Override
    public IReceptor crearControlServidor() {
        return null;
    }
}
