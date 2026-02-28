package mvc.interfaces;
import dominio.Jugador;
import dto.CartaDTO;
import dto.JugadorRivalDTO;
import java.util.List;

public interface IModeloLectura {

     List<CartaDTO> getDescarte();

     List<CartaDTO> getManoJugador();

     Jugador getJugadorEnTurno();

     List<CartaDTO> getManoJugadorActual();

     CartaDTO getCartaCima();

     String getNombreTurnoActual();

     List<JugadorRivalDTO> getJugadoresRivales();

     boolean isSpinActivo();
}
