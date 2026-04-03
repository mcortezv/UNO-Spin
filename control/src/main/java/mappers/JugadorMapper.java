package mappers;
import dominio.Jugador;
import dto.JugadorDTO;
import java.util.List;

public class JugadorMapper {

    public static JugadorDTO toDTO(Jugador jugador) {
        return new JugadorDTO(jugador.getNombre(), jugador.getNumeroAvatar(), jugador.getMano().getCartas().size(), true);
    }

    public static JugadorDTO toDTO(List<Jugador> jugadores) {
        List<JugadorDTO> jugadoresDTO = jugadores.stream().map(JugadorMapper::toDTO).toList();
    }
}