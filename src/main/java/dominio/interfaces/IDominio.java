package dominio.interfaces;
import dominio.Carta;

/**
 * The interface Dominio.
 */
public interface IDominio {

    /**
     * Validar jugada boolean.
     *
     * @param carta the carta
     * @return the boolean
     */
    boolean validarJugada(Carta carta);
}
