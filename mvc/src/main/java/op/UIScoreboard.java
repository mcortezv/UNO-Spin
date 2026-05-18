/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package op;

import dto.JugadorDTO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.*;

import styles.Button;

/**
 *
 * @author garfi
 */
public class UIScoreboard extends JFrame {
    private static final Color FONDO = new Color(160, 20, 20);
    private static final Color OVALO = new Color(130, 15, 15);
    private static final Color PODIO_COLOR = new Color(80, 10, 10);
    private static final Color ORO = new Color(255, 200, 0);
    private static final Color PLATA = new Color(200, 200, 200);
    private static final Color BRONCE = new Color(180, 100, 30);
    private static final Color CUARTO = new Color(150, 150, 150);

    private static final int ANCHO = 900;
    private static final int ALTO = 580;

    private final List<JugadorDTO> posiciones;

    public UIScoreboard(List<JugadorDTO> posiciones) {
        super("UNO - Fin de partida");
        this.posiciones = posiciones;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(ANCHO, ALTO);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                dibujarFondo(g2);
                dibujarPodio(g2);
            }
        };
        canvas.setLayout(null);
        canvas.setPreferredSize(new Dimension(ANCHO, ALTO));

        CargadorAssets assets = CargadorAssets.getInstance();
        Image logoImg = assets.getLogo() != null
                ? assets.getLogo().getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH)
                : null;
        if (logoImg != null) {
            JLabel lblLogo = new JLabel(new ImageIcon(logoImg));
            lblLogo.setBounds(ANCHO / 2 - 60, 10, 120, 80);
            canvas.add(lblLogo);
        }

        JLabel lblTitulo = new JLabel("SCOREBOARD", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitulo.setForeground(new Color(30, 30, 30));
        lblTitulo.setBackground(ORO);
        lblTitulo.setOpaque(true);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        int tituloW = 280, tituloH = 45;
        lblTitulo.setBounds(ANCHO / 2 - tituloW / 2, 95, tituloW, tituloH);
        canvas.add(lblTitulo);

        int[] xCentros = calcularXCentros();
        int[] alturasPodio = calcularAlturasPodio();
        int[] ordenVisual = calcularOrdenVisual();

        for (int slot = 0; slot < Math.min(posiciones.size(), 4); slot++) {
            int posReal = ordenVisual[slot]; 
            if (posReal >= posiciones.size()) {
                continue;
            }
            JugadorDTO jugador = posiciones.get(posReal);

            int cx = xCentros[slot];
            int altPodio = alturasPodio[slot];
            int yBase = ALTO - 120 - altPodio; 
            int avatarTam = 60;
            Image avatarImg = assets.getAvatarEscalado(jugador.getNumeroAvatar(), avatarTam);
            JLabel lblAvatar = new JLabel(new ImageIcon(avatarImg)) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, getWidth(), getHeight()));
                    super.paintComponent(g2);
                }
            };
            lblAvatar.setBounds(cx - avatarTam / 2, yBase - avatarTam - 5, avatarTam, avatarTam);
            canvas.add(lblAvatar);

            JLabel lblNombre = new JLabel(jugador.getNombre(), SwingConstants.CENTER);
            lblNombre.setFont(new Font("Arial", Font.BOLD, 11));
            lblNombre.setForeground(Color.WHITE);
            lblNombre.setBounds(cx - 55, yBase - avatarTam - 22, 110, 18);
            canvas.add(lblNombre);
        }

        JButton btnSalir = new JButton("SALIR");
        btnSalir.setBackground(ORO);
        btnSalir.setForeground(new Color(30, 30, 30));
        btnSalir.setFont(new Font("Arial", Font.BOLD, 16));
        btnSalir.setFocusPainted(false);
        btnSalir.setBorderPainted(false);
        btnSalir.setOpaque(true);
        int btnW = 160, btnH = 42;
        btnSalir.setBounds(ANCHO / 2 - btnW / 2, ALTO - 90, btnW, btnH);
        btnSalir.addActionListener(e -> System.exit(0));
        canvas.add(btnSalir);

        setContentPane(canvas);
    }

    private void dibujarFondo(Graphics2D g2) {
        g2.setColor(FONDO);
        g2.fillRect(0, 0, ANCHO, ALTO);
        g2.setColor(OVALO);
        g2.fillOval(-50, 60, ANCHO + 100, ALTO - 80);
    }

    private void dibujarPodio(Graphics2D g2) {
        int[] xCentros = calcularXCentros();
        int[] alturasPodio = calcularAlturasPodio();
        int[] ordenVisual = calcularOrdenVisual();
        int anchoColumna = 130;
        int yBase = ALTO - 120;

        for (int slot = 0; slot < Math.min(posiciones.size(), 4); slot++) {
            int cx = xCentros[slot];
            int altPodio = alturasPodio[slot];
            int x = cx - anchoColumna / 2;
            int y = yBase - altPodio;

            g2.setColor(PODIO_COLOR.darker());
            g2.fillRect(x + anchoColumna, y + 6, 8, altPodio);
            g2.setColor(PODIO_COLOR);
            g2.fillRect(x, y, anchoColumna, altPodio);

            int posReal = posiciones.get(ordenVisual[slot]).getCantidadCartas();
            String numStr = String.valueOf(posReal);
            Color medallaColor = switch (posReal) {
                case 1 -> ORO;
                case 2 -> PLATA;
                case 3 -> BRONCE;
                default -> CUARTO;
            };

            int hx = cx, hy = y + 25;
            int r = 22;
            int[] px = {hx, hx + r, hx + r, hx, hx - r, hx - r};
            int[] py = {hy - r, hy - r / 2, hy + r / 2, hy + r, hy + r / 2, hy - r / 2};
            g2.setColor(medallaColor);
            g2.fillPolygon(px, py, 6);
            g2.setColor(medallaColor.darker());
            g2.drawPolygon(px, py, 6);

            g2.setColor(new Color(30, 30, 30));
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(numStr, hx - fm.stringWidth(numStr) / 2, hy + fm.getAscent() / 2 - 2);
        }
        g2.setColor(PODIO_COLOR.darker());
        int lastX = xCentros[xCentros.length - 1];
        g2.fillRect(xCentros[0] - 65, yBase, lastX - xCentros[0] + 130, 20);
    }

    private int[] calcularOrdenVisual() {
        int n = Math.min(posiciones.size(), 4);
        if (n == 1) return new int[]{0};
        if (n == 2) return new int[]{1, 0};
        if (n == 3) return new int[]{1, 0, 2};
        return new int[]{1, 0, 2, 3};
    }


    private int[] calcularXCentros() {
        int n = Math.min(posiciones.size(), 4);
        int[] cx = new int[n];
        switch (n) {
            case 1 -> cx = new int[]{ANCHO / 2};
            case 2 -> cx = new int[]{ANCHO / 2 - 120, ANCHO / 2 + 120};
            case 3 -> cx = new int[]{ANCHO / 2 - 200, ANCHO / 2, ANCHO / 2 + 200};
            case 4 -> cx = new int[]{ANCHO / 2 - 300, ANCHO / 2 - 100, ANCHO / 2 + 100, ANCHO / 2 + 300};
        }
        return cx;
    }

    private int[] calcularAlturasPodio() {
        int n = Math.min(posiciones.size(), 4);
        int[] todasAlturas = {140, 200, 100, 70};
        int[] resultado = new int[n];
        for (int i = 0; i < n; i++) {
            resultado[i] = todasAlturas[i];
        }
        return resultado;
    }
}
