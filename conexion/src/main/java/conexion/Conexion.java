package conexion;
import dispatcher.Dispatcher;
import dispatcher.SocketOut;
import interfaces.IDispatcher;
import interfaces.IReceptor;
import interfaces.IReceptorObserver;
import receptor.SocketIn;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Conexion implements IReceptorObserver {
    private static Conexion instance;
    private final List<IReceptor> suscriptores = new ArrayList<>();
    private final BlockingQueue<String> entrada = new LinkedBlockingQueue<>();
    private final SocketIn socketIn;
    private final SocketOut socketOut;
    private final IDispatcher dispatcher;

    Conexion(int puerto) {
        this.socketIn = new SocketIn(puerto, this);

        Dispatcher dispatcher = new Dispatcher();
        this.socketOut = new SocketOut();
        dispatcher.attach(socketOut);
        this.dispatcher = dispatcher;

        instance = this;
    }

    public static void iniciar() {
        instance.socketOut.start();
        instance.socketIn.start();
    }

    public static void suscribir(IReceptor receptor) {
        instance.suscriptores.add(receptor);
    }

    public IDispatcher getDispatcher() {
        return dispatcher;
    }

    @Override
    public void update(String json, int port, String ip) {
        try {
            entrada.put(json);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        for (IReceptor s : suscriptores) {
            s.recibirMensaje(json);
        }
    }
}
