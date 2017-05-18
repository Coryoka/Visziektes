package Domain;

import java.sql.Date;
import java.sql.Timestamp;

public class AquariumDagOpname {
    private Timestamp dag;
    private Opname opname;

    public AquariumDagOpname(Timestamp dag, Opname opname) {
        this.dag = dag;
        this.opname = opname;
    }

    public Timestamp getDag() {
        return dag;
    }

    public String getTijd() {
        return opname.getTijd().toString();
    }

    public String getOpmerking() {
        return opname.getOpmerking();
    }

    public String getVoeding() {
        if(opname.getVoeding() == null){
            return "";
        }
        return opname.getVoeding().getNaam() + " dosering: " + opname.getVoeding().getDosering();
    }

    public String getO2() {
        return getValue("O2");
    }

    public String getTemp() {
        return getValue("Temp");
    }

    public String getPH() {
        return getValue("PH");
    }

    public String getGH() {
        return getValue("GH");
    }

    private String getValue(String id){
        for(Meting meting : opname.getMetingen()){
            if(meting.getVariabeleId().equals(id)){
                return meting.getWaarde()+"";
            }
        }
        return "";
    }

    public Opname getOpname() {
        return opname;
    }
}
