package conexion;

import interfaces.IReceptor;
import java.util.ArrayList;
import java.util.List;

public class Conexion {
    private static Conexion instance;
    private final List<IReceptor> suscriptores = new ArrayList<>();

    Conexion() {
        instance = this;
    }

    public static void suscribir (IReceptor receptor){
        instance.suscriptores.add(receptor);
    }

}
