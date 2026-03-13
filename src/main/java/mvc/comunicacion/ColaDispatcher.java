package mvc.comunicacion;

import java.util.concurrent.BlockingDeque;

public class ColaDispatcher {
    private BlockingDeque<Dispatcher> salida;
    private IDispatcherObserver observer;

    public ColaDispatcher(BlockingDeque<Dispatcher> salida) {
        this.salida = salida;
    }

    public void encolar(String json, int port, String ip){

    }

    public void atatch(IDispatcherObserver cola){
        this.observer = cola;
    }
}
