package interfaces;
import dto.CartaDTO;
import dto.JugadorDTO;
import java.util.List;

public interface IModeloLectura {

    List<CartaDTO> getDescarte();

    List<CartaDTO> getManoJugador();

    CartaDTO getCartaCima();

    String getNombreTurnoActual();

    List<JugadorDTO> getJugadoresRivales();

    List<JugadorDTO> getTodosLosJugadores();

    boolean isTurnoActivo();

    boolean isSpinActivo();

    String getEventoRuletaActual();

    boolean isUltimaJugadaValida();

    boolean isSeleccionColorPendiente();
}
