package dominio.entidades.enums;

/**
 * The enum Tipo accion.
 */
public enum TipoAccion {
    /**
     * Jugar carta tipo accion.
     */
    JUGAR_CARTA,
    /**
     * Pedir carta tipo accion.
     */
    PEDIR_CARTA,
    /**
     * Pedir carta por castigo sin avanzar turno tipo accion.
     */
    PEDIR_CARTA_CASTIGO,
    /**
     * Girar ruleta tipo accion.
     */
    GIRAR_RULETA,
    /**
     * Gritar uno tipo accion.
     */
    GRITAR_UNO,
    /**
     * Abandonar partida tipo accion.
     */
    ABANDONAR_PARTIDA,
    /**
     * Reconocer evento tipo accion.
     */
    RECONOCER_EVENTO,
    /**
     * Seleccionar color tipo accion.
     */
    SELECCIONAR_COLOR,
    /**
     * Unirse partida tipo accion.
     */
    UNIRSE_PARTIDA,
    /**
     * Confirmar inicio tipo accion.
     */
    SOLICITAR_INICIO,

    CONFIRMAR_INICIO,

    RECHAZAR_INICIO,

    CREAR_PARTIDA,

    ACEPTAR_SOLICITUD,

    RECHAZAR_SOLICITUD
}
