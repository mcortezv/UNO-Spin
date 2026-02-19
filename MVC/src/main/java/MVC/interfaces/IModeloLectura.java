package MVC.interfaces;

import domain.Jugador;
import dto.CartaDTO;

import java.util.List;

public interface IModeloLectura {

     List<CartaDTO> getDescarte();
     List<CartaDTO> getManoJugador();
     Jugador getJugadorEnTurno();
}
