package factory;
import interfaces.IDispatcher;
import interfaces.IFactoryMVC;
import interfaces.IReceptor;
import interfaces.ISerializer;
import lobby.LobbyControlador;
import lobby.LobbyModelo;
import lobby.CrearJugadorControlador;
import lobby.CrearPartidaControlador;
import lobby.MenuControlador;
import lobby.NavegadorLobby;
import mvc.Controlador;
import mvc.Modelo;
import lobby.UICrearJugador;
import lobby.UICrearPartida;
import lobby.UIMenuPrincipal;
import lobby.UIPantallaCarga;
import mvc.UITurnoJugador;
import javax.swing.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

/**
 * The type Factory mvc.
 */
public class FactoryMVC implements IFactoryMVC {

    private static final String IP_SERVIDOR  = System.getProperty("ip.servidor", "127.0.0.1");
    private static final int PUERTO_SERVIDOR = 5000;

    @Override
    public IReceptor crearMVC(ISerializer serializer, IDispatcher dispatcher) {
        int puertoCliente = Integer.parseInt(System.getProperty("puerto.cliente", "6000"));
        String ipLocal = detectarIpLocal();

        Modelo gameModelo = new Modelo(serializer, dispatcher, IP_SERVIDOR, PUERTO_SERVIDOR, puertoCliente, ipLocal);
        Controlador gameControlador = new Controlador(gameModelo);
        UITurnoJugador gameUI = new UITurnoJugador(gameControlador, gameModelo, List.of(100, 100));
        gameModelo.subscribe(gameUI);

        LobbyModelo lobbyModelo = new LobbyModelo(dispatcher, serializer, IP_SERVIDOR, PUERTO_SERVIDOR, puertoCliente, ipLocal);
        LobbyControlador lobbyControlador = new LobbyControlador(lobbyModelo);
        lobbyControlador.setControladorJuego(gameControlador);

        NavegadorLobby navegadorLobby = new NavegadorLobby();
        MenuControlador menuControlador = new MenuControlador(navegadorLobby, navegadorLobby);
        CrearPartidaControlador crearPartidaControlador = new CrearPartidaControlador(lobbyModelo, navegadorLobby);
        CrearJugadorControlador crearJugadorControlador = new CrearJugadorControlador(lobbyControlador);
        UIMenuPrincipal menuUI = new UIMenuPrincipal(menuControlador);
        UICrearPartida crearPartidaUI = new UICrearPartida(crearPartidaControlador);
        UICrearJugador lobbyUI = new UICrearJugador(crearJugadorControlador);
        UIPantallaCarga pantallaCarga = new UIPantallaCarga(lobbyControlador);
        navegadorLobby.setPantallas(menuUI, crearPartidaUI, lobbyUI);
        lobbyModelo.suscribir(lobbyControlador);
        lobbyModelo.suscribir(lobbyUI);
        lobbyModelo.suscribir(pantallaCarga);

        SwingUtilities.invokeLater(navegadorLobby::mostrarMenu);

        return new MVC(serializer, lobbyModelo, gameModelo);
    }

    private static String detectarIpLocal() {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName(FactoryMVC.IP_SERVIDOR), 1);
            return socket.getLocalAddress().getHostAddress();
        } catch (Exception e) {
            return "127.0.0.1";
        }
    }
}
