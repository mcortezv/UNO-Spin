package dispatcher;
import interfaces.IDispatcher;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The type Socket out.
 */
public class SocketOut implements IDispatcher {
    private record MensajeSalida(String json, String ip, int port) {}
    private static final MensajeSalida POISON = new MensajeSalida(null, null, 0);
    private final BlockingQueue<MensajeSalida> colaSalida = new LinkedBlockingQueue<>();
    private Thread trabajador;
    private volatile boolean activo;

    /**
     * Start.
     */
    public void start() {
        this.activo = true;
        this.trabajador = new Thread(this::drenar, "SocketOut-Worker");
        this.trabajador.setDaemon(true);
        this.trabajador.start();
    }

    /**
     * Close.
     */
    public void close() {
        activo = false;
        colaSalida.offer(POISON);
        if (trabajador != null) {
            trabajador.interrupt();
        }
    }

    @Override
    public void enviar(String json, int port, String ip) {
        if (!activo) {
            System.err.println("SocketOut no está activo, mensaje descartado");
            return;
        }
        System.out.println("Recibido. Encolando para " + ip + ":" + port);
        colaSalida.offer(new MensajeSalida(json, ip, port));
    }

    private void drenar() {
        while (activo) {
            try {
                MensajeSalida m = colaSalida.take();
                if (m == POISON) break;
                enviarPorSocket(m.json(), m.ip(), m.port());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void enviarPorSocket(String json, String ip, int port) {
        try (Socket socket = new Socket(ip, port); PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Enviando '" + json + "' a " + ip + ":" + port);
            out.println(json);
            System.out.println("Envío completado");

        } catch (Exception e) {
            System.err.println("Error al enviar - " + e.getMessage());
        }
    }
}
