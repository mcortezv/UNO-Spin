package interfaces;

/**
 * The interface Dispatcher.
 */
public interface IDispatcher {

    /**
     * Enviar.
     *
     * @param json the json
     * @param port the port
     * @param ip   the ip
     */
    void enviar(String json, int port, String ip);
}
