package mappers;
import dominio.Carta;
import dominio.enums.TipoCarta;
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
        dto.setTipoCarta(c.getTipoCarta().toString());
        dto.setNumero(c.getNumero());
        dto.setValor(String.valueOf(c.getValor()));
        return dto;
    }

    public static Carta toEntity(CartaDTO dto) {
        Carta c = new Carta();
        c.setColor(dto.getColor());
        c.setTipoCarta(TipoCarta.valueOf(dto.getTipoCarta()));
        c.setNumero(dto.getNumero());
        c.setValor(Integer.parseInt(dto.getValor()));
        return c;
    }
}
