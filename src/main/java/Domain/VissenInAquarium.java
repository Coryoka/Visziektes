package Domain;

import java.sql.Timestamp;

public class VissenInAquarium {
    private int groupId;
    private Vis vis;
    private Timestamp datumToevoeging;
    private int huidigeVissen;
    private int oorspronkelijkeVissen;
    private String leverancier;

    public VissenInAquarium(int groupId, Vis vis, Timestamp datumToevoeging, int huidigeVissen, int oorspronkelijkeVissen, String leverancier) {
        this.groupId = groupId;
        this.vis = vis;
        this.datumToevoeging = datumToevoeging;
        this.huidigeVissen = huidigeVissen;
        this.oorspronkelijkeVissen = oorspronkelijkeVissen;
        this.leverancier = leverancier;
    }

    public VissenInAquarium(Vis vis, Timestamp datumToevoeging, int huidigeVissen, String leverancier) {
        this.vis = vis;
        this.datumToevoeging = datumToevoeging;
        this.huidigeVissen = huidigeVissen;
        this.leverancier = leverancier;
    }

    public int getGroupId() {
        return groupId;
    }

    public Vis getVis() {
        return vis;
    }

    public String getGenus(){
        return vis.getGenus();
    }

    public String getSoort(){
        return vis.getSoort();
    }

    public Timestamp getDatumToevoeging() {
        return datumToevoeging;
    }

    public int getHuidigeVissen() {
        return huidigeVissen;
    }

    public int getOorspronkelijkeVissen() {
        return oorspronkelijkeVissen;
    }

    public String getLeverancier() {
        return leverancier;
    }
}
