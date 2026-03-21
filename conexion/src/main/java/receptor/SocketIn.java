package receptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketIn {
    private ServerSocket server;
    private ExecutorService pool;
    private int puertoEscucha;

    private ColaReceptor colaReceptor;

    public SocketIn(int puerto, ColaReceptor cola) {
        this.puertoEscucha = puerto;
        this.colaReceptor = cola;
    }

    public void start() {
        pool = Executors.newCachedThreadPool();

        new Thread(() -> {
            try {
                server = new ServerSocket(puertoEscucha);
                System.out.println("Servidor escuchando en puerto " + puertoEscucha);

                while (!server.isClosed()) {
                    Socket clienteSocket = server.accept();
                    pool.submit(new ClienteHandler(clienteSocket, colaReceptor));
                }
            } catch (IOException e) {
                if (!server.isClosed()) {
                    System.err.println("Error en el ServerSocket - " + e.getMessage());
                }
            }
        }).start();
    }

    public void close() {
        try {
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

    private static class ClienteHandler implements Runnable {

        private Socket socket;
        private ColaReceptor colaReceptor;

        public ClienteHandler(Socket socket, ColaReceptor cola) {
            this.socket = socket;
            this.colaReceptor = cola;
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
                    colaReceptor.recibir(json, port, ip);
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
