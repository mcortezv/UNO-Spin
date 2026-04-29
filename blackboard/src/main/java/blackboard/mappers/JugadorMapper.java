package blackboard.mappers;
import blackboard.dominio.entidades.Jugador;
import blackboard.dominio.entidades.Mano;
import dto.JugadorDTO;
import java.util.ArrayList;
import java.util.List;

public class JugadorMapper {

    public static JugadorDTO toDTO(Jugador jugador, boolean esTurnoActual) {
        return new JugadorDTO(
                jugador.getNombre(),
                jugador.getNumeroAvatar(),
                jugador.getMano().getCartas().size(),
                esTurnoActual
        );
    }

    public static List<JugadorDTO> toDTO(List<Jugador> jugadores, int indiceActual) {
        List<JugadorDTO> lista = new ArrayList<>();
        if (jugadores != null) {
            for (int i = 0; i < jugadores.size(); i++) {
                lista.add(toDTO(jugadores.get(i), i == indiceActual));
            }
        }
        return lista;
    }

    public static Jugador toEntity(JugadorDTO dto) {
        if (dto == null) return null;
        return new Jugador(
                dto.getNombre(),
                new Mano(new ArrayList<>()),
                new ArrayList<>(),
                0,
                dto.getNumeroAvatar()
        );
    }
}
