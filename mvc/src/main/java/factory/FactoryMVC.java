package factory;
import interfaces.IDispatcher;
import interfaces.IFactoryMVC;
import interfaces.IReceptor;
import interfaces.ISerializer;
import mvc.MVC;
import mvc.Modelo;

public class FactoryMVC implements IFactoryMVC {

    private static final String IP_SERVIDOR = "127.0.0.1";
    private static final int PUERTO_SERVIDOR = 5000;

    @Override
    public IReceptor crearMVC(ISerializer serializer, IDispatcher dispatcher) {
        Modelo modelo = new Modelo(serializer, dispatcher, IP_SERVIDOR, PUERTO_SERVIDOR);
        return new MVC(serializer, modelo);
    }
}
