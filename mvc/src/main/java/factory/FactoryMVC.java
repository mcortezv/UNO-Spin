package factory;
import dto.JugadorDTO;
import interfaces.IDispatcher;
import interfaces.IFactoryMVC;
import interfaces.IReceptor;
import interfaces.ISerializer;
import mvc.Controlador;
import mvc.Modelo;
import op.UITurnoJugador;
import javax.swing.*;
import java.util.List;

/**
 * The type Factory mvc.
 */
public class FactoryMVC implements IFactoryMVC {

    private static final String IP_SERVIDOR  = "127.0.0.1";
    private static final int PUERTO_SERVIDOR = 5000;

    @Override
    public IReceptor crearMVC(ISerializer serializer, IDispatcher dispatcher) {
        int puertoCliente = Integer.parseInt(System.getProperty("puerto.cliente", "6000"));
        Modelo modelo = new Modelo(serializer, dispatcher, IP_SERVIDOR, PUERTO_SERVIDOR, puertoCliente);
        Controlador controlador = new Controlador(modelo);
        UITurnoJugador ventana = new UITurnoJugador(controlador, modelo, List.of(100, 100));
        modelo.subscribe(ventana);

        SwingUtilities.invokeLater(() -> mostrarLobby(ventana, controlador));

        return new MVC(serializer, modelo);
    }

    private void mostrarLobby(UITurnoJugador ventana, Controlador controlador) {
        String nombre = JOptionPane.showInputDialog(null,
                "Nombre del jugador:", "UNO-Spin — Unirse", JOptionPane.PLAIN_MESSAGE);
        if (nombre == null || nombre.isBlank()) nombre = "Jugador";

        JugadorDTO jugador = new JugadorDTO();
        jugador.setNombre(nombre.trim());

        controlador.unirsePartida(jugador, null);
        ventana.setVisible(true);

        JOptionPane.showMessageDialog(ventana,
                "Esperando jugadores...\nPresiona OK cuando todos estén listos.",
                "Sala de espera", JOptionPane.INFORMATION_MESSAGE);

        controlador.confirmarInicio(jugador);
    }
}