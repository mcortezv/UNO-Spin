package blackboard;
import blackboard.dominio.IDominio;
import dto.CartaDTO;
import dto.EventoRuletaDTO;
import dto.JugadorDTO;
import interfaces.IBlackboard;
import interfaces.IReceptor;
import interfaces.ISerializer;
import java.util.ArrayList;
import java.util.List;

public class Blackboard implements IBlackboard {
    private static Blackboard instance;
    private final List<IReceptor> suscriptores = new ArrayList<>();
    private IDominio dominio;
    private ISerializer serializer;

    Blackboard(IDominio dominio, ISerializer serializer) {
        this.dominio = dominio;
        instance = this;
        this.serializer = serializer;
    }

    public static void suscribir(IReceptor receptor){
        instance.suscriptores.add(receptor);
    }

    @Override
    public void recibirMensaje(String json) {

    }

    @Override
    public List<JugadorDTO> getJugadores() {
        return List.of();
    }

    @Override
    public CartaDTO getCartaCima() {
        return null;
    }

    @Override
    public List<CartaDTO> getManoJugador(int indiceJugador) {
        return List.of();
    }

    @Override
    public String getEstadoPartida() {
        return null;
    }

    @Override
    public int getIndiceJugadorActual() {
        return 0;
    }

    @Override
    public EventoRuletaDTO getEventoRuleta() {
        return null;
    }

    @Override
    public boolean isUltimaJugadaValida() {
        return false;
    }
}
