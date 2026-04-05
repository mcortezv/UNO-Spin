package mappers;
import dominio.entidades.Jugador;
import dto.JugadorDTO;

import java.util.ArrayList;
import java.util.List;

public class JugadorMapper {

    public static JugadorDTO toDTO(Jugador jugador) {
        JugadorDTO dto = new JugadorDTO(
                jugador.getNombre(),
                jugador.getNumeroAvatar(),
                jugador.getMano().getCartas().size(),
                false,
                CartaMapper.toDTO(jugador.getMano().getCartas())
        );
        return dto;
    }

    public static List<JugadorDTO> toDTO(List<Jugador> jugadores) {
        List<JugadorDTO> lista = new ArrayList<>();
        for (Jugador j : jugadores) {
            lista.add(toDTO(j));
        }
        return lista;
    }
}