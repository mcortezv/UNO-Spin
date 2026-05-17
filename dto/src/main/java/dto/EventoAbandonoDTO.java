package dto;

/**
 * The type Evento abandono dto.
 */
public class EventoAbandonoDTO {
    private String nombreAbandono;
    private String nombreGanador;

    /**
     * Instantiates a new Evento abandono dto.
     */
    public EventoAbandonoDTO() {}

    /**
     * Instantiates a new Evento abandono dto.
     *
     * @param nombreAbandono the nombre abandono
     * @param nombreGanador  the nombre ganador
     */
    public EventoAbandonoDTO(String nombreAbandono, String nombreGanador) {
        this.nombreAbandono = nombreAbandono;
        this.nombreGanador = nombreGanador;
    }

    /**
     * Gets nombre abandono.
     *
     * @return the nombre abandono
     */
    public String getNombreAbandono() {
        return nombreAbandono;
    }

    /**
     * Sets nombre abandono.
     *
     * @param nombreAbandono the nombre abandono
     */
    public void setNombreAbandono(String nombreAbandono) {
        this.nombreAbandono = nombreAbandono;
    }

    /**
     * Gets nombre ganador.
     *
     * @return the nombre ganador
     */
    public String getNombreGanador() {
        return nombreGanador;
    }

    /**
     * Sets nombre ganador.
     *
     * @param nombreGanador the nombre ganador
     */
    public void setNombreGanador(String nombreGanador) {
        this.nombreGanador = nombreGanador;
    }
}
