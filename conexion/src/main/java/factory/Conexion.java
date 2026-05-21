package factory;
import dispatcher.SocketOut;
import interfaces.IDispatcher;
import interfaces.IReceptor;
import interfaces.IReceptorObserver;
import receptor.SocketIn;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Conexion.
 */
public class Conexion implements IReceptorObserver {
    private static Conexion instance;
    private final List<IReceptor> suscriptores = new ArrayList<>();
    private final SocketIn socketIn;
    private final SocketOut socketOut;

    /**
     * Instantiates a new Conexion.
     *
     * @param puerto the puerto
     */
    Conexion(int puerto) {
        this.socketIn = new SocketIn(puerto, this);
        this.socketOut = new SocketOut();
        instance = this;
    }

    /**
     * Iniciar.
     */
    public static void iniciar() {
        instance.socketOut.start();
        instance.socketIn.start();
    }

    /**
     * Suscribir.
     *
     * @param receptor the receptor
     */
    public static void suscribir(IReceptor receptor) {
        instance.suscriptores.add(receptor);
    }

    /**
     * Gets dispatcher.
     *
     * @return the dispatcher
     */
    public IDispatcher getDispatcher() {
        return socketOut;
    }

    @Override
    public void update(String json, int port, String ip) {
        for (IReceptor s : suscriptores) {
            s.recibirMensaje(json);
        }
    }
}
