package mappers;

import dominio.entidades.Jugador;
import dominio.entidades.Mano;
import dto.JugadorDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Jugador mapper.
 */
public class JugadorMapper {

    // Separador usado por UICrearJugador para empaquetar los 4 colores en el nombre
    private static final String SEP = "\u0000";

    /**
     * To dto jugador dto.
     */
    public static JugadorDTO toDTO(Jugador jugador, boolean esTurnoActual) {
        int colorId = colorListaAId(jugador.getColorCarta());
        return new JugadorDTO(
                jugador.getNombre(),
                jugador.getNumeroAvatar(),
                colorId,
                jugador.getMano().getCartas().size(),
                esTurnoActual
        );
    }

    /**
     * To dto list.
     */
    public static List<JugadorDTO> toDTO(List<Jugador> jugadores, int indiceActual) {
        List<JugadorDTO> lista = new ArrayList<>();
        if (jugadores != null) {
            for (int i = 0; i < jugadores.size(); i++) {
                lista.add(toDTO(jugadores.get(i), i == indiceActual));
            }
        }
        return lista;
    }

    /**
     * To entity jugador.
     * Si el nombre viene con el separador "\u0000", extrae el nombre real y la
     * lista de 4 colores que codificó UICrearJugador.
     */
    public static Jugador toEntity(JugadorDTO dto) {
        if (dto == null) return null;

        String nombreRaw = dto.getNombre() == null ? "" : dto.getNombre();
        String nombreReal;
        List<String> listaColor = new ArrayList<>();

        if (nombreRaw.contains(SEP)) {
            // Formato: "NombreJugador\u0000AZUL,ROJO,VERDE,AMARILLO"
            String[] partes = nombreRaw.split(SEP, 2);
            nombreReal = partes[0];
            for (String c : partes[1].split(",")) {
                String color = c.trim().toUpperCase();
                if (!color.isEmpty()) listaColor.add(color);
            }
        } else {
            // Sin colores extra: usar el colorCartas del DTO
            nombreReal = nombreRaw;
            listaColor.add(idAColorNombre(dto.getColorCartas()));
        }

        // Si por algún motivo quedaron menos de 4, rellenar con AZUL
        while (listaColor.size() < 4) listaColor.add("AZUL");

        return new Jugador(
                nombreReal,
                new Mano(new ArrayList<>()),
                listaColor,
                0,
                dto.getNumeroAvatar()
        );
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    /** id 1-8 → nombre de dominio */
    private static String idAColorNombre(int id) {
        return switch (id) {
            case 1 -> "AZUL";
            case 2 -> "ROJO";
            case 3 -> "VERDE";
            case 4 -> "AMARILLO";
            case 5 -> "MORADO";
            case 6 -> "NARANJA";
            case 7 -> "ROSA";
            case 8 -> "CIAN";
            default -> "AZUL";
        };
    }

    /** primer color de la lista → id 1-8 */
    private static int colorListaAId(List<String> colores) {
        if (colores == null || colores.isEmpty()) return 1;
        return switch (colores.get(0).toUpperCase().trim()) {
            case "AZUL"     -> 1;
            case "ROJO"     -> 2;
            case "VERDE"    -> 3;
            case "AMARILLO" -> 4;
            case "MORADO"   -> 5;
            case "NARANJA"  -> 6;
            case "ROSA"     -> 7;
            case "CIAN"     -> 8;
            default         -> 1;
        };
    }
}