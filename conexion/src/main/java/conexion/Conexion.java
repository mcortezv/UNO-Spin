package conexion;
import dispatcher.ColaDispatcher;
import dispatcher.Dispatcher;
import dispatcher.SocketOut;
import interfaces.IDispatcher;
import interfaces.IReceptor;
import interfaces.IReceptorObserver;
import receptor.ColaReceptor;
import receptor.SocketIn;
import java.util.ArrayList;
import java.util.List;

public class Conexion implements IReceptorObserver {
    private static Conexion instance;
    private final List<IReceptor> suscriptores = new ArrayList<>();
    private final SocketIn socketIn;
    private final SocketOut socketOut;
    private final IDispatcher dispatcher;

    Conexion(int puerto) {
        ColaReceptor colaReceptor = new ColaReceptor();
        colaReceptor.attach(this);
        this.socketIn = new SocketIn(puerto, colaReceptor);

        ColaDispatcher colaDispatcher = new ColaDispatcher();
        this.socketOut = new SocketOut();
        colaDispatcher.attach(socketOut);
        this.dispatcher = new Dispatcher(colaDispatcher);

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
        for (IReceptor s : suscriptores) {
            s.recibirMensaje(json);
        }
    }
}
