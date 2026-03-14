package dominio.enums;

/**
 * The enum Tipo evento ruleta.
 */
public enum TipoEventoRuleta {
    /**
     * Casi uno tipo evento ruleta.
     *
     * El jugador que ha hecho girar la rueda deshace de todas las cartas menos
     * dos
     *
     */
    CASI_UNO,
    /**
     * Descartar por color tipo evento ruleta.
     *
     * el jugador elige un color y descarta todas las cartas de ese color
     * (también puede conservar algunas).
     */
    DESCARTAR_POR_COLOR,
    /**
     * Robar hasta azul tipo evento ruleta.
     *
     * roba cartas hasta que salga una azul (o comodín) y se queda con todas.
     */
    ROBAR_HASTA_AZUL,
    /**
     * Robar hasta rojo tipo evento ruleta.
     *
     * roba cartas hasta que salga una roja (o comodín) y se queda con todas.
     */
    ROBAR_HASTA_ROJO,
    /**
     * Guerra tipo evento ruleta.
     *
     * todos muestran su carta más alta. El que tenga la más alta la descarta;
     * los demás conservan las suyas. En caso de empate, se comparan las
     * siguientes cartas más altas. El ganador descarta todas las cartas jugadas
     * en la batalla.
     */
    GUERRA,
    /**
     * Mostrar la mano tipo evento ruleta.
     *
     * el jugador enseña sus cartas a los demás y el juego continúa normalmente.
     */
    MOSTRAR_LA_MANO,
    /**
     * Intercambio de manos tipo evento ruleta.
     *
     * todos pasan sus cartas al jugador de la izquierda. Si alguien recibe una
     * sola carta debe gritar “UNO”; si no lo hace, roba dos cartas.
     */
    INTERCAMBIO_DE_MANOS,
    /**
     * Puntuacion mas baja tipo evento ruleta.
     */
    PUNTUACION_MAS_BAJA
}
