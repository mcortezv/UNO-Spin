package op;
import dto.CartaDTO;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Cargador assets.
 */
public final class CargadorAssets {

    private static final String FALLBACK = "carta_reves.png";

    private static CargadorAssets instancia;
    private final Map<String, ImageIcon> cache = new HashMap<>();

    private CargadorAssets() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static CargadorAssets getInstance() {
        if (instancia == null) {
            instancia = new CargadorAssets();
        }
        return instancia;
    }

    /**
     * Carga el asset correspondiente a una carta. Information Expert: este es
     * el único lugar donde vive el mapeo tipoCarta → nombre de archivo.
     *
     * @param carta the carta dto
     * @return the carta image icon
     */
    public ImageIcon getCarta(CartaDTO carta) {
        String clave = resolverClave(carta);
        if (clave == null) return null;
        return cargar("carta_" + clave + ".png");
    }

    /**
     * Gets reverso.
     *
     * @return the reverso
     */
    public ImageIcon getReverso() {
        return cargar(FALLBACK);
    }

    /**
     * Gets avatar.
     *
     * @param numero the numero
     * @return the avatar
     */
    public ImageIcon getAvatar(int numero) {
        return cargar("avatares/avatar_" + numero + ".png");
    }

    /**
     * Gets carta escalada.
     *
     * @param carta the carta dto
     * @param ancho the ancho
     * @param alto  the alto
     * @return the scaled image
     */
    public Image getCartaEscalada(CartaDTO carta, int ancho, int alto) {
        ImageIcon icon = getCarta(carta);
        if (icon == null) return null;
        return icon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
    }

    /**
     * Gets avatar escalado.
     *
     * @param numero the numero
     * @param tam    the tam
     * @return the avatar escalado
     */
    public Image getAvatarEscalado(int numero, int tam) {
        return getAvatar(numero).getImage().getScaledInstance(tam, tam, Image.SCALE_SMOOTH);
    }

    private String resolverClave(CartaDTO carta) {
        if (carta.getTipoCarta() == null) return null;
        return switch (carta.getTipoCarta()) {
            case "BLOQUEO"  -> "bloqueo";
            case "TOMA_DOS" -> "mas_dos";
            case "NUMERICA", "NUMERO_SPIN" -> carta.getNumero() != null ? String.valueOf(carta.getNumero()) : null;
            default         -> null;
        };
    }

    private ImageIcon cargar(String rutaRelativa) {
        if (cache.containsKey(rutaRelativa)) return cache.get(rutaRelativa);
        String rutaLimpia = "/" + (rutaRelativa.startsWith("/") ? rutaRelativa.substring(1) : rutaRelativa);
        URL url = getClass().getResource(rutaLimpia);
        if (url == null) {
            System.err.println("Asset no encontrado: " + rutaLimpia);
            ImageIcon fallback = rutaRelativa.equals(FALLBACK) ? null : cargar(FALLBACK);
            cache.put(rutaRelativa, fallback);
            return fallback;
        }
        ImageIcon icon = new ImageIcon(url);
        if (icon.getImageLoadStatus() == java.awt.MediaTracker.ERRORED) {
            System.err.println("Error al leer imagen: " + rutaLimpia);
            ImageIcon fallback = rutaRelativa.equals(FALLBACK) ? null : cargar(FALLBACK);
            cache.put(rutaRelativa, fallback);
            return fallback;
        }
        cache.put(rutaRelativa, icon);
        return icon;
    }
}
