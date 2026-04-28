package blackboard.dominio;
import blackboard.dominio.entidades.Carta;
import blackboard.dominio.entidades.Jugador;
import blackboard.dominio.entidades.Tablero;
import blackboard.dominio.entidades.enums.EstadoPartida;
import blackboard.dominio.entidades.enums.TipoEventoRuleta;
import java.util.List;

public interface IDominio {

    void iniciarPartida(List<Jugador> jugadoresIniciales, Tablero tableroInicial);

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
