package interfaces;

import dto.CartaDTO;
import dto.JugadorDTO;
import dominio.entidades.enums.TipoEventoRuleta;
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
     List<CartaDTO> getManoJugadorEspecifico(int indiceJugador);
     boolean isTurnoActivoEspecifico(int indiceJugador);
     TipoEventoRuleta getEventoRuletaActual();
     int getPasoEventoActual();
     boolean isUltimaJugadaValida();
     boolean isSeleccionColorPendiente();
}