package interfaces;

/**
 * The interface Control servidor.
 */
public interface IControlServidor {

    /**
     * Registrar cliente.
     *
     * @param indiceJugador the indice jugador
     * @param ip            the ip
     * @param puerto        the puerto
     */
    void registrarCliente(int indiceJugador, String ip, int puerto);

    /**
     * Desconectar cliente.
     *
     * @param indiceJugador the indice jugador
     */
    void desconectarCliente(int indiceJugador);
}
