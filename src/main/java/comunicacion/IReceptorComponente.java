package comunicacion;

public interface IReceptorComponente {
    void recibirMensaje(String json, int port, String ip);
}
