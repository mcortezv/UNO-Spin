package factory;
import interfaces.IFactoryMVC;
import interfaces.IReceptor;
import interfaces.ISerializer;

public class FactoryMVC implements IFactoryMVC {

    @Override
    public IReceptor crearMVC(ISerializer serializer) {
        return null;
    }
}
