/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;
<<<<<<<< HEAD:control/src/main/java/mappers/EventoRuletaMapper.java
import dominio.enums.TipoEventoRuleta;
========

import dominio.entidades.enums.TipoEventoRuleta;
>>>>>>>> origin/dev:mappers/src/main/java/mappers/EventoRuletaMapper.java
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
        return new EventoRuletaDTO(evento.name());
    }

}
