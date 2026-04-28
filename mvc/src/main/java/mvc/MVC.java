package mvc;
import interfaces.IDispatcher;
import interfaces.IReceptor;
import java.util.ArrayList;
import java.util.List;

public class MVC implements IReceptor {
    private static MVC instance;
    private final List<IDispatcher> suscriptores = new ArrayList<>();

    MVC() {
        instance = this;
    }

    public static void suscribir(IDispatcher dispatcher) {
        instance.suscriptores.add(dispatcher);
    }

    public static void iniciar(){

    }

    @Override
    public void recibirMensaje(String json) {

    }
}
