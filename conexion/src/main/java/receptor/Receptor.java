package receptor;

import interfaces.IReceptorComponente;
import interfaces.IReceptorObserver;

public class Receptor implements IReceptorObserver {
    private final IReceptorComponente componente;

    public Receptor(IReceptorComponente componente) {
        this.componente = componente;
    }

    @Override
    public void update(String json, int port, String ip) {
        componente.recibirMensaje(json);
    }
}
