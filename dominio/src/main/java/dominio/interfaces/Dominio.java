package dominio.interfaces;

import dominio.entidades.Jugador;
import dominio.entidades.Tablero;
import dominio.entidades.enums.EstadoPartida;
import dominio.entidades.enums.TipoEventoRuleta;
import dto.CartaDTO;
import dto.JugadorDTO;

import java.util.List;

public class Dominio implements IDominio {
    @Override
    public void iniciarPartida(List<Jugador> jugadoresIniciales, Tablero tableroInicial) {

    }

    @Override
    public boolean validarJugada(CartaDTO carta) {
        return false;
    }

    @Override
    public boolean aplicarJugada(CartaDTO carta) {
        return false;
    }

    @Override
    public void robarCartaJugadorActual() {

    }

    @Override
    public void aplicarSeleccionColor(String color) {

    }

    @Override
    public TipoEventoRuleta procesarGiroRuleta() throws Exception {
        return null;
    }

    @Override
    public void aplicarEfectoRuleta(TipoEventoRuleta evento, Object resultado) {

    }

    @Override
    public void avanzarTurno() {

    }

    @Override
    public void gritarUno() {

    }

    @Override
    public void aplicarCastigoUno(int indiceJugador) {

    }

    @Override
    public EstadoPartida getEstadoPartida() {
        return null;
    }

    @Override
    public int getIndiceJugadorActual() {
        return 0;
    }

    @Override
    public int getCantidadCartasJugador(int indiceJugador) {
        return 0;
    }

    @Override
    public List<JugadorDTO> getJugadores() {
        return List.of();
    }

    @Override
    public List<CartaDTO> getManoJugador(int indiceJugador) {
        return List.of();
    }

    @Override
    public CartaDTO getCartaCima() {
        return null;
    }

    @Override
    public List<CartaDTO> getCartasDescarte() {
        return List.of();
    }
}
