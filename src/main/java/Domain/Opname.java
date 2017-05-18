package Domain;

import java.sql.Time;
import java.util.ArrayList;

public class Opname {
    private Time tijd;
    private ArrayList<Meting> metingen;
    private String opmerking;
    private Voeding voeding;

    public Opname() {
        metingen = new ArrayList<>();
    }

    public Opname(Time tijd, ArrayList<Meting> metingen, String opmerking, Voeding voeding) {
        this.tijd = tijd;
        this.metingen = metingen;
        this.opmerking = opmerking;
        this.voeding = voeding;
    }

    public void addMeting(Meting meting){
        if(metingen == null){
            metingen = new ArrayList<>();
        }
        metingen.add(meting);
    }

    public Time getTijd() {
        return tijd;
    }

    public ArrayList<Meting> getMetingen() {
        return metingen;
    }

    public String getOpmerking() {
        return opmerking;
    }

    public Voeding getVoeding() {
        return voeding;
    }

    public void setTijd(Time tijd) {
        this.tijd = tijd;
    }

    public void setMetingen(ArrayList<Meting> metingen) {
        this.metingen = metingen;
    }

    public void setOpmerking(String opmerking) {
        this.opmerking = opmerking;
    }

    public void setVoeding(Voeding voeding) {
        this.voeding = voeding;
    }
}
