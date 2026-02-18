package domain;

public class ConfiguracionPartida {
    private int valorMinimoCarta;
    private int valorMaximoCarta;
    private int cantidadComodines;
    private int cantidadCartasAccion;
    private int tiempoMaximoRuleta;

    public ConfiguracionPartida() {}

    public int getValorMinimoCarta() {
        return valorMinimoCarta;
    }

    public void setValorMinimoCarta(int valorMinimoCarta) {
        this.valorMinimoCarta = valorMinimoCarta;
    }

    public int getValorMaximoCarta() {
        return valorMaximoCarta;
    }

    public void setValorMaximoCarta(int valorMaximoCarta) {
        this.valorMaximoCarta = valorMaximoCarta;
    }

    public int getCantidadComodines() {
        return cantidadComodines;
    }

    public void setCantidadComodines(int cantidadComodines) {
        this.cantidadComodines = cantidadComodines;
    }

    public int getCantidadCartasAccion() {
        return cantidadCartasAccion;
    }

    public void setCantidadCartasAccion(int cantidadCartasAccion) {
        this.cantidadCartasAccion = cantidadCartasAccion;
    }

    public int getTiempoMaximoRuleta() {
        return tiempoMaximoRuleta;
    }

    public void setTiempoMaximoRuleta(int tiempoMaximoRuleta) {
        this.tiempoMaximoRuleta = tiempoMaximoRuleta;
    }
}
