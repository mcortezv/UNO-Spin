package dispatcher;
import interfaces.IDispatcher;
import interfaces.IDispatcherObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Dispatcher implements IDispatcher {
    private final BlockingQueue<String> salida = new LinkedBlockingQueue<>();
    private final List<IDispatcherObserver> observadores = new ArrayList<>();

    public void attach(IDispatcherObserver observador) {
        observadores.add(observador);
    }

    @Override
    public void enviar(String json, int port, String ip) {
        try {
            salida.put(json);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        for (IDispatcherObserver obs : observadores) {
            obs.update(json, port, ip);
        }
    }
}
