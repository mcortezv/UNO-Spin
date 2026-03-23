package dispatcher;
import interfaces.IDispatcher;

/**
 * The type Dispatcher.
 */
public class Dispatcher implements IDispatcher {
    private final ColaDispatcher cola;

    /**
     * Instantiates a new Dispatcher.
     *
     * @param cola the cola
     */
    public Dispatcher(ColaDispatcher cola) {
        this.cola = cola;
    }

    @Override
    public void enviar(String json, int port, String ip) {
        cola.encolar(json, port, ip);
    }
}
