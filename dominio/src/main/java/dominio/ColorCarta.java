package dominio;
import java.awt.*;

/**
 * The type Color carta.
 */
public class ColorCarta {

    private ColorCarta() {}

    /**
     * The constant ROJO.
     */
    public static final Color ROJO = new Color(204, 37, 37);
    /**
     * The constant AZUL.
     */
    public static final Color AZUL = new Color(30, 100, 195);
    /**
     * The constant VERDE.
     */
    public static final Color VERDE = new Color(46, 153, 56);
    /**
     * The constant AMARILLO.
     */
    public static final Color AMARILLO = new Color(218, 180, 0);
    /**
     * The constant NEGRO.
     */
    public static final Color NEGRO = new Color(25, 25, 25);

    /**
     * To awt color.
     *
     * @param colorDominio the color dominio
     * @return the color
     */
    public static Color toAWT(String colorDominio) {
        if (colorDominio == null) return NEGRO;
        return switch (colorDominio.toUpperCase().trim()) {
            case "ROJO" -> ROJO;
            case "AZUL" -> AZUL;
            case "VERDE" -> VERDE;
            case "AMARILLO" -> AMARILLO;
            default -> NEGRO;
        };
    }
}
