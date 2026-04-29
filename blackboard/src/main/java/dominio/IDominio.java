package dominio;
import dominio.entidades.Carta;
import dominio.entidades.ConfiguracionPartida;
import dominio.entidades.Jugador;
import dominio.entidades.enums.EstadoPartida;
import dominio.entidades.enums.TipoEventoRuleta;
import java.util.List;

public interface IDominio {

    void iniciarPartida(List<Jugador> jugadoresIniciales, ConfiguracionPartida configuracion);

    boolean validarJugada(Carta carta);

    boolean aplicarJugada(Carta carta);

    void robarCartaJugadorActual();

    void aplicarSeleccionColor(String color);

    void gritarUno();

    void aplicarCastigoUno(int indiceJugador);

    TipoEventoRuleta procesarGiroRuleta() throws Exception;

    void aplicarEfectoRuleta(TipoEventoRuleta evento, Object resultado);

    void avanzarTurno();

    EstadoPartida getEstadoPartida();

    int getIndiceJugadorActual();

    List<Jugador> getJugadores();

    int getCantidadCartasJugador(int indiceJugador);

    List<Carta> getManoJugador(int indiceJugador);

    Carta getCartaCima();

    List<Carta> getCartasDescarte();

    boolean isUltimaJugadaValida();

    TipoEventoRuleta getEventoRuleta();
}
