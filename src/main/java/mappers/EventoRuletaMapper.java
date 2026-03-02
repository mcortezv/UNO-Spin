/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import dominio.enums.TipoEventoRuleta;
import dto.EventoRuletaDTO;

/**
 *
 * @author janethcristinagalvanquinonez
 */
public class EventoRuletaMapper {
    
    public static EventoRuletaDTO toDTO(TipoEventoRuleta evento){
        if(evento == null){
            return null;
        }
        EventoRuletaDTO eventoDTO= new EventoRuletaDTO(evento.name());
        return eventoDTO;   
    }
    
}
