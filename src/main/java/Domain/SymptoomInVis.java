package Domain;

import java.sql.Timestamp;

public class SymptoomInVis extends Symptoom {
    private Timestamp eersteWaarneming;
    private int intensiteit;

    public SymptoomInVis(int symptoomId, String mediaUrls, String name, Timestamp eersteWaarneming, int intensiteit) {
        super(symptoomId, mediaUrls, name);
        this.eersteWaarneming = eersteWaarneming;
        this.intensiteit = intensiteit;
    }

    public Timestamp getEersteWaarneming() {
        return eersteWaarneming;
    }

    public int getIntensiteit() {
        return intensiteit;
    }
}
