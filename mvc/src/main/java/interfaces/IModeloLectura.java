package interfaces;
import dto.CartaDTO;
import dto.JugadorDTO;
import java.util.List;

/**
 * The interface Modelo lectura.
 */
public interface IModeloLectura {

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
      * Gets todos los jugadores.
      *
      * @return the todos los jugadores
      */
     List<JugadorDTO> getTodosLosJugadores();

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


     /**
      * Gets evento ruleta actual.
      *
      * @return the evento ruleta actual
      */
     String getEventoRuletaActual();

     /**
      * Is ultima jugada valida boolean.
      *
      * @return the boolean
      */
     boolean isUltimaJugadaValida();

     /**
      * Is seleccion color pendiente boolean.
      *
      * @return the boolean
      */
     boolean isSeleccionColorPendiente();
}