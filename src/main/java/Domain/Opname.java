package Domain;

import java.sql.Time;
import java.util.ArrayList;

public class Opname {
    private Time tijd;
    private ArrayList<Meting> metingen;
    private String opmerking;
    private Voeding voeding;

    public Opname(Time tijd, ArrayList<Meting> metingen, String opmerking, Voeding voeding) {
        this.tijd = tijd;
        this.metingen = metingen;
        this.opmerking = opmerking;
        this.voeding = voeding;
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

}
