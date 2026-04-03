package mvc;
import dto.AccionJuegoDTO;
import dto.JugadorDTO;
import dto.PartidaDTO;
import interfaces.IDispatcher;
import interfaces.ISerializer;
import mvc.interfaces.IModeloConexion;
import mvc.interfaces.IModeloControlador;
import mvc.interfaces.IModeloLectura;
import mvc.interfaces.ISuscriptor;
import dto.CartaDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Modelo.
 */
public class Modelo implements IModeloControlador, IModeloLectura, IModeloConexion {
    private final List<ISuscriptor> suscriptores = new ArrayList<>();
    private final IDispatcher dispatcher;
    private final ISerializer serializer;
    private List<CartaDTO> descarteLocal = new ArrayList<>();
    private List<CartaDTO> manoLocal = new ArrayList<>();
    private CartaDTO cartaCimaLocal;
    private String nombreTurnoActual = "";
    private List<JugadorDTO> rivalesLocales = new ArrayList<>();
    private boolean turnoActivo = false;
    private boolean spinActivo = false;

    /**
     * Instantiates a new Modelo.
     */
    public Modelo(IDispatcher dispatcher, ISerializer serializer) {
        this.dispatcher = dispatcher;
        this.serializer = serializer;
    }

    @Override
    public boolean jugarCarta(CartaDTO cartaDTO) {
        String json = serializer.serialize(new AccionJuegoDTO("JUGAR_CARTA", cartaDTO));
        this.dispatcher.enviar(json, 8080, "ip_del_servidor");
        return true;
    }


    @Override
    public void pedirCarta() {
        String json = serializer.serialize(new AccionJuegoDTO("PEDIR_CARTA", null));
        this.dispatcher.enviar(json, 8080, "ip_del_servidor");
    }

    @Override
    public void girarRuleta() {
        String json = serializer.serialize(new AccionJuegoDTO("GIRAR_RULETA", null));
        this.dispatcher.enviar(json, 8080, "ip_del_servidor");
    }

    @Override
    public void gritarUno() {
        String json = serializer.serialize(new AccionJuegoDTO("GRITAR_UNO", null));
        this.dispatcher.enviar(json, 8080, "ip_del_servidor");
    }


    @Override
    public List<CartaDTO> getDescarte() {
        return descarteLocal;
    }

    @Override
    public List<CartaDTO> getManoJugador() {
        return manoLocal;
    }

    @Override
    public CartaDTO getCartaCima() {
        return cartaCimaLocal;
    }

    @Override
    public String getNombreTurnoActual() {
        return nombreTurnoActual;
    }

    @Override
    public List<JugadorDTO> getJugadoresRivales() {
        return rivalesLocales;
    }

    @Override
    public boolean isTurnoActivo() {
        return turnoActivo;
    }

    @Override
    public boolean isSpinActivo() {
        return spinActivo;
    }

    /**
     * Subscribe.
     *
     * @param suscriptor the suscriptor
     */
    public void subscribe(ISuscriptor suscriptor) {
        this.suscriptores.add(suscriptor);
    }

    /**
     * Unsubscribe.
     *
     * @param suscriptor the suscriptor
     */
    public void unsubscribe(ISuscriptor suscriptor) {
        this.suscriptores.remove(suscriptor);
    }

    private void notifyObservers() {
        for (ISuscriptor suscriptor : suscriptores) {
            suscriptor.update(this);
        }
    }

    @Override
    public List<CartaDTO> getManoJugadorEspecifico(int indiceJugador) {
        return new ArrayList<>();
    }

    @Override
    public boolean isTurnoActivoEspecifico(int indiceJugador) {
        return false;
    }

    @Override
    public void enviar(String json, int port, String ip) {
        if (this.dispatcher != null) {
            this.dispatcher.enviar(json, port, ip);
        }
    }

    public void actualizarEstadoDesdeServidor(String jsonEstado) {
        PartidaDTO partidaActualizada = serializer.deserialize(jsonEstado, PartidaDTO.class);
        this.descarteLocal = partidaActualizada.getDescarte();
        this.manoLocal = partidaActualizada.getManoJugador();
        this.cartaCimaLocal = partidaActualizada.getCartaCima();
        this.nombreTurnoActual = partidaActualizada.getNombreTurnoActual();
        this.rivalesLocales = partidaActualizada.getJugadoresRivales();
        this.turnoActivo = partidaActualizada.isTurnoActivo();
        this.spinActivo = partidaActualizada.isSpinActivo();
        notifyObservers();
    }
}
