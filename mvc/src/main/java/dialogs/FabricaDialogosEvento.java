package dialogs;

import dto.TipoEventoRuletaDTO;
import dialogs.eventosRuleta.*;
import interfaces.IModeloLectura;
import dto.JugadorDTO;

import javax.swing.*;
import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Fabrica dialogos evento.
 */
public class FabricaDialogosEvento {

    /**
     * Crear dialogo evento ruleta.
     *
     * @param evento the evento (Ahora usa el Enum)
     * @param owner  the owner
     * @param modelo the modelo
     * @return the dialogo evento ruleta
     */
    public static DialogoEventoRuleta crear(TipoEventoRuletaDTO evento, JFrame owner, IModeloLectura modelo) {
        if (evento == null) {
            throw new IllegalArgumentException("El evento es nulo");
        }

        return switch (evento) {
            case CASI_UNO -> new DialogoCasiUno(owner, modelo.getManoJugador());
            case DESCARTAR_POR_COLOR -> new DialogoDescartarPorColor(owner);
            case ROBAR_HASTA_ROJO -> new DialogoColorInformativo(owner, "¡ROBAR CARTAS HASTA ROJO!", Color.RED, "Rojo");
            case ROBAR_HASTA_AZUL -> new DialogoColorInformativo(owner, "¡ROBAR CARTAS HASTA AZUL!", new Color(0, 170, 255), "Azul");
            case GUERRA -> new DialogoGuerra(owner, extraerNombres(modelo));
            case PUNTUACION_MAS_BAJA -> new DialogoPuntuacionMasBaja(owner, extraerNombres(modelo), extraerPuntos(modelo));
            case MOSTRAR_LA_MANO -> new DialogoMostrarMano(owner, modelo.getNombreTurnoActual(), modelo.getManoJugador());
            case INTERCAMBIO_DE_MANOS -> new DialogoIntercambioDeManos(owner);
            case DESCARTAR_POR_NUMERO -> new DialogoDescartarPorNumero(owner);
        };
    }

    private static List<String> extraerNombres(IModeloLectura modelo) {
        return modelo.getTodosLosJugadores().stream()
                .map(JugadorDTO::getNombre)
                .collect(Collectors.toList());
    }

    private static List<Integer> extraerPuntos(IModeloLectura modelo) {
        return modelo.getTodosLosJugadores().stream()
                .map(JugadorDTO::getPuntos)
                .collect(Collectors.toList());
    }
}
