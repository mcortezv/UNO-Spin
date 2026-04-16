package dominio;

/**
 * The type Configuracion partida.
 */
public class ConfiguracionPartida {
    private int valorMinimo;
    private int valorMaximo;
    private int cantidadComodines;
    private int cantidadCartasAccion;
    private float tiempoMaximoRuleta;

    /**
     * Instantiates a new Configuracion partida.
     */
    public ConfiguracionPartida() {
    }

    /**
     * Gets valor minimo.
     *
     * @return the valor minimo
     */
    public int getValorMinimo() {
        return valorMinimo;
    }

    /**
     * Sets valor minimo.
     *
     * @param valorMinimo the valor minimo
     */
    public void setValorMinimo(int valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    /**
     * Gets valor maximo.
     *
     * @return the valor maximo
     */
    public int getValorMaximo() {
        return valorMaximo;
    }

    /**
     * Sets valor maximo.
     *
     * @param valorMaximo the valor maximo
     */
    public void setValorMaximo(int valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    /**
     * Gets cantidad comodines.
     *
     * @return the cantidad comodines
     */
    public int getCantidadComodines() {
        return cantidadComodines;
    }

    /**
     * Sets cantidad comodines.
     *
     * @param cantidadComodines the cantidad comodines
     */
    public void setCantidadComodines(int cantidadComodines) {
        this.cantidadComodines = cantidadComodines;
    }

    /**
     * Gets cantidad cartas accion.
     *
     * @return the cantidad cartas accion
     */
    public int getCantidadCartasAccion() {
        return cantidadCartasAccion;
    }

    /**
     * Sets cantidad cartas accion.
     *
     * @param cantidadCartasAccion the cantidad cartas accion
     */
    public void setCantidadCartasAccion(int cantidadCartasAccion) {
        this.cantidadCartasAccion = cantidadCartasAccion;
    }

    /**
     * Gets tiempo maximo ruleta.
     *
     * @return the tiempo maximo ruleta
     */
    public float getTiempoMaximoRuleta() {
        return tiempoMaximoRuleta;
    }

    /**
     * Sets tiempo maximo ruleta.
     *
     * @param tiempoMaximoRuleta the tiempo maximo ruleta
     */
    public void setTiempoMaximoRuleta(float tiempoMaximoRuleta) {
        this.tiempoMaximoRuleta = tiempoMaximoRuleta;
    }
}
