package mvc.comunicacion;

import java.util.concurrent.BlockingQueue;

public class ColaReceptor {
    private BlockingQueue<String> entrada;
    private IReceptorObserver observador;

    public void recibir(String json, int port, String ip) {

    }

    public void atatch(IReceptorObserver receptor) {
        this.observador = receptor;
    }
}
