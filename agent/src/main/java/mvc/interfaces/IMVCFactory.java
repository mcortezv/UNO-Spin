package mvc.interfaces;
import interfaces.IDispatcher;

public interface IMVCFactory {
    IModeloConexion createModelo(IDispatcher iDispatcher);
}
