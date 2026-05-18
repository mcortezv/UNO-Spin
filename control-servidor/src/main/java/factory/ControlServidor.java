package factory;
import interfaces.*;
import dto.CartaDTO;
import dto.EstadoPartidaDTO;
import dto.JugadorDTO;
import dto.SolicitudUnionDTO;
import dto.TipoEventoRuletaDTO;
import java.util.List;

/**
 * The type Control servidor.
 */
public class ControlServidor implements IBlackboardObservador {
    private IBlackboard blackboard;
    private final IDispatcher dispatcher;
    private final ISerializer serializer;

    /**
     * Instantiates a new Control servidor.
     *
     * @param blackboard the blackboard
     * @param dispatcher the dispatcher
     * @param serializer the serializer
     */
    ControlServidor(IBlackboard blackboard, IDispatcher dispatcher, ISerializer serializer) {
        this.blackboard = blackboard;
        this.dispatcher = dispatcher;
        this.serializer = serializer;
    }

    @Override
    public void update(IBlackboard blackboard) {
        this.blackboard = blackboard;
        String estado = blackboard.getEstadoPartida();
        if (estado == null || "NO_INICIADA".equals(estado)) {
            manejarFaseLobby();
        } else {
            broadcastEstado();
        }
    }

    private void manejarFaseLobby() {
        String accion = blackboard.getUltimaAccionLobby();
        if (accion == null) return;

        String host = blackboard.getHostNombre();
        if (host == null) return;
        String ipHost = blackboard.getIpJugador(host);
        int puertoHost = blackboard.getPuertoJugador(host);
        List<JugadorDTO> jugadores = blackboard.getJugadores();
        List<SolicitudUnionDTO> solicitudes = blackboard.getSolicitudesPendientes();

        System.out.println("[CS] lobby accion=" + accion + " host=" + host);

        switch (accion) {
            case "HOST_UNIDO" -> {
                EstadoPartidaDTO dto = new EstadoPartidaDTO();
                dto.setEstadoPartida("NO_INICIADA");
                dto.setJugadores(jugadores);
                enviar(dto, puertoHost, ipHost);
            }
            case "SOLICITUD_NUEVA" -> {
                EstadoPartidaDTO dto = new EstadoPartidaDTO();
                dto.setEstadoPartida("NO_INICIADA");
                dto.setJugadores(jugadores);
                dto.setSolicitudesPendientes(solicitudes);
                enviar(dto, puertoHost, ipHost);
            }
            case "SOLICITUD_ACEPTADA" -> {
                String nombre = blackboard.getNombreSolicitudResuelta();
                String ip = blackboard.getIpJugador(nombre);
                int puerto = blackboard.getPuertoJugador(nombre);

                EstadoPartidaDTO dtoNuevo = new EstadoPartidaDTO();
                dtoNuevo.setResultadoSolicitud("ACEPTADA");
                dtoNuevo.setJugadores(jugadores);
                enviar(dtoNuevo, puerto, ip);

                EstadoPartidaDTO dtoHost2 = new EstadoPartidaDTO();
                dtoHost2.setEstadoPartida("NO_INICIADA");
                dtoHost2.setJugadores(jugadores);
                dtoHost2.setSolicitudesPendientes(solicitudes);
                enviar(dtoHost2, puertoHost, ipHost);

                EstadoPartidaDTO dtoSala = new EstadoPartidaDTO();
                dtoSala.setEstadoPartida("NO_INICIADA");
                dtoSala.setJugadores(jugadores);
                for (JugadorDTO j : jugadores) {
                    if (j.getNombre().equals(nombre)) continue;
                    if (j.getNombre().equals(host)) continue;
                    enviar(dtoSala, blackboard.getPuertoJugador(j.getNombre()),
                            blackboard.getIpJugador(j.getNombre()));
                }
            }
            case "SOLICITUD_RECHAZADA" -> {
                String nombre = blackboard.getNombreSolicitudResuelta();
                String ip = blackboard.getIpJugador(nombre);
                int puerto = blackboard.getPuertoJugador(nombre);
                EstadoPartidaDTO dtoJugador = new EstadoPartidaDTO();
                dtoJugador.setResultadoSolicitud("RECHAZADA");
                enviar(dtoJugador, puerto, ip);
                EstadoPartidaDTO dtoHost = new EstadoPartidaDTO();
                dtoHost.setEstadoPartida("NO_INICIADA");
                dtoHost.setJugadores(jugadores);
                dtoHost.setSolicitudesPendientes(solicitudes);
                enviar(dtoHost, puertoHost, ipHost);
            }
            
            case "SOLICITAR_INICIO" -> {
                String jugadorSolicitante= blackboard.getNombreSolicitudResuelta();
                for(JugadorDTO jugador: jugadores){

                    if (jugador.getNombre().equals(jugadorSolicitante)) continue;
                    String ip= blackboard.getIpJugador(jugador.getNombre());
                    int puerto= blackboard.getPuertoJugador(jugador.getNombre());
                    if (ip == null) continue; 
                    
                    EstadoPartidaDTO estado= new EstadoPartidaDTO();
                    estado.setEstadoPartida("SOLICITUD_PENDIENTE");
                    estado.setJugadores(jugadores);
                    enviar(estado, puerto, ip);
                }
            }

            case "NEGAR_INICIO" ->{
                for(JugadorDTO jugador: jugadores){
                    String ip= blackboard.getIpJugador(jugador.getNombre());
                    int puerto= blackboard.getPuertoJugador(jugador.getNombre());
                    if (ip == null) continue;

                    EstadoPartidaDTO estado= new EstadoPartidaDTO();
                    estado.setEstadoPartida("SOLICITUD_CANCELADA");
                    estado.setJugadores(jugadores);
                    enviar(estado, puerto, ip);

                }
            }

        }
    }

    private void broadcastEstado() {
        List<JugadorDTO> jugadores = blackboard.getJugadores();
        System.out.println("[CS] broadcastEstado jugadores=" + jugadores.size());
        for (int i = 0; i < jugadores.size(); i++) {
            JugadorDTO jugador = jugadores.get(i);
            String ip = blackboard.getIpJugador(jugador.getNombre());
            int puerto = blackboard.getPuertoJugador(jugador.getNombre());
            if (ip == null) {
                System.out.println("[CS] SIN SOCKET para jugador=" + jugador.getNombre());
                continue;
            }
            System.out.println("[CS] Enviando estado[" + i + "] a " + jugador.getNombre() + " -> " + ip + ":" + puerto);
            enviar(buildEstadoPartida(i), puerto, ip);
        }
    }

    private EstadoPartidaDTO buildEstadoPartida(int indiceJugador) {
        List<JugadorDTO> jugadores = blackboard.getJugadores();
        CartaDTO cartaCima = blackboard.getCartaCima();
        List<CartaDTO> mano = blackboard.getManoJugador(indiceJugador);
        TipoEventoRuletaDTO eventoRuleta = "GIRO_PENDIENTE".equals(blackboard.getEstadoPartida())
                ? blackboard.getEventoRuleta()
                : null;
        return new EstadoPartidaDTO(
                blackboard.getIndiceJugadorActual(),
                blackboard.getEstadoPartida(),
                cartaCima,
                jugadores,
                mano,
                blackboard.getIndiceJugadorActual() == indiceJugador,
                eventoRuleta,
                blackboard.isUltimaJugadaValida());
    }

    private void enviar(EstadoPartidaDTO dto, int puerto, String ip) {
        if (ip == null || puerto == 0) return;
        dispatcher.enviar(serializer.serialize(dto), puerto, ip);
    }
}
