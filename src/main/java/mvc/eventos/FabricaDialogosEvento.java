package mvc.eventos;

import dominio.enums.TipoEventoRuleta;
import mvc.eventos.eventosRuleta.*;

import java.awt.*;

public class FabricaDialogosEvento {

    public static DialogoEventoRuleta crear(TipoEventoRuleta evento, Frame owner, Object datos) {
        return switch (evento) {
            case ELEGIR_COLOR        -> new DialogoElegirColor(owner);
            case DESCARTAR_POR_COLOR -> new DialogoDescartarPorColor(owner);
            case DESCARTAR_POR_NUMERO-> new DialogoDescartarPorNumero(owner);
            case ROBAR_HASTA_ROJO    -> new DialogoRobarHastaRojo(owner);
            case ROBAR_HASTA_AZUL    -> new DialogoRobarHastaAzul(owner);
            case CASI_UNO            -> new DialogoCasiUno(owner, null);
            case MOSTRAR_LA_MANO        -> new DialogoMostrarMano(owner, null, null);
            case DESCARTAR_CARTA     -> new DialogoDescartarCarta(owner, null);
            case GUERRA              -> new DialogoGuerra(owner,null,null);
            case PUNTUACION_MAS_BAJA     -> new DialogoPuntuacionMasBaja(owner, null,null);
            case INTERCAMBIO_DE_MANOS   -> new DialogoIntercambioDeManos(owner);
        };
    }
}
