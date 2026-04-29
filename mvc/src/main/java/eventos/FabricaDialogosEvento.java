package eventos;
import eventos.eventosRuleta.*;
import interfaces.IModeloLectura;
import dto.JugadorDTO;
import javax.swing.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FabricaDialogosEvento {

    public static DialogoEventoRuleta crear(String evento, JFrame owner, IModeloLectura modelo) {
        if (evento == null) {
            throw new IllegalArgumentException("El evento es nulo");
        }

        List<JugadorDTO> todosLosJugadores = modelo.getTodosLosJugadores();
        List<String> todosLosNombres = todosLosJugadores.stream()
                .map(JugadorDTO::getNombre)
                .collect(Collectors.toList());

        return switch (evento) {
            case "CASI_UNO" -> new DialogoCasiUno(owner, modelo.getManoJugador());
            case "DESCARTAR_POR_COLOR" -> new DialogoDescartarPorColor(owner);
            case "ROBAR_HASTA_ROJO" -> new DialogoRobarHastaRojo(owner);
            case "ROBAR_HASTA_AZUL" -> new DialogoRobarHastaAzul(owner);
            case "GUERRA" -> new DialogoGuerra(owner, todosLosNombres);
            case "MOSTRAR_LA_MANO" -> new DialogoMostrarMano(owner, modelo.getNombreTurnoActual(), modelo.getManoJugador());
            case "INTERCAMBIO_DE_MANOS" -> new DialogoIntercambioDeManos(owner);
            case "PUNTUACION_MAS_BAJA" -> new DialogoPuntuacionMasBaja(owner, todosLosNombres, Collections.nCopies(todosLosNombres.size(), 0));
            case "DESCARTAR_POR_NUMERO" -> new DialogoDescartarPorNumero(owner);
            default -> throw new IllegalStateException("Unexpected value: " + evento);
        };
    }
}
