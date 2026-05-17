/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.util.List;

/**
 *
 * @author garfi
 */
public class EventoFinalizacionDTO {
    
    private List<JugadorDTO> posiciones;
    private boolean resultadoVotacion;

    public EventoFinalizacionDTO(List<JugadorDTO> posiciones) {
        this.posiciones = posiciones;
    }

    public EventoFinalizacionDTO(boolean resultadoVotacion) {
        this.resultadoVotacion = resultadoVotacion;
    }

    public List<JugadorDTO> getPosiciones() {
        return posiciones;
    }

    public void setPosiciones(List<JugadorDTO> posiciones) {
        this.posiciones = posiciones;
    }

    public boolean isResultadoVotacion() {
        return resultadoVotacion;
    }

    public void setResultadoVotacion(boolean resultadoVotacion) {
        this.resultadoVotacion = resultadoVotacion;
    }
    
    
}
