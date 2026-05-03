package util;

/**
 * The type Socket cliente.
 */
public class SocketCliente {
    private final int indiceJugador;
    private final String ip;
    private final int puerto;

    /**
     * Instantiates a new Socket cliente.
     *
     * @param indiceJugador the indice jugador
     * @param ip            the ip
     * @param puerto        the puerto
     */
    public SocketCliente(int indiceJugador, String ip, int puerto) {
        this.indiceJugador = indiceJugador;
        this.ip = ip;
        this.puerto = puerto;
    }

    /**
     * Gets indice jugador.
     *
     * @return the indice jugador
     */
    public int getIndiceJugador() {
        return indiceJugador;
    }

    /**
     * Gets ip.
     *
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * Gets puerto.
     *
     * @return the puerto
     */
    public int getPuerto() {
        return puerto;
    }
}