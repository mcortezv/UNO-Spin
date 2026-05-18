package mappers;
import dominio.entidades.Carta;
import dominio.entidades.enums.TipoCarta;
import dto.CartaDTO;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CartaMapperTest {


    @Test
    void toDtoNumerica() {
        Carta carta = new Carta("ROJO", 5, TipoCarta.NUMERICA, 5);
        CartaDTO dto = CartaMapper.toDTO(carta);

        assertEquals("ROJO", dto.getColor());
        assertEquals("NUMERICA", dto.getTipoCarta());
        assertEquals(5, dto.getNumero());
        assertEquals("5", dto.getValor());
    }

    @Test
    void toDtoBloqueo() {
        Carta carta = new Carta("AZUL", null, TipoCarta.BLOQUEO, 20);
        CartaDTO dto = CartaMapper.toDTO(carta);

        assertEquals("AZUL", dto.getColor());
        assertEquals("BLOQUEO", dto.getTipoCarta());
        assertNull(dto.getNumero());
        assertEquals("20", dto.getValor());
    }

    @Test
    void toDtoReversa() {
        Carta carta = new Carta("VERDE", null, TipoCarta.REVERSA, 20);
        CartaDTO dto = CartaMapper.toDTO(carta);

        assertEquals("REVERSA", dto.getTipoCarta());
        assertEquals("20", dto.getValor());
    }

    @Test
    void toDtoTomaDos() {
        Carta carta = new Carta("AMARILLO", null, TipoCarta.TOMA_DOS, 20);
        CartaDTO dto = CartaMapper.toDTO(carta);

        assertEquals("TOMA_DOS", dto.getTipoCarta());
        assertEquals("20", dto.getValor());
    }

    @Test
    void toDtoCambioColor() {
        Carta carta = new Carta(null, null, TipoCarta.CAMBIO_COLOR, 50);
        CartaDTO dto = CartaMapper.toDTO(carta);

        assertNull(dto.getColor());
        assertEquals("CAMBIO_COLOR", dto.getTipoCarta());
        assertEquals("50", dto.getValor());
    }

    @Test
    void toDtoTomaCuatro() {
        Carta carta = new Carta(null, null, TipoCarta.TOMA_CUATRO, 50);
        CartaDTO dto = CartaMapper.toDTO(carta);

        assertNull(dto.getColor());
        assertEquals("TOMA_CUATRO", dto.getTipoCarta());
        assertEquals("50", dto.getValor());
    }

    @Test
    void toDtoNumeroSpin() {
        Carta carta = new Carta("ROJO", 7, TipoCarta.NUMERO_SPIN, 7);
        CartaDTO dto = CartaMapper.toDTO(carta);

        assertEquals("NUMERO_SPIN", dto.getTipoCarta());
        assertEquals(7, dto.getNumero());
    }

    @Test
    void toDtoNull() {
        assertNull(CartaMapper.toDTO((Carta) null));
    }

    @Test
    void toDtoLista() {
        List<Carta> cartas = Arrays.asList(
                new Carta("ROJO", 1, TipoCarta.NUMERICA, 1),
                new Carta("AZUL", null, TipoCarta.BLOQUEO, 20)
        );
        List<CartaDTO> dtos = CartaMapper.toDTO(cartas);

        assertEquals(2, dtos.size());
        assertEquals("NUMERICA", dtos.get(0).getTipoCarta());
        assertEquals("BLOQUEO", dtos.get(1).getTipoCarta());
    }

    @Test
    void toDtoListaNull() {
        List<CartaDTO> dtos = CartaMapper.toDTO((List<Carta>) null);
        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }


    @Test
    void toEntityNumerica() {
        CartaDTO dto = new CartaDTO();
        dto.setColor("VERDE");
        dto.setTipoCarta("NUMERICA");
        dto.setNumero(3);
        dto.setValor("3");

        Carta carta = CartaMapper.toEntity(dto);

        assertEquals("VERDE", carta.getColor());
        assertEquals(TipoCarta.NUMERICA, carta.getTipoCarta());
        assertEquals(3, carta.getNumero());
        assertEquals(3, carta.getValor());
    }

    @Test
    void toEntityComodinSinColor() {
        CartaDTO dto = new CartaDTO();
        dto.setColor(null);
        dto.setTipoCarta("CAMBIO_COLOR");
        dto.setNumero(null);
        dto.setValor("50");

        Carta carta = CartaMapper.toEntity(dto);

        assertNull(carta.getColor());
        assertEquals(TipoCarta.CAMBIO_COLOR, carta.getTipoCarta());
        assertEquals(50, carta.getValor());
    }

    @Test
    void toEntityValorNullDefaulsACero() {
        CartaDTO dto = new CartaDTO();
        dto.setTipoCarta("BLOQUEO");
        dto.setColor("ROJO");
        dto.setValor(null);

        Carta carta = CartaMapper.toEntity(dto);

        assertEquals(0, carta.getValor());
    }

    @Test
    void toEntityValorBlankDefaulsACero() {
        CartaDTO dto = new CartaDTO();
        dto.setTipoCarta("BLOQUEO");
        dto.setColor("ROJO");
        dto.setValor("  ");

        Carta carta = CartaMapper.toEntity(dto);

        assertEquals(0, carta.getValor());
    }

    @Test
    void toEntityNull() {
        assertNull(CartaMapper.toEntity(null));
    }


    @Test
    void roundTripNumerica() {
        Carta original = new Carta("AMARILLO", 9, TipoCarta.NUMERICA, 9);
        Carta recuperada = CartaMapper.toEntity(CartaMapper.toDTO(original));

        assertEquals(original.getColor(), recuperada.getColor());
        assertEquals(original.getTipoCarta(), recuperada.getTipoCarta());
        assertEquals(original.getNumero(), recuperada.getNumero());
        assertEquals(original.getValor(), recuperada.getValor());
    }

    @Test
    void roundTripComodin() {
        Carta original = new Carta(null, null, TipoCarta.TOMA_CUATRO, 50);
        Carta recuperada = CartaMapper.toEntity(CartaMapper.toDTO(original));

        assertNull(recuperada.getColor());
        assertEquals(TipoCarta.TOMA_CUATRO, recuperada.getTipoCarta());
        assertEquals(50, recuperada.getValor());
    }

    @Test
    void roundTripNumeroSpin() {
        Carta original = new Carta("VERDE", 3, TipoCarta.NUMERO_SPIN, 3);
        Carta recuperada = CartaMapper.toEntity(CartaMapper.toDTO(original));

        assertEquals("VERDE", recuperada.getColor());
        assertEquals(TipoCarta.NUMERO_SPIN, recuperada.getTipoCarta());
        assertEquals(3, recuperada.getNumero());
    }
}
