package mappers;
import dominio.entidades.ConfiguracionPartida;
import dto.ConfiguracionPartidaDTO;

/**
 * The type Configuracion partida mapper.
 */
public class ConfiguracionPartidaMapper {

    /**
     * To entity configuracion partida.
     *
     * @param dto the dto
     * @return the configuracion partida
     */
    public static ConfiguracionPartida toEntity(ConfiguracionPartidaDTO dto) {
        if (dto == null) return null;
        ConfiguracionPartida entity = new ConfiguracionPartida();
        entity.setValorMinimo(dto.getValorMinimo());
        entity.setValorMaximo(dto.getValorMaximo());
        entity.setCantidadComodines(dto.getCantidadComodines());
        entity.setCantidadCartasAccion(dto.getCantidadCartasAccion());
        entity.setTiempoMaximoRuleta(dto.getTiempoMaximoRuleta());
        return entity;
    }
}
