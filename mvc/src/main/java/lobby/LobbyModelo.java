package lobby;

import dto.EstadoPartidaDTO;
import dto.JugadorDTO;
import dto.SolicitudUnionDTO;
import dto.TipoAccionDTO;
import interfaces.IDispatcher;
import interfaces.ISerializer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class LobbyModelo implements IModeloLobby, IModeloLobbyLectura, IModeloLobbyReceptor {

    private final List<ISuscriptorLobby> vistas = new ArrayList<>();
    private final IDispatcher dispatcher;
    private final ISerializer serializer;
    private final String ipServidor;
    private final int puertoServidor;
    private final int puertoEscucha;
    private final String ipLocal;

    private String estadoUnion = "INICIAL";
    private String mensajeError;
    private String resultadoSolicitud;
    private List<JugadorDTO> jugadoresEnSala = new ArrayList<>();
    private List<SolicitudUnionDTO> solicitudesPendientes = new ArrayList<>();
    private JugadorDTO jugadorLocal;
    private Runnable onPartidaIniciada;

    public LobbyModelo(IDispatcher dispatcher, ISerializer serializer,
                       String ipServidor, int puertoServidor,
                       int puertoEscucha, String ipLocal) {
        this.dispatcher = dispatcher;
        this.serializer = serializer;
        this.ipServidor = ipServidor;
        this.puertoServidor = puertoServidor;
        this.puertoEscucha = puertoEscucha;
        this.ipLocal = ipLocal;
    }

    public void suscribir(ISuscriptorLobby vista) {
        vistas.add(vista);
    }

    public void setOnPartidaIniciada(Runnable callback) {
        this.onPartidaIniciada = callback;
    }


    @Override
    public void aceptarSolicitud(String nombre) {
        JugadorDTO jugador = new JugadorDTO();
        jugador.setNombre(nombre);
        TipoAccionDTO accion = new TipoAccionDTO("ACEPTAR_SOLICITUD");
        accion.setJugadorDTO(jugador);
        enviar(accion);
    }

    @Override
    public void rechazarSolicitud(String nombre) {
        JugadorDTO jugador = new JugadorDTO();
        jugador.setNombre(nombre);
        TipoAccionDTO accion = new TipoAccionDTO("RECHAZAR_SOLICITUD");
        accion.setJugadorDTO(jugador);
        enviar(accion);
    }

    @Override
    public void solicitarUnion(JugadorDTO jugador) {
        this.jugadorLocal = jugador;
        this.estadoUnion = "PENDIENTE";
        this.mensajeError = null;
        this.resultadoSolicitud = null;
        notificarVistas();

        TipoAccionDTO accion = new TipoAccionDTO("UNIRSE_PARTIDA");
        accion.setJugadorDTO(jugador);
        accion.setIp(ipLocal);
        accion.setPuerto(puertoEscucha);
        enviar(accion);
    }

    @Override
    public void confirmarInicio() {
        TipoAccionDTO accion = new TipoAccionDTO("CONFIRMAR_INICIO");
        accion.setJugadorDTO(jugadorLocal);
        enviar(accion);
    }

    public void procesarRespuesta(EstadoPartidaDTO estado) {
        if (estado == null) return;

        if (estado.getResultadoSolicitud() != null) {
            this.resultadoSolicitud = estado.getResultadoSolicitud();
            if ("ACEPTADA".equals(resultadoSolicitud)) {
                estadoUnion = "EN_SALA";
                jugadoresEnSala = estado.getJugadores() != null
                        ? estado.getJugadores() : new ArrayList<>();
            } else if ("RECHAZADA".equals(resultadoSolicitud)) {
                estadoUnion = "RECHAZADA";
                mensajeError = "Solicitud rechazada por el host.";
            }
            notificarVistas();
            return;
        }

        if (estado.getSolicitudesPendientes() != null) {
            solicitudesPendientes = estado.getSolicitudesPendientes();
            jugadoresEnSala = estado.getJugadores() != null
                    ? estado.getJugadores() : new ArrayList<>();
            estadoUnion = "EN_SALA";
            notificarVistas();
            return;
        }

        String estadoPartida = estado.getEstadoPartida();
        if ("EN_PROCESO".equals(estadoPartida) || "GIRO_PENDIENTE".equals(estadoPartida)
                || "SELECCION_COLOR_PENDIENTE".equals(estadoPartida)) {
            estadoUnion = "EN_JUEGO";
            notificarVistas();
            if (onPartidaIniciada != null) {
                SwingUtilities.invokeLater(onPartidaIniciada);
            }
        } else {
            estadoUnion = "EN_SALA";
            jugadoresEnSala = estado.getJugadores() != null
                    ? estado.getJugadores() : new ArrayList<>();
            notificarVistas();
        }
    }

    public boolean estaEnJuego() {
        return "EN_JUEGO".equals(estadoUnion);
    }

    @Override public String getEstadoUnion() { return estadoUnion; }
    @Override public String getMensajeError() { return mensajeError; }
    @Override public List<JugadorDTO> getJugadoresEnSala() { return jugadoresEnSala; }
    @Override public List<SolicitudUnionDTO> getSolicitudesPendientes() { return solicitudesPendientes; }

    private void notificarVistas() {
        for (ISuscriptorLobby v : vistas) {
            v.actualizar(this);
        }
    }

    private void enviar(TipoAccionDTO accion) {
        if (dispatcher == null || serializer == null) return;
        dispatcher.enviar(serializer.serialize(accion), puertoServidor, ipServidor);
    }
}
