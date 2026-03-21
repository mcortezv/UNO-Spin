package dominio.mappers;

import dominio.entidades.Carta;
import dominio.entidades.enums.TipoCarta;
import dto.CartaDTO;

import java.util.ArrayList;
import java.util.List;

public class CartaMapper {

    public static List<CartaDTO> toDTO(List<Carta> cartas) {
        List<CartaDTO> listaDTO = new ArrayList<>();
        if (cartas != null) {
            for (Carta c : cartas) {
                listaDTO.add(toDTO(c));
            }
        }
        return listaDTO;
    }

    public static CartaDTO toDTO(Carta c) {
        if (c == null) return null;
        CartaDTO dto = new CartaDTO();
        dto.setColor(c.getColor());
        dto.setTipoCarta(c.getTipoCarta().toString());
        dto.setNumero(c.getNumero());
        dto.setValor(String.valueOf(c.getValor()));
        return dto;
    }

    public static Carta toEntity(CartaDTO dto) {
        if (dto == null) return null;
        Carta c = new Carta();
        c.setColor(dto.getColor());
        c.setTipoCarta(TipoCarta.valueOf(dto.getTipoCarta()));
        c.setNumero(dto.getNumero());
        if (dto.getValor() != null && !dto.getValor().isBlank()) {
            c.setValor(Integer.parseInt(dto.getValor()));
        } else {
            c.setValor(0);
        }
        return c;
    }
}