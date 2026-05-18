package mvc;
import dto.TipoEventoRuletaDTO;
import dto.*;
import interfaces.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Modelo.
 */
public class Modelo implements IModeloControlador, IModeloLectura {
    private final List<ISuscriptor> suscriptores = new ArrayList<>();
    private final IDispatcher dispatcher;
    private final ISerializer serializer;
    private final String ipServidor;
    private final int puertoServidor;
    private final int puertoEscucha;
    private final String ipLocal;
    private String miNombre;
    private EstadoPartidaDTO estadoPartida;
    private int cartasPendientesCastigoLocal;
    private String ultimaCartaCimaProcesada;
    private boolean yaVote = false;
    private boolean votoEnviado = false;

    /**IReceptor
     * Instantiates a new Modelo.
     *
     * @param serializer     the serializer
     * @param dispatcher     the dispatcher
     * @param ipServidor     the ip servidor
     * @param puertoServidor the puerto servidor
     */
    public Modelo(ISerializer serializer, IDispatcher dispatcher, String ipServidor, int puertoServidor, int puertoEscucha, String ipLocal) {
        this.serializer = serializer;
        this.dispatcher = dispatcher;
        this.ipServidor = ipServidor;
        this.puertoServidor = puertoServidor;
        this.puertoEscucha = puertoEscucha;
        this.ipLocal = ipLocal;
    }

    /**
     * Actualizar estado.
     *
     * @param estado the estado
     */
    public void actualizarEstado(EstadoPartidaDTO estado) {
        this.estadoPartida = estado;
        EventoFinalizacionDTO evento = estado.getEventoFinalizacion();
        if (evento == null) {
            yaVote = false;
            votoEnviado = false;
        }
        procesarCastigoCartaCima();
        notifyObservers();
    }

    @Override
    public void unirsePartida(JugadorDTO jugador, ConfiguracionPartidaDTO configuracion) {
        this.miNombre = jugador != null ? jugador.getNombre() : null;
        TipoAccionDTO accion = new TipoAccionDTO("UNIRSE_PARTIDA");
        accion.setJugadorDTO(jugador);
        accion.setConfiguracion(configuracion);
        accion.setIp(ipLocal);
        accion.setPuerto(puertoEscucha);
        enviar(accion);
    }

    @Override
    public void confirmarInicio(JugadorDTO jugador) {
        TipoAccionDTO accion = new TipoAccionDTO("CONFIRMAR_INICIO");
        accion.setJugadorDTO(jugador);
        enviar(accion);
    }

    @Override
    public void jugarCarta(CartaDTO cartaDTO) {
        if (puedeJugarCarta(cartaDTO)) {
            if (estadoPartida != null) {
                estadoPartida.setUltimaJugadaValida(true);
            }
            enviar(new TipoAccionDTO("JUGAR_CARTA", cartaDTO));
        } else {
            if (estadoPartida != null) {
                estadoPartida.setUltimaJugadaValida(false);
            }
            notifyObservers();
        }
    }

    @Override
    public void pedirCarta() {
        if (tieneCastigoPendienteLocal()) {
            cartasPendientesCastigoLocal--;
            enviar(new TipoAccionDTO("PEDIR_CARTA_CASTIGO"));
            notifyObservers();
            return;
        }
        enviar(new TipoAccionDTO("PEDIR_CARTA"));
    }

    @Override
    public void girarRuleta() {
        if (estadoPartida != null && estadoPartida.isEsTuTurno()) {
            enviar(new TipoAccionDTO("GIRAR_RULETA"));
        }
    }

    @Override
    public void gritarUno() {
        enviar(new TipoAccionDTO("GRITAR_UNO"));
    }

    @Override
    public void limpiarEventoRuleta() {
        if (estadoPartida != null) {
            estadoPartida.setEventoRuletaActivo(null);
        }
        notifyObservers();
    }

    @Override
    public void reconocerEvento(int indiceJugador) {
        enviar(new TipoAccionDTO("RECONOCER_EVENTO"));
        limpiarEventoRuleta();
    }

    @Override
    public void aplicarSeleccionColor(String color) {
        CartaDTO payload = new CartaDTO();
        payload.setColor(color);
        enviar(new TipoAccionDTO("SELECCIONAR_COLOR", payload));
    }

    @Override
    public void aplicarEventoRuleta(String evento, Object resultado) {
        TipoAccionDTO accion = new TipoAccionDTO("RECONOCER_EVENTO");
        if (resultado != null) {
            accion.setResultadoEvento(resultado.toString());
        }
        enviar(accion);
    }

    @Override
    public boolean isUltimaJugadaValida() {
        return estadoPartida != null && estadoPartida.isUltimaJugadaValida();
    }

    @Override
    public List<CartaDTO> getDescarte() {
        List<CartaDTO> descarte = new ArrayList<>();
        if (getCartaCima() != null) {
            descarte.add(getCartaCima());
        }
        return descarte;
    }

    @Override
    public List<CartaDTO> getManoJugador() {
        return estadoPartida != null && estadoPartida.getManoJugador() != null
                ? estadoPartida.getManoJugador() : new ArrayList<>();
    }

    @Override
    public CartaDTO getCartaCima() {
        return estadoPartida != null ? estadoPartida.getCartaCima() : null;
    }

    @Override
    public String getNombreTurnoActual() {
        if (estadoPartida == null || estadoPartida.getJugadores() == null) {
            return "Esperando...";
        }
        int indice = estadoPartida.getIndiceJugadorActual();
        if (indice < 0 || indice >= estadoPartida.getJugadores().size()) {
            return "Esperando...";
        }
        return estadoPartida.getJugadores().get(indice).getNombre();
    }

    @Override
    public List<JugadorDTO> getJugadoresRivales() {
        if (miNombre == null) return getTodosLosJugadores();
        return getTodosLosJugadores().stream()
                .filter(j -> !miNombre.equals(j.getNombre()))
                .collect(Collectors.toList());
    }

    @Override
    public List<JugadorDTO> getTodosLosJugadores() {
        return estadoPartida != null && estadoPartida.getJugadores() != null
                ? estadoPartida.getJugadores() : new ArrayList<>();
    }

    @Override
    public boolean isTurnoActivo() {
        return estadoPartida != null && estadoPartida.isEsTuTurno()
                && "EN_PROCESO".equals(estadoPartida.getEstadoPartida());
    }

    @Override
    public boolean isSpinActivo() {
        return estadoPartida != null
                && "GIRO_PENDIENTE".equals(estadoPartida.getEstadoPartida())
                && getEventoRuletaActual() == null;
    }

    @Override
    public boolean isSeleccionColorPendiente() {
        return estadoPartida != null
                && "SELECCION_COLOR_PENDIENTE".equals(estadoPartida.getEstadoPartida());
    }

    @Override
    public boolean isSeleccionColorPropia() {
        return isSeleccionColorPendiente() && estadoPartida != null && estadoPartida.isEsTuTurno();
    }

    @Override
    public boolean isEventoRuletaPropio() {
        return getEventoRuletaActual() != null && estadoPartida != null && estadoPartida.isEsTuTurno();
    }

    @Override
    public TipoEventoRuletaDTO getEventoRuletaActual() {
        return estadoPartida != null ? estadoPartida.getEventoRuletaActivo() : null;
    }

    /**
     * Subscribe.
     *
     * @param suscriptor the suscriptor
     */
    public void subscribe(ISuscriptor suscriptor) {
        suscriptores.add(suscriptor);
    }

    /**
     * Unsubscribe.
     *
     * @param suscriptor the suscriptor
     */
    public void unsubscribe(ISuscriptor suscriptor) {
        suscriptores.remove(suscriptor);
    }

    private void notifyObservers() {
        for (ISuscriptor s : suscriptores) {
            s.update(this);
        }
    }

    private void enviar(TipoAccionDTO accion) {
        String json = serializer.serialize(accion);
        dispatcher.enviar(json, puertoServidor, ipServidor);
    }
    @Override
    public boolean cartaCimaEsCastigo() {
        CartaDTO cima = getCartaCima();
        if (cima == null) return false;

        String tipo = cima.getTipoCarta();
        return "TOMA_DOS".equals(tipo) || "TOMA_CUATRO".equals(tipo);
    }

    @Override
    public boolean tieneCastigoPendienteLocal() {
        return cartasPendientesCastigoLocal > 0;
    }

    @Override
    public int getCartasPendientesCastigoLocal() {
        return cartasPendientesCastigoLocal;
    }

    @Override
    public boolean puedeUsarMazo() {
        return isTurnoActivo();
    }

    @Override
    public boolean puedeIntentarJugarCarta() {
        return isTurnoActivo() && !tieneCastigoPendienteLocal();
    }

    @Override
    public boolean puedeJugarCarta(CartaDTO carta) {
        if (carta == null || !puedeIntentarJugarCarta()) {
            return false;
        }

        CartaDTO cima = getCartaCima();
        if (cima == null) return true; // Caso borde de inicio de juego

        String tipoSel = carta.getTipoCarta();
        if ("COMODIN".equals(tipoSel) || "TOMA_CUATRO".equals(tipoSel) || "CAMBIO_COLOR".equals(tipoSel)) {
            return true;
        }
        if (cima.getColor() != null && cima.getColor().equals(carta.getColor())) return true;
        if (cima.getValor() != null && cima.getValor().equals(carta.getValor())) return true;
        if (cima.getNumero() != null && cima.getNumero().equals(carta.getNumero())) return true;

        return false;
    }

    private void procesarCastigoCartaCima() {
        if (!isTurnoActivo() || !cartaCimaEsCastigo()) {
            return;
        }

        String claveCartaCima = claveCartaCimaActual();
        if (claveCartaCima == null || claveCartaCima.equals(ultimaCartaCimaProcesada)) {
            return;
        }

        String tipo = getCartaCima().getTipoCarta();
        cartasPendientesCastigoLocal = "TOMA_DOS".equals(tipo) ? 2 : 4;
        ultimaCartaCimaProcesada = claveCartaCima;
    }

    private String claveCartaCimaActual() {
        CartaDTO cima = getCartaCima();
        if (cima == null || estadoPartida == null) {
            return null;
        }
        return estadoPartida.getIndiceJugadorActual() + "|"
                + cima.getTipoCarta() + "|"
                + cima.getColor() + "|"
                + cima.getNumero() + "|"
                + cima.getValor();
    }

    @Override
    public void solicitarTerminarPartida() {
        yaVote = true;
        votoEnviado = true;
        TipoAccionDTO accion = new TipoAccionDTO("SOLICITAR_FINALIZAR");
        accion.setResultadoEvento("true");
        enviar(accion);
    }

    @Override
    public void enviarVotoTerminar(boolean acepta) {
        yaVote = acepta;
        votoEnviado = true;
        TipoAccionDTO accion = new TipoAccionDTO("SOLICITAR_FINALIZAR");
        accion.setResultadoEvento(String.valueOf(acepta));
        enviar(accion);
    }

    @Override
    public EventoFinalizacionDTO getEventoFinalizacion() {
        return estadoPartida != null ? estadoPartida.getEventoFinalizacion() : null;
    }

    @Override
    public boolean isVotacionPendiente() {
        if (estadoPartida == null) return false;
        EventoFinalizacionDTO evento = estadoPartida.getEventoFinalizacion();
        return evento != null
                && evento.getPosiciones() == null
                && Boolean.FALSE.equals(evento.isResultadoVotacion())
                && !yaVote;
    }

    @Override
    public boolean isVotoEnviado() { return votoEnviado; }

    public boolean isYaVote() { return yaVote; }
    
}
