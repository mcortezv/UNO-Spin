package receptor;
import interfaces.IReceptor;

public class Receptor implements IReceptor {
    private final IReceptor componente;

    public Receptor(IReceptor componente) {
        this.componente = componente;
    }

    @Override
    public void update(String json, int port, String ip) {
        componente.update(json, port, ip);
    }
}