package dominio.entidades;

import dominio.entidades.enums.TipoCarta;

import java.util.ArrayList;
import java.util.List;

public class Mano {

    private List<Carta> cartas;

    public Mano() {}

    public Mano(List<Carta> cartas) {
        this.cartas = cartas;
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(List<Carta> cartas) {
        this.cartas = cartas;
    }

    public boolean tieneCarta(Carta carta) {
        return cartas.contains(carta);
    }


    public Carta getMasAlta() {
        if (cartas == null || cartas.isEmpty()) return null;

        Carta cartaMasAlta = null;
        for (Carta carta : cartas) {
            if (carta.getTipoCarta() == TipoCarta.NUMERICA || carta.getTipoCarta() == TipoCarta.NUMERO_SPIN) {
                if (cartaMasAlta == null || carta.getNumero() > cartaMasAlta.getNumero()) {
                    cartaMasAlta = carta;
                }
            }
        }
        return cartaMasAlta != null ? cartaMasAlta : cartas.get(0);
    }

    public List<Carta> descartarCartasPorNumero(int numero) {
        List<Carta> aDescartar = new ArrayList<>();
        for (Carta carta : cartas) {
            if ((carta.getTipoCarta() == TipoCarta.NUMERICA || carta.getTipoCarta() == TipoCarta.NUMERO_SPIN)
                    && carta.getNumero() != null
                    && carta.getNumero() == numero) {
                aDescartar.add(carta);
            }
        }
        cartas.removeAll(aDescartar);
        return aDescartar;
    }
}