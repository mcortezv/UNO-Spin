package mappers;
import dominio.Jugador;
import dto.JugadorDTO;

public class JugadorMapper {

    public static JugadorDTO toDTO(Jugador jugador) {
        return new JugadorDTO(jugador.getNombre(), jugador.getNumeroAvatar(), jugador.getMano().getCartas().size(), true);

    }
}