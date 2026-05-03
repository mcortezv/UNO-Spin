package factory;
import dto.EstadoPartidaDTO;
import interfaces.IReceptor;
import interfaces.ISerializer;
import mvc.Modelo;

/**
 * The type Mvc.
 */
public class MVC implements IReceptor {
    private final ISerializer serializer;
    private final Modelo modelo;

    /**
     * Instantiates a new Mvc.
     *
     * @param serializer the serializer
     * @param modelo     the modelo
     */
    MVC(ISerializer serializer, Modelo modelo) {
        this.serializer = serializer;
        this.modelo = modelo;
    }

    @Override
    public void recibirMensaje(String json) {
        EstadoPartidaDTO estado = serializer.deserialize(json, EstadoPartidaDTO.class);
        modelo.actualizarEstado(estado);
    }
}