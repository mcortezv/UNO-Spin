package factory;
import dto.*;
import interfaces.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


        switch (accion) {
            case "HOST_UNIDO" -> enviar(crearEstadoSala(jugadores), puertoHost, ipHost);

            case "SOLICITUD_NUEVA" -> {
                EstadoPartidaDTO dto = crearEstadoSala(jugadores);
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

                EstadoPartidaDTO dtoHost = crearEstadoSala(jugadores);
                dtoHost.setSolicitudesPendientes(solicitudes);
                enviar(dtoHost, puertoHost, ipHost);

                broadcastExcepto(crearEstadoSala(jugadores), jugadores, nombre, host);
            }
            case "SOLICITUD_RECHAZADA" -> {
                String nombre = blackboard.getNombreSolicitudResuelta();
                String ip = blackboard.getIpJugador(nombre);
                int puerto = blackboard.getPuertoJugador(nombre);

                EstadoPartidaDTO dtoJugador = new EstadoPartidaDTO();
                dtoJugador.setResultadoSolicitud("RECHAZADA");
                enviar(dtoJugador, puerto, ip);

                EstadoPartidaDTO dtoHost = crearEstadoSala(jugadores);
                dtoHost.setSolicitudesPendientes(solicitudes);
                enviar(dtoHost, puertoHost, ipHost);
            }
            case "SOLICITAR_INICIO" -> {
                String solicitante = blackboard.getNombreSolicitudResuelta();
                EstadoPartidaDTO estado = new EstadoPartidaDTO();
                estado.setEstadoPartida("SOLICITUD_PENDIENTE");
                estado.setJugadores(jugadores);
                broadcastExcepto(estado, jugadores, solicitante);
            }
            case "NEGAR_INICIO" -> {
                String clienteSolicitudNegada = blackboard.getNombreSolicitudResuelta();
                EstadoPartidaDTO estado = new EstadoPartidaDTO();
                estado.setEstadoPartida("SOLICITUD_CANCELADA");
                estado.setJugadores(jugadores);
                broadcastExcepto(estado, jugadores, clienteSolicitudNegada);
            }
        }
    }

    private EstadoPartidaDTO crearEstadoSala(List<JugadorDTO> jugadores) {
        EstadoPartidaDTO dto = new EstadoPartidaDTO();
        dto.setEstadoPartida("NO_INICIADA");
        dto.setJugadores(jugadores);
        return dto;
    }

    private void broadcastExcepto(EstadoPartidaDTO dto, List<JugadorDTO> jugadores, String... excluidos) {
        Set<String> excluir = new HashSet<>(Arrays.asList(excluidos));
        for (JugadorDTO j : jugadores) {
            if (excluir.contains(j.getNombre())) continue;
            String ip = blackboard.getIpJugador(j.getNombre());
            int puerto = blackboard.getPuertoJugador(j.getNombre());
            if (ip == null) continue;
            enviar(dto, puerto, ip);
        }
    }

    private void broadcastEstado() {
        List<JugadorDTO> jugadores = blackboard.getJugadores();
        EventoAbandonoDTO eventoAbandono = blackboard.getEventoAbandono();
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
            enviar(buildEstadoPartida(i, eventoAbandono), puerto, ip);
        }
    }

    private EstadoPartidaDTO buildEstadoPartida(int indiceJugador, EventoAbandonoDTO eventoAbandono) {
        List<JugadorDTO> jugadores = blackboard.getJugadores();
        CartaDTO cartaCima = blackboard.getCartaCima();
        List<CartaDTO> mano = blackboard.getManoJugador(indiceJugador);
        TipoEventoRuletaDTO eventoRuleta = "GIRO_PENDIENTE".equals(blackboard.getEstadoPartida())
                ? blackboard.getEventoRuleta()
                : null;
         EstadoPartidaDTO dto = new EstadoPartidaDTO(
                blackboard.getIndiceJugadorActual(),
                blackboard.getEstadoPartida(),
                eventoAbandono,
                cartaCima,
                jugadores,
                mano,
                blackboard.getIndiceJugadorActual() == indiceJugador,
                eventoRuleta,
                blackboard.isUltimaJugadaValida());
        dto.setEventoFinalizacion(blackboard.getEventoFinalizacion());
    return dto;  
        
    }

    private void enviar(EstadoPartidaDTO dto, int puerto, String ip) {
        if (ip == null || puerto == 0) return;
        dispatcher.enviar(serializer.serialize(dto), puerto, ip);
    }
}
