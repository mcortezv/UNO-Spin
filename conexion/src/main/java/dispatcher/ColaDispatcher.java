package dispatcher;
import interfaces.IDispatcherObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The type Cola dispatcher.
 */
public class ColaDispatcher {
    private BlockingQueue<String> salida = new LinkedBlockingQueue<>();
    private List<IDispatcherObserver> observadores = new ArrayList<>();

    /**
     * Encolar.
     *
     * @param json the json
     * @param port the port
     * @param ip   the ip
     */
    public void encolar(String json, int port, String ip) {
        try {
            salida.put(json);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        for (IDispatcherObserver obs : observadores) {
            obs.update(json, port, ip);
        }
    }

    /**
     * Attach.
     *
     * @param observador the observador
     */
    public void attach(IDispatcherObserver observador) {
        observadores.add(observador);
    }
}
