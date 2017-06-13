package Domain;

public class Analyse {
    private int ZiekteID;
    private String naamZiekte;
    private double Waarschijnlijkheid;

    public Analyse(int ZiekteID, String naamZiekte, double Waarschijnlijkheid) {
        this.ZiekteID = ZiekteID;
        this.naamZiekte = naamZiekte;
        this.Waarschijnlijkheid = Waarschijnlijkheid;
    }

    public int getZiekteID() {
        return ZiekteID;
    }

    public double getWaarschijnlijkheid() {
        return Waarschijnlijkheid;
    }

    public String getNaamZiekte() {
        return naamZiekte;
    }
}
