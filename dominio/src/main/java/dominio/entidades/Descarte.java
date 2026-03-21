package dominio.entidades;

import dominio.entidades.enums.TipoCarta;

import java.util.List;
import java.util.Objects;

public class Descarte {

    private List<Carta> cartas;

    public Descarte() {}

    public Descarte(List<Carta> cartas) {
        this.cartas = cartas;
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(List<Carta> cartas) {
        this.cartas = cartas;
    }

    public Carta getUltimaCarta() {
        return cartas.getLast();
    }

    public boolean validarCartaEntrante(Carta entrada) {
        if (entrada == null) return false;
        if (entrada.esComodin()) return true;
        Carta cima = getUltimaCarta();

        if (Objects.equals(cima.getColor(), entrada.getColor())) return true;

        boolean cimaConNumero = cima.getNumero() != null;
        boolean entradaConNumero = entrada.getNumero() != null;
        if (cimaConNumero && entradaConNumero && Objects.equals(cima.getNumero(), entrada.getNumero())) return true;

        boolean entradaEsEspecial = entrada.getTipoCarta() != TipoCarta.NUMERICA && entrada.getTipoCarta() != TipoCarta.NUMERO_SPIN && !entrada.esComodin();
        return entradaEsEspecial && entrada.getTipoCarta() == cima.getTipoCarta();
    }
}