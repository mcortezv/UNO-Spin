package dispatcher;

import interfaces.IDispatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ColaDispatcher {

    private BlockingQueue<String> salida = new LinkedBlockingQueue<>();

    private List<IDispatcher> observadores = new ArrayList<>();

    public void encolar(String json, int port, String ip) {
        try {
            salida.put(json);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        for (IDispatcher obs : observadores) {
            obs.update(json, port, ip);
        }
    }

    public void attach(IDispatcher observador) {
        observadores.add(observador);
    }

}