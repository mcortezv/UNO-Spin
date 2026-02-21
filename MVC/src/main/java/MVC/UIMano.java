package MVC;
import domain.Jugador;
import domain.Partida;
import domain.Tablero;
import dto.CartaDTO;
import java.util.List;

public class UIMano {
    private List<CartaDTO> cartas;

    public UIMano(List<CartaDTO> cartas) {
        this.cartas = cartas;
    }
}
