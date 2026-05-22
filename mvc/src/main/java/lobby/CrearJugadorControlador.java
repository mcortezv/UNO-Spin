package lobby;

import dto.JugadorDTO;

public class CrearJugadorControlador {

    private final IControlUnionJugador controlUnionJugador;

    public CrearJugadorControlador(IControlUnionJugador controlUnionJugador) {
        this.controlUnionJugador = controlUnionJugador;
    }

    public void solicitarUnion(JugadorDTO jugador) {
        controlUnionJugador.solicitarUnion(jugador);
    }
}
