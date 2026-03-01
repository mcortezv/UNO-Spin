package mvc.mock;
import dto.CartaDTO;
import dto.JugadorDTO;
import mappers.CartaMapper;
import mvc.interfaces.IModeloLectura;
import java.util.List;

public class ModeloVistaJugador implements IModeloLectura {
    private IModeloLectura modeloLectura;
    private int vistaJugador;

    public ModeloVistaJugador(int vistaJugador, IModeloLectura modeloLectura) {
        this.vistaJugador = vistaJugador;
        this.modeloLectura = modeloLectura;
    }

    @Override
    public List<CartaDTO> getDescarte() {
        return modeloLectura.getDescarte();
    }

    @Override
    public List<CartaDTO> getManoJugador() {
        return modeloLectura.getManoJugador();
    }

    @Override
    public JugadorDTO getJugadorEnTurno() {
        return modeloLectura.getJugadorEnTurno();
    }

    @Override
    public List<CartaDTO> getManoJugadorActual() {
        return CartaMapper.toDTO(modeloLectura.getPartidaMock().getJugadores().get(vistaJugador).getMano().getCartas());

    }

    @Override
    public CartaDTO getCartaCima() {
        return modeloLectura.getCartaCima();
    }

    @Override
    public String getNombreTurnoActual() {
        return modeloLectura.getNombreTurnoActual();
    }

    @Override
    public List<JugadorDTO> getJugadoresRivales() {
        List<JugadorDTO> listaJugadoresRivales = modeloLectura.getJugadoresRivales();
        if (vistaJugador == 0){
            return List.of(listaJugadoresRivales.get(1));

        } else {
            return List.of(listaJugadoresRivales.get(0));
        }
    }

    @Override
    public boolean isSpinActivo() {
        return false;
    }

    @Override
    public PartidaMock getPartidaMock(){
        return null;
    }
}
