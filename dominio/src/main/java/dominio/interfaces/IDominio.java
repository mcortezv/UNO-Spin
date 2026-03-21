package dominio.interfaces;

import dominio.entidades.Carta;
import dominio.entidades.Jugador;
import dominio.entidades.Tablero;
import dominio.entidades.enums.EstadoPartida;
import dominio.entidades.enums.TipoEventoRuleta;

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
    void iniciarPartida(List<Jugador> jugadoresIniciales, Tablero tableroInicial);
}