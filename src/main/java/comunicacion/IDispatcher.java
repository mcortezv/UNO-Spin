package comunicacion;

public interface IDispatcher {
    void enviar(String json, int port, String ip);
}
