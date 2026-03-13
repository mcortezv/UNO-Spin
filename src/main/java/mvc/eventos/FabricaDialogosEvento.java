package mvc.eventos;

import dominio.enums.TipoEventoRuleta;
import mvc.eventos.eventosRuleta.*;
import mvc.interfaces.IModeloLectura;
import dto.JugadorDTO;
import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class FabricaDialogosEvento {

    public static DialogoEventoRuleta crear(TipoEventoRuleta evento,
                                            JFrame owner,
                                            IModeloLectura modelo) {

        List<String> nombres = modelo.getJugadoresRivales().stream()
                .map(JugadorDTO::getNombre)
                .collect(Collectors.toList());

        return switch (evento) {
            case CASI_UNO ->
                    new DialogoCasiUno(owner, modelo.getManoJugador());

            case DESCARTAR_POR_COLOR ->
                    new DialogoDescartarPorColor(owner);

            case ROBAR_HASTA_ROJO ->
                    new DialogoRobarHastaRojo(owner);

            case ROBAR_HASTA_AZUL ->
                    new DialogoRobarHastaAzul(owner);

            case GUERRA ->
                    new DialogoGuerra(owner, nombres);

            case MOSTRAR_LA_MANO ->
                    new DialogoMostrarMano(owner,
                            modelo.getNombreTurnoActual(),
                            modelo.getManoJugador());

            case INTERCAMBIO_DE_MANOS ->
                    new DialogoIntercambioDeManos(owner);

            case PUNTUACION_MAS_BAJA ->
                    new DialogoPuntuacionMasBaja(owner, nombres);

            case DESCARTAR_POR_NUMERO ->
                    new DialogoDescartarPorNumero(owner);

            case ELEGIR_COLOR ->
                    new DialogoElegirColor(owner);

            case DESCARTAR_CARTA ->
                    new DialogoDescartarCarta(owner, modelo.getManoJugador());
        };
    }
}