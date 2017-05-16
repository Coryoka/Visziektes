package Domain;

import java.sql.Date;

public class AquariumDagOpname {
    private Date dag;
    private Opname opname;

    public AquariumDagOpname(Date dag, Opname opname) {
        this.dag = dag;
        this.opname = opname;
    }

    public Date getDag() {
        return dag;
    }

    public String getTijd() {
        return opname.getTijd().toString();
    }

    public String getOpmerking() {
        return opname.getOpmerking();
    }

    public String getVoeding() {
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
