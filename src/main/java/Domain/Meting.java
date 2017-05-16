package Domain;

public class Meting {
    private String variabele;
    private String variabeleId;
    private int waarde;

    public Meting(String variabele, String variabeleId, int waarde) {
        this.variabele = variabele;
        this.variabeleId = variabeleId;
        this.waarde = waarde;
    }

    public String getVariabele() {
        return variabele;
    }

    public String getVariabeleId() {
        return variabeleId;
    }

    public int getWaarde() {
        return waarde;
    }
}
