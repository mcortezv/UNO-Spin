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
    private Boolean resultadoVotacion;
    private boolean votacionEnCurso = false;

    public EventoFinalizacionDTO() {
        this.posiciones = null;
        this.resultadoVotacion = Boolean.parseBoolean(null);
    }

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

    public Boolean isResultadoVotacion() {
        return resultadoVotacion;
    }

    public void setResultadoVotacion(boolean resultadoVotacion) {
        this.resultadoVotacion = resultadoVotacion;
    }

    public boolean isVotacionEnCurso() { return votacionEnCurso; }

    public void setVotacionEnCurso(boolean v) { this.votacionEnCurso = v; }
    
    
}
