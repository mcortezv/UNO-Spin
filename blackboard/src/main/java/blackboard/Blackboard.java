package blackboard;
import blackboard.dominio.IDominio;
import blackboard.dominio.entidades.enums.EstadoPartida;
import blackboard.dominio.entidades.enums.TipoEventoRuleta;
import blackboard.mappers.CartaMapper;
import blackboard.mappers.JugadorMapper;
import dto.CartaDTO;
import dto.JugadorDTO;
import dto.TipoAccionDTO;
import interfaces.IBlackboard;
import interfaces.IReceptor;
import interfaces.ISerializer;
import java.util.ArrayList;
import java.util.List;

public class Blackboard implements IBlackboard {
    private static Blackboard instance;
    private final List<IReceptor> suscriptores = new ArrayList<>();
    private final IDominio dominio;
    private final ISerializer serializer;

    Blackboard(IDominio dominio, ISerializer serializer) {
        this.dominio = dominio;
        this.serializer = serializer;
        instance = this;
    }

    public static void suscribir(IReceptor receptor) {
        instance.suscriptores.add(receptor);
    }

    @Override
    public void recibirMensaje(String json) {
        TipoAccionDTO accion = serializer.deserialize(json, TipoAccionDTO.class);
        switch (accion.getTipoAccion()) {
            case JUGAR_CARTA       -> dominio.aplicarJugada(CartaMapper.toEntity(accion.getCartaDTO()));
            case PEDIR_CARTA       -> dominio.robarCartaJugadorActual();
            case GRITAR_UNO        -> dominio.gritarUno();
            case SELECCIONAR_COLOR -> dominio.aplicarSeleccionColor(accion.getCartaDTO().getColor());
        }
        notificar();
    }

    private void notificar() {
        for (IReceptor s : suscriptores) {
            s.recibirMensaje("");
        }
    }

    @Override
    public List<JugadorDTO> getJugadores() {
        return JugadorMapper.toDTO(dominio.getJugadores(), dominio.getIndiceJugadorActual());
    }

    @Override
    public CartaDTO getCartaCima() {
        if (!partidaIniciada()) return null;
        return CartaMapper.toDTO(dominio.getCartaCima());
    }

    @Override
    public List<CartaDTO> getManoJugador(int indiceJugador) {
        if (!partidaIniciada()) return List.of();
        return CartaMapper.toDTO(dominio.getManoJugador(indiceJugador));
    }

    @Override
    public String getEstadoPartida() {
        EstadoPartida e = dominio.getEstadoPartida();
        return e == null ? null : e.name();
    }

    @Override
    public int getIndiceJugadorActual() {
        return dominio.getIndiceJugadorActual();
    }

    @Override
    public String getEventoRuleta() {
        if (!partidaIniciada()) return null;
        TipoEventoRuleta e = dominio.getEventoRuleta();
        return e == null ? null : e.name();
    }

    @Override
    public boolean isUltimaJugadaValida() {
        return dominio.isUltimaJugadaValida();
    }

    private boolean partidaIniciada() {
        EstadoPartida e = dominio.getEstadoPartida();
        return e != null && e != EstadoPartida.NO_INICIADA;
    }
}
