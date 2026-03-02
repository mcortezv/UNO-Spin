package mvc.interfaces;
import dto.CartaDTO;
import dto.JugadorDTO;
import dominio.enums.TipoEventoRuleta; // ← AGREGADO
import java.util.List;

/**
 * The interface Modelo lectura.
 */
public interface IModeloLectura {

     List<CartaDTO> getDescarte();

     List<CartaDTO> getManoJugador();

     CartaDTO getCartaCima();

     String getNombreTurnoActual();

     List<JugadorDTO> getJugadoresRivales();

     boolean isTurnoActivo();

     boolean isSpinActivo();

     List<CartaDTO> getManoJugadorEspecifico(int indiceJugador);

     boolean isTurnoActivoEspecifico(int indiceJugador);

     TipoEventoRuleta getEventoRuletaActual();
}