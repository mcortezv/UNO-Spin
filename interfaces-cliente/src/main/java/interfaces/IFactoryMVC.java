package interfaces;

public interface IFactoryMVC {

    IReceptor crearMVC(ISerializer serializer, IDispatcher dispatcher);
}
