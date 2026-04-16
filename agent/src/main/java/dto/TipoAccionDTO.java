package dto;

import enums.TipoAccion;

public class TipoAccionDTO {
    private TipoAccion tipoAccion;
    private CartaDTO cartaDTO;

    public TipoAccionDTO(TipoAccion tipoAccion, CartaDTO cartaDTO) {
        this.tipoAccion = tipoAccion;
        this.cartaDTO = cartaDTO;
    }

    public TipoAccionDTO(TipoAccion tipoAccion) {
        this.tipoAccion = tipoAccion;
        this.cartaDTO = null;
    }
    public TipoAccion getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(TipoAccion tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public CartaDTO getCartaDTO() {
        return cartaDTO;
    }

    public void setCartaDTO(CartaDTO cartaDTO) {
        this.cartaDTO = cartaDTO;
    }
}
