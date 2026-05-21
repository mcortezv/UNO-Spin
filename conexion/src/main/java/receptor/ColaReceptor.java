package receptor;
import interfaces.IReceptor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The type Cola receptor.
 */
public class ColaReceptor {
    private BlockingQueue<String> entrada = new LinkedBlockingQueue<>();
    private List<IReceptor> observadores = new ArrayList<>();

    /**
     * Recibir.
     *
     * @param json the json
     * @param port the port
     * @param ip   the ip
     */
    public void recibir(String json, int port, String ip) {
        System.out.println("ColaReceptor: Mensaje recibido. Notificando a observadores (Receptor)...");
        try {
            entrada.put(json);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        for (IReceptor obs : observadores) {
            obs.update(json, port, ip);
        }
    }

    /**
     * Attach.
     *
     * @param receptor the receptor
     */
    public void attach(IReceptor receptor) {
        observadores.add(receptor);
    }
}
