/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author garfi
 */
public class VotoDTO {
    
    private int jugadorId;
    private boolean acepta;
    
    public VotoDTO() {
        
    }

    public VotoDTO(int jugadorId, boolean acepta) {
        this.jugadorId = jugadorId;
        this.acepta = acepta;
    }

    public int getJugadorId() {
        return jugadorId;
    }

    public void setJugadorId(int jugadorId) {
        this.jugadorId = jugadorId;
    }

    public boolean isAcepta() {
        return acepta;
    }

    public void setAcepta(boolean acepta) {
        this.acepta = acepta;
    }
    
    
    
}
