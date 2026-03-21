package dominio.interfaces;

import dominio.entidades.Jugador;
import dominio.entidades.Tablero;
import dominio.entidades.enums.EstadoPartida;
import dominio.entidades.enums.TipoEventoRuleta;
import dto.CartaDTO;
import dto.JugadorDTO;

import java.util.List;

public interface IDominio {

    void iniciarPartida(List<Jugador> jugadoresIniciales, Tablero tableroInicial);
    boolean validarJugada(CartaDTO carta);
    boolean aplicarJugada(CartaDTO carta);
    void robarCartaJugadorActual();
    void aplicarSeleccionColor(String color);
    void gritarUno();
    void aplicarCastigoUno(int indiceJugador);
    TipoEventoRuleta procesarGiroRuleta() throws Exception;
    void aplicarEfectoRuleta(TipoEventoRuleta evento, Object resultado);
    void avanzarTurno();
    EstadoPartida getEstadoPartida();
    int getIndiceJugadorActual();
    List<JugadorDTO> getJugadores();
    int getCantidadCartasJugador(int indiceJugador);
    List<CartaDTO> getManoJugador(int indiceJugador);
    CartaDTO getCartaCima();
    List<CartaDTO> getCartasDescarte();
}