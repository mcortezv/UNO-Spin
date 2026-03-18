package mvc.eventos;

import dominio.enums.TipoEventoRuleta;
import mvc.eventos.eventosRuleta.*;
import mvc.interfaces.IModeloLectura;
import dto.CartaDTO;
import dto.JugadorDTO;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FabricaDialogosEvento {

    public static DialogoEventoRuleta crear(TipoEventoRuleta evento,
                                            JFrame owner,
                                            IModeloLectura modelo) {

        List<JugadorDTO> todosLosJugadores = modelo.getTodosLosJugadores();
        List<String> todosLosNombres = todosLosJugadores.stream()
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
                    new DialogoGuerra(owner, todosLosNombres);

            case MOSTRAR_LA_MANO ->
                    new DialogoMostrarMano(owner,
                            modelo.getNombreTurnoActual(),
                            modelo.getManoJugador());

            case INTERCAMBIO_DE_MANOS ->
                    new DialogoIntercambioDeManos(owner);

            case PUNTUACION_MAS_BAJA ->
                    new DialogoPuntuacionMasBaja(
                            owner,
                            todosLosNombres,
                            calcularPuntajes(todosLosJugadores, modelo));

            case DESCARTAR_POR_NUMERO ->
                    new DialogoDescartarPorNumero(owner);

            case ELEGIR_COLOR ->
                    new DialogoElegirColor(owner);

            case DESCARTAR_CARTA ->
                    new DialogoDescartarCarta(owner, modelo.getManoJugador());
        };
    }


    private static List<Integer> calcularPuntajes(List<JugadorDTO> jugadores, IModeloLectura modelo) {
        List<Integer> puntajes = new ArrayList<>();
        for (int i = 0; i < jugadores.size(); i++) {
            List<CartaDTO> mano = modelo.getManoJugadorEspecifico(i);
            int total = mano.stream()
                    .mapToInt(FabricaDialogosEvento::puntosCartaDTO)
                    .sum();
            puntajes.add(total);
        }
        return puntajes;
    }

    private static int puntosCartaDTO(CartaDTO carta) {
        if (carta.getTipoCarta() == null) return 0;
        return switch (carta.getTipoCarta().toUpperCase()) {
            case "NUMERICA", "NUMERO_SPIN" -> {
                try { yield Integer.parseInt(carta.getValor()); }
                catch (NumberFormatException e) { yield 0; }
            }
            case "TOMA_DOS", "REVERSA", "BLOQUEO" -> 20;
            case "TOMA_CUATRO", "CAMBIO_COLOR"     -> 50;
            default -> 0;
        };
    }
}