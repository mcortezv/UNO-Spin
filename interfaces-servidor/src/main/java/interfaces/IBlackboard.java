package interfaces;
import dto.CartaDTO;
import dto.JugadorDTO;
import java.util.List;

public interface IBlackboard extends IReceptor {

    List<JugadorDTO> getJugadores();

    CartaDTO getCartaCima();

    List<CartaDTO> getManoJugador(int indiceJugador);

    String getEstadoPartida();

    int getIndiceJugadorActual();

    String getEventoRuleta();

    boolean isUltimaJugadaValida();
}
