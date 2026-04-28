package blackboard;
import dominio.interfaces.IDominio;
import interfaces.IReceptor;
import java.util.ArrayList;
import java.util.List;

public class Blackboard {
    private static Blackboard instance;
    private final List<IReceptor> suscriptores = new ArrayList<>();
    private IDominio dominio;

    Blackboard(IDominio dominio) {
        this.dominio = dominio;
        instance = this;
    }

    public static void suscribir(IReceptor receptor){
        instance.suscriptores.add(receptor);
    }
}
