package dto;

public class ConfiguracionPartidaDTO {
    private int valorMinimo;
    private int valorMaximo;
    private int cantidadComodines;
    private int cantidadCartasAccion;
    private float tiempoMaximoRuleta;

    public ConfiguracionPartidaDTO() {}

    public ConfiguracionPartidaDTO(int valorMinimo, int valorMaximo, int cantidadComodines,
                                    int cantidadCartasAccion, float tiempoMaximoRuleta) {
        this.valorMinimo = valorMinimo;
        this.valorMaximo = valorMaximo;
        this.cantidadComodines = cantidadComodines;
        this.cantidadCartasAccion = cantidadCartasAccion;
        this.tiempoMaximoRuleta = tiempoMaximoRuleta;
    }

    public int getValorMinimo() { return valorMinimo; }
    public void setValorMinimo(int valorMinimo) { this.valorMinimo = valorMinimo; }

    public int getValorMaximo() { return valorMaximo; }
    public void setValorMaximo(int valorMaximo) { this.valorMaximo = valorMaximo; }

    public int getCantidadComodines() { return cantidadComodines; }
    public void setCantidadComodines(int cantidadComodines) { this.cantidadComodines = cantidadComodines; }

    public int getCantidadCartasAccion() { return cantidadCartasAccion; }
    public void setCantidadCartasAccion(int cantidadCartasAccion) { this.cantidadCartasAccion = cantidadCartasAccion; }

    public float getTiempoMaximoRuleta() { return tiempoMaximoRuleta; }
    public void setTiempoMaximoRuleta(float tiempoMaximoRuleta) { this.tiempoMaximoRuleta = tiempoMaximoRuleta; }
}
