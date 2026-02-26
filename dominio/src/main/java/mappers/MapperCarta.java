package mappers;

import domain.Carta;
import dto.CartaDTO;
import java.util.ArrayList;
import java.util.List;

public class MapperCarta {
    public static List<CartaDTO> toDTO(List<Carta> cartas) {
        List<CartaDTO> listaDTO = new ArrayList<>();
        if (cartas != null) {
            for (Carta c : cartas) {
                CartaDTO dto = toDTO(c);
                listaDTO.add(dto);
            }
        }
        return listaDTO;
    }
    public static CartaDTO toDTO(Carta c) {
        CartaDTO dto = new CartaDTO();
        dto.setColor(c.getColor());
        dto.setValor(String.valueOf(c.getValor()));
        return dto;
    }
}
