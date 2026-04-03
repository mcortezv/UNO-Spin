/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 * The type Evento ruleta dto.
 *
 * @author janethcristinagalvanquinonez
 */
public class EventoRuletaDTO {
    
    private String nombre;

    /**
     * Instantiates a new Evento ruleta dto.
     */
    public EventoRuletaDTO() {
    }

    /**
     * Instantiates a new Evento ruleta dto.
     *
     * @param nombre the nombre
     */
    public EventoRuletaDTO(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Gets nombre.
     *
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets nombre.
     *
     * @param nombre the nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
