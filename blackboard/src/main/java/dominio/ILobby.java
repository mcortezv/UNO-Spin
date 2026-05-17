package dominio;

import dominio.entidades.ConfiguracionPartida;
import dominio.entidades.Jugador;
import java.util.List;

public interface ILobby {
    void registrarJugadorLobby(Jugador jugador);
    void aceptarSolicitudLobby(String nombre);
    void rechazarSolicitudLobby(String nombre);
    boolean puedeUnirseAlLobby();
    boolean esSalaLlena();
    List<Jugador> getJugadoresInscritos();
    List<Jugador> getSolicitudesPendientesLobby();
    String getHostNombre();
    void setConfiguracion(ConfiguracionPartida configuracion);
    ConfiguracionPartida getConfiguracion();
}
