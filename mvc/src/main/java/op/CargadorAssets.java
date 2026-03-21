package op;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public final class CargadorAssets {

    private static CargadorAssets instancia;
    private final Map<String, ImageIcon> cache = new HashMap<>();

    private CargadorAssets() {}

    public static CargadorAssets getInstance() {
        if (instancia == null) {
            instancia = new CargadorAssets();
        }
        return instancia;
    }

    public ImageIcon getCarta(String valor) {
        return cargar("carta_" + valor.toLowerCase() + ".png");
    }

    public ImageIcon getReverso() {
        return cargar("carta_reves.png");
    }

    public ImageIcon getAvatar(int numero) {
        return cargar("avatares/avatar_" + numero + ".png");
    }

    public Image getCartaEscalada(String valor, int ancho, int alto) {
        return getCarta(valor).getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
    }

    public Image getAvatarEscalado(int numero, int tam) {
        return getAvatar(numero).getImage().getScaledInstance(tam, tam, Image.SCALE_SMOOTH);
    }

    private ImageIcon cargar(String rutaRelativa) {
        return cache.computeIfAbsent(rutaRelativa, ruta -> {
            String rutaLimpia = ruta.startsWith("/") ? ruta : "/" + ruta;
            URL url = getClass().getResource(rutaLimpia);

            if (url == null) {
                throw new IllegalArgumentException("Recurso no encontrado en classpath: " + rutaLimpia);
            }

            ImageIcon icon = new ImageIcon(url);

            if (icon.getImageLoadStatus() == java.awt.MediaTracker.ERRORED) {
                throw new RuntimeException("Error al leer los bytes de la imagen: " + rutaLimpia);
            }

            return icon;
        });
    }
}