package receptor;

import interfaces.IReceptorObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ColaReceptor {

    private BlockingQueue<String> entrada = new LinkedBlockingQueue<>();

    private List<IReceptorObserver> observadores = new ArrayList<>();

    public void recibir(String json, int port, String ip) {
        System.out.println("ColaReceptor: Mensaje recibido. Notificando a observadores (Receptor)...");
        try {
            entrada.put(json);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        for (IReceptorObserver obs : observadores) {
            obs.update(json, port, ip);
        }
    }

    public void attach(IReceptorObserver receptor) {
        observadores.add(receptor);
    }
}
