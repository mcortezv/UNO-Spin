package dominio.interfaces;

import dominio.Carta;
import dominio.Jugador;
import dominio.enums.EstadoPartida;
import dominio.enums.TipoEventoRuleta;

import java.util.List;

public interface IDominio {

    boolean validarJugada(Carta carta);
    boolean aplicarJugada(Carta c);
    void robarCartaJugadorActual();
    void aplicarSeleccionColor(String color);
    TipoEventoRuleta procesarGiroRuleta() throws Exception;
    void aplicarEfectoRuleta(TipoEventoRuleta evento, Object resultado);
    void avanzarTurno();
    void gritarUno();
    EstadoPartida getEstadoPartida();
    int getIndiceJugadorActual();
    List<Jugador> getJugadores();
    int getCantidadCartasJugador(int indiceJugador);
    List<Carta> getManoJugador(int indiceJugador);
    Carta getCartaCima();
    List<Carta> getCartasDescarte();
    void aplicarCastigoUno(int indiceJugador);
    void iniciarPartida(List<Jugador> jugadoresIniciales, dominio.Tablero tableroInicial);
}