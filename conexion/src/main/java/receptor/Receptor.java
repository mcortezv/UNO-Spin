package receptor;
import interfaces.IReceptorObserver;

/**
 * The type Receptor.
 */
public class Receptor implements IReceptorObserver {
    private final IReceptorComponente componente;

    /**
     * Instantiates a new Receptor.
     *
     * @param componente the componente
     */
    public Receptor(IReceptorComponente componente) {
        this.componente = componente;
    }

    @Override
    public void update(String json, int port, String ip) {
        componente.recibirMensaje(json);
    }
}
