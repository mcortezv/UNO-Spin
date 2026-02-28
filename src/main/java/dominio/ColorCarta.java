package dominio;
import java.awt.*;

public class ColorCarta {

    private ColorCarta() {}

    public static final Color ROJO = new Color(204, 37, 37);
    public static final Color AZUL = new Color(30, 100, 195);
    public static final Color VERDE = new Color(46, 153, 56);
    public static final Color AMARILLO = new Color(218, 180, 0);
    public static final Color NEGRO = new Color(25, 25, 25);

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
