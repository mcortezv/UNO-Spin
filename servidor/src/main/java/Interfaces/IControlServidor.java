package Interfaces;

public interface IControlServidor {

    void registrarCliente(int indiceJugador, String ip, int puerto);
    void desconectarCliente(int indiceJugador);

}
