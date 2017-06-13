package Domain;

import java.sql.Timestamp;

/**
 * Created by Cas on 12-6-2017.
 */
public class Meter {
    private int aquariumId;
    private String naamApparaat;
    private Timestamp datumToevoeging;

    public Meter(int aquariumId, String naamApparaat, Timestamp datumToevoeging){
        this.aquariumId = aquariumId;
        this.naamApparaat = naamApparaat;
        this.datumToevoeging = datumToevoeging;
    }

    public int getAquariumId() {
        return aquariumId;
    }

    public void setAquariumId(int aquariumId) {
        this.aquariumId = aquariumId;
    }

    public String getNaamApparaat() {
        return naamApparaat;
    }

    public void setNaamApparaat(String naamApparaat) {
        this.naamApparaat = naamApparaat;
    }

    public Timestamp getDatumToevoeging() {
        return datumToevoeging;
    }

    public void setDatumToevoeging(Timestamp datumToevoeging) {
        this.datumToevoeging = datumToevoeging;
    }
}
