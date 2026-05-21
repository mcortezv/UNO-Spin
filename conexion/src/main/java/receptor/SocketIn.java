package receptor;
import interfaces.IReceptorObserver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The type Socket in.
 */
public class SocketIn {
    private record MensajeEntrada(String json, String ip, int port) {}
    private static final MensajeEntrada POISON = new MensajeEntrada(null, null, 0);
    private final BlockingQueue<MensajeEntrada> colaEntrada = new LinkedBlockingQueue<>();
    private ServerSocket server;
    private ExecutorService pool;
    private Thread procesador;
    private final int puertoEscucha;
    private final IReceptorObserver observador;

    /**
     * Instantiates a new Socket in.
     *
     * @param puerto     the puerto
     * @param observador the observador
     */
    public SocketIn(int puerto, IReceptorObserver observador) {
        this.puertoEscucha = puerto;
        this.observador = observador;
    }

    /**
     * Start.
     */
    public void start() {
        pool = Executors.newCachedThreadPool();
        procesador = new Thread(this::drenar, "SocketIn-Procesador");
        procesador.setDaemon(true);
        procesador.start();

        new Thread(() -> {
            try {
                server = new ServerSocket();
                server.setReuseAddress(true);
                server.bind(new java.net.InetSocketAddress(puertoEscucha));
                System.out.println("Servidor escuchando en puerto " + puertoEscucha);

                while (!server.isClosed()) {
                    Socket clienteSocket = server.accept();
                    pool.submit(new ClienteHandler(clienteSocket, colaEntrada));
                }
            } catch (IOException e) {
                if (server == null || !server.isClosed()) {
                    System.err.println("Error en el ServerSocket - " + e.getMessage());
                }
            }
        }).start();
    }

    /**
     * Close.
     */
    public void close() {
        try {
            colaEntrada.offer(POISON);
            if (procesador != null) {
                procesador.interrupt();
            }
            if (pool != null && !pool.isShutdown()) {
                pool.shutdown();
            }
            if (server != null && !server.isClosed()) {
                server.close();
            }
        } catch (IOException e) {
            System.err.println("Error al cerrar servidor - " + e.getMessage());
        }
    }

    private void drenar() {
        while (true) {
            try {
                MensajeEntrada m = colaEntrada.take();
                if (m == POISON) break;
                observador.update(m.json(), m.port(), m.ip());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private static class ClienteHandler implements Runnable {

        private final Socket socket;
        private final BlockingQueue<MensajeEntrada> colaEntrada;

        /**
         * Instantiates a new Cliente handler.
         *
         * @param socket       the socket
         * @param colaEntrada  the cola entrada
         */
        public ClienteHandler(Socket socket, BlockingQueue<MensajeEntrada> colaEntrada) {
            this.socket = socket;
            this.colaEntrada = colaEntrada;
        }

        @Override
        public void run() {
            String ip = socket.getInetAddress().getHostAddress();
            int port = socket.getPort();
            System.out.println("Conexión recibida de " + ip + ":" + port);

            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String json;
                while ((json = in.readLine()) != null) {
                    System.out.println("JSON recibido '" + json + "'");
                    colaEntrada.offer(new MensajeEntrada(json, ip, port));
                }
            } catch (IOException e) {
                System.err.println("Error de lectura - " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                }
                System.out.println("Conexión cerrada con " + ip);
            }
        }
    }
}
