package mvc.comunicacion;

import comunicacion.IDispatcher;
import comunicacion.IReceptorComponente;

public class DispatcherFactory {

    public IDispatcher createDispatcher() {
        return new IDispatcher() {
            @Override
            public void enviar(String json, int port, String ip) {
            }
        };
    }

    public IReceptorComponente createReceptor() {
        return new IReceptorComponente() {
            @Override
            public void recibirMensaje(String json, int port, String ip) {

            }
        };
    }


}
