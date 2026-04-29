package dispatcher;
import interfaces.IDispatcher;
import interfaces.IDispatcherObserver;
import java.util.ArrayList;
import java.util.List;

public class Dispatcher implements IDispatcher {
    private final List<IDispatcherObserver> observadores = new ArrayList<>();

    public void attach(IDispatcherObserver observador) {
        observadores.add(observador);
    }

    @Override
    public void enviar(String json, int port, String ip) {
        for (IDispatcherObserver obs : observadores) {
            obs.update(json, port, ip);
        }
    }
}
