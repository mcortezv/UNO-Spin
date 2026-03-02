package mvc.interfaces;
import dominio.enums.TipoEventoRuleta;
import dto.CartaDTO;
import dto.JugadorDTO;
import java.util.List;

/**
 * The interface Modelo lectura.
 */
public interface IModeloLectura {

     TipoEventoRuleta getEventoRuletaActual();

     /**
      * Gets descarte.
      *
      * @return the descarte
      */
     List<CartaDTO> getDescarte();

     /**
      * Gets mano jugador.
      *
      * @return the mano jugador
      */
     List<CartaDTO> getManoJugador();

     /**
      * Gets carta cima.
      *
      * @return the carta cima
      */
     CartaDTO getCartaCima();

     /**
      * Gets nombre turno actual.
      *
      * @return the nombre turno actual
      */
     String getNombreTurnoActual();

     /**
      * Gets jugadores rivales.
      *
      * @return the jugadores rivales
      */
     List<JugadorDTO> getJugadoresRivales();

     /**
      * Is turno activo boolean.
      *
      * @return the boolean
      */
     boolean isTurnoActivo();

     /**
      * Is spin activo boolean.
      *
      * @return the boolean
      */
     boolean isSpinActivo();

     List<CartaDTO> getManoJugadorEspecifico(int indiceJugador);
     boolean isTurnoActivoEspecifico(int indiceJugador);
}
