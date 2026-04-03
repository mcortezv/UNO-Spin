package interfaces;

/**
 * The interface Receptor observer.
 */
public interface IReceptorObserver {

    /**
     * Update.
     *
     * @param json the json
     * @param port the port
     * @param ip   the ip
     */
    void update(String json, int port, String ip);
}
