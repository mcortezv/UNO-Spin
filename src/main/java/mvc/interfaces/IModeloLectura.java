package mvc.interfaces;
import dto.CartaDTO;
import dto.JugadorDTO;
import mvc.mock.PartidaMock;

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

     /**
      * Gets jugador en turno.
      *
      * @return the jugador en turno
      */
     JugadorDTO getJugadorEnTurno();

     /**
      * Gets mano jugador actual.
      *
      * @return the mano jugador actual
      */
     List<CartaDTO> getManoJugadorActual();

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
      * Is spin activo boolean.
      *
      * @return the boolean
      */
     boolean isSpinActivo();

     PartidaMock getPartidaMock();
}
