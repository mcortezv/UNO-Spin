package interfaces;

/**
 * The interface Factory mvc.
 */
public interface IFactoryMVC {

    /**
     * Crear mvc receptor.
     *
     * @param serializer the serializer
     * @param dispatcher the dispatcher
     * @return the receptor
     */
    IReceptor crearMVC(ISerializer serializer, IDispatcher dispatcher);
}
