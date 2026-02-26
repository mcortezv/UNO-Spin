package MVC;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.MediaTracker;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public final class CargadorAssets {

    private static final String BASE_PATH = "/assets/";
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
        if (icon == null || icon.getImage() == null) return null;
        return icon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
    }

    public Image getAvatarEscalado(int numero, int tam) {
        Image img = getAvatar(numero).getImage();
        if (img == null) return null;
        return img.getScaledInstance(tam, tam, Image.SCALE_SMOOTH);
    }


    private ImageIcon cargar(String rutaRelativa) {
        return cache.computeIfAbsent(rutaRelativa, ruta -> {
            URL url = getClass().getResource(BASE_PATH + ruta);
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                    return icon;
                }
                icon.getImage().getWidth(null);
                return icon;
            }
            java.io.File archivo = new java.io.File("assets/" + ruta);
            if (archivo.exists()) {
                ImageIcon icon = new ImageIcon(archivo.getAbsolutePath());
                icon.getImage().getWidth(null);
                return icon;
            }
            System.err.println("Asset no encontrado: " + ruta);
            java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB);
            return new ImageIcon(img);
        });
    }

    private ImageIcon crearFallback() {
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(
                1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        return new ImageIcon(img);
    }
}