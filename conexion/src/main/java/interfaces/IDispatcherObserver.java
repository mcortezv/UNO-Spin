package interfaces;

/**
 * The interface Dispatcher observer.
 */
public interface IDispatcherObserver {

    /**
     * Update.
     *
     * @param json the json
     * @param port the port
     * @param ip   the ip
     */
    void update(String json, int port, String ip);
}
