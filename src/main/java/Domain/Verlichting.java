package Domain;

import java.sql.Timestamp;

/**
 * Created by Cas on 12-6-2017.
 */
public class Verlichting {
    private int aquariumId;
    private String naamApparaat;
    private Timestamp datumToevoeging;
    private String lichtsterkte;

    public Verlichting(int aquariumId, String naamApparaat, Timestamp datumToevoeging, String lichtsterkte) {
        this.aquariumId = aquariumId;
        this.naamApparaat = naamApparaat;
        this.datumToevoeging = datumToevoeging;
        this.lichtsterkte = lichtsterkte;
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

    public String getLichtsterkte() {
        return lichtsterkte;
    }

    public void setLichtsterkte(String lichtsterkte) {
        this.lichtsterkte = lichtsterkte;
    }
}
