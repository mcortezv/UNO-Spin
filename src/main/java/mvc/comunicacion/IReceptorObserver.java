package mvc.comunicacion;

public interface IReceptorObserver {
    void update(String json, int port, String ip);
}
