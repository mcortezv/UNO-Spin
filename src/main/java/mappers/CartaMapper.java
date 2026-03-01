package mappers;
import dominio.Carta;
import dto.CartaDTO;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Mapper carta.
 */
public class CartaMapper {
    /**
     * To dto list.
     *
     * @param cartas the cartas
     * @return the list
     */
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

    /**
     * To dto carta dto.
     *
     * @param c the c
     * @return the carta dto
     */
    public static CartaDTO toDTO(Carta c) {
        CartaDTO dto = new CartaDTO();
        dto.setColor(c.getColor());
        dto.setValor(String.valueOf(c.getValor()));
        return dto;
    }
}
