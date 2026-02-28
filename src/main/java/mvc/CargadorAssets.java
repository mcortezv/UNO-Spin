package mvc;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public final class CargadorAssets {

    private static CargadorAssets instancia;
    private final Map<String, ImageIcon> cache = new HashMap<>();

    private CargadorAssets() {
    }

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
        ImageIcon icon = getCarta(valor);
        return icon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
    }

    public Image getAvatarEscalado(int numero, int tam) {
        ImageIcon icon = getAvatar(numero);
        return icon.getImage().getScaledInstance(tam, tam, Image.SCALE_SMOOTH);
    }

    private ImageIcon cargar(String rutaRelativa) {
        return cache.computeIfAbsent(rutaRelativa, ruta -> {

            URL url = CargadorAssets.class.getResource("/" + ruta);

            if (url == null) {
                throw new IllegalArgumentException("Recurso no encontrado en classpath: " + ruta);
            }

            ImageIcon icon = new ImageIcon(url);

            // fuerza carga inmediata
            icon.getImage().getWidth(null);

            return icon;
        });
    }
}