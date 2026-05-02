package mappers;
import dominio.entidades.Jugador;
import dominio.entidades.Mano;
import dto.JugadorDTO;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Jugador mapper.
 */
public class JugadorMapper {

    /**
     * To dto jugador dto.
     *
     * @param jugador       the jugador
     * @param esTurnoActual the es turno actual
     * @return the jugador dto
     */
    public static JugadorDTO toDTO(Jugador jugador, boolean esTurnoActual) {
        return new JugadorDTO(
                jugador.getNombre(),
                jugador.getNumeroAvatar(),
                jugador.getMano().getCartas().size(),
                esTurnoActual
        );
    }

    /**
     * To dto list.
     *
     * @param jugadores    the jugadores
     * @param indiceActual the indice actual
     * @return the list
     */
    public static List<JugadorDTO> toDTO(List<Jugador> jugadores, int indiceActual) {
        List<JugadorDTO> lista = new ArrayList<>();
        if (jugadores != null) {
            for (int i = 0; i < jugadores.size(); i++) {
                lista.add(toDTO(jugadores.get(i), i == indiceActual));
            }
        }
        return lista;
    }

    /**
     * To entity jugador.
     *
     * @param dto the dto
     * @return the jugador
     */
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