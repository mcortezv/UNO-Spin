package dto;

/**
 * The type Configuracion partida dto.
 */
public class ConfiguracionPartidaDTO {
    private int cartasIniciales;
    private int valorMinimo;
    private int valorMaximo;
    private int cantidadComodines;
    private int cantidadCartasAccion;
    private float tiempoMaximoRuleta;
    private int valorCartaAccion;
    private int valorCartaComodin;
    private int numeroSpinPorColor;

    /**
     * Instantiates a new Configuracion partida dto.
     */
    public ConfiguracionPartidaDTO() {}

    /**
     * Instantiates a new Configuracion partida dto.
     *
     * @param valorMinimo          the valor minimo
     * @param valorMaximo          the valor maximo
     * @param cantidadComodines    the cantidad comodines
     * @param cantidadCartasAccion the cantidad cartas accion
     * @param tiempoMaximoRuleta   the tiempo maximo ruleta
     */
    public ConfiguracionPartidaDTO(int valorMinimo, int valorMaximo, int cantidadComodines,
                                    int cantidadCartasAccion, float tiempoMaximoRuleta, int cartasIniciales) {
        this.valorMinimo = valorMinimo;
        this.valorMaximo = valorMaximo;
        this.cantidadComodines = cantidadComodines;
        this.cantidadCartasAccion = cantidadCartasAccion;
        this.tiempoMaximoRuleta = tiempoMaximoRuleta;
        this.cartasIniciales= cartasIniciales;
    }

    /**
     * Gets valor minimo.
     *
     * @return the valor minimo
     */
    public int getValorMinimo() { return valorMinimo; }

    /**
     * Sets valor minimo.
     *
     * @param valorMinimo the valor minimo
     */
    public void setValorMinimo(int valorMinimo) { this.valorMinimo = valorMinimo; }

    /**
     * Gets valor maximo.
     *
     * @return the valor maximo
     */
    public int getValorMaximo() { return valorMaximo; }

    /**
     * Sets valor maximo.
     *
     * @param valorMaximo the valor maximo
     */
    public void setValorMaximo(int valorMaximo) { this.valorMaximo = valorMaximo; }

    /**
     * Gets cantidad comodines.
     *
     * @return the cantidad comodines
     */
    public int getCantidadComodines() { return cantidadComodines; }

    /**
     * Sets cantidad comodines.
     *
     * @param cantidadComodines the cantidad comodines
     */
    public void setCantidadComodines(int cantidadComodines) { this.cantidadComodines = cantidadComodines; }

    /**
     * Gets cantidad cartas accion.
     *
     * @return the cantidad cartas accion
     */
    public int getCantidadCartasAccion() { return cantidadCartasAccion; }

    /**
     * Sets cantidad cartas accion.
     *
     * @param cantidadCartasAccion the cantidad cartas accion
     */
    public void setCantidadCartasAccion(int cantidadCartasAccion) { this.cantidadCartasAccion = cantidadCartasAccion; }

    /**
     * Gets tiempo maximo ruleta.
     *
     * @return the tiempo maximo ruleta
     */
    public float getTiempoMaximoRuleta() { return tiempoMaximoRuleta; }

    /**
     * Sets tiempo maximo ruleta.
     *
     * @param tiempoMaximoRuleta the tiempo maximo ruleta
     */
    public void setTiempoMaximoRuleta(float tiempoMaximoRuleta) { this.tiempoMaximoRuleta = tiempoMaximoRuleta; }

    public int getCartasIniciales() {
        return cartasIniciales;
    }

    public void setCartasIniciales(int cartasIniciales) {
        this.cartasIniciales = cartasIniciales;
    }

    public int getValorCartaAccion() {
        return valorCartaAccion;
    }

    public void setValorCartaAccion(int valorCartaAccion) {
        this.valorCartaAccion = valorCartaAccion;
    }

    public int getValorCartaComodin() {
        return valorCartaComodin;
    }

    public void setValorCartaComodin(int valorCartaComodin) {
        this.valorCartaComodin = valorCartaComodin;
    }

    public int getNumeroSpinPorColor() {
        return numeroSpinPorColor;
    }

    public void setNumeroSpinPorColor(int numeroSpinPorColor) {
        this.numeroSpinPorColor = numeroSpinPorColor;
    }
}
