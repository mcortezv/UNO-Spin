package mvc.comunicacion;

import comunicacion.IDispatcher;

public class Dispatcher implements IDispatcher {
    private ColaDispatcher cola;

    public Dispatcher(ColaDispatcher cola) {
        this.cola = cola;
    }

    @Override
    public void enviar(String json, int port, String ip) {

    }


}
