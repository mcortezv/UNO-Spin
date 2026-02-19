package domain;

public class ConfirmacionInicio {
    private boolean acepto;

    public ConfirmacionInicio(boolean acepto) {
        this.acepto = acepto;
    }

    public boolean isAcepto() {
        return acepto;
    }

    public void setAcepto(boolean acepto) {
        this.acepto = acepto;
    }
}
