package Domain;

import java.sql.Timestamp;

public class VissenInAquarium {
    private int groupId;
    private Vissoort vis;
    private Timestamp datumToevoeging;
    private int huidigeVissen;
    private int oorspronkelijkeVissen;
    private String leverancier;

    public VissenInAquarium(int groupId, Vissoort vis, Timestamp datumToevoeging, int huidigeVissen, int oorspronkelijkeVissen, String leverancier) {
        this.groupId = groupId;
        this.vis = vis;
        this.datumToevoeging = datumToevoeging;
        this.huidigeVissen = huidigeVissen;
        this.oorspronkelijkeVissen = oorspronkelijkeVissen;
        this.leverancier = leverancier;
    }

    public VissenInAquarium(Vissoort vis, Timestamp datumToevoeging, int huidigeVissen, String leverancier) {
        this.vis = vis;
        this.datumToevoeging = datumToevoeging;
        this.huidigeVissen = huidigeVissen;
        this.leverancier = leverancier;
    }

    public int getGroupId() {
        return groupId;
    }

    public Vissoort getVis() {
        return vis;
    }

    public String getGenus(){
        return vis.getGenus();
    }

    public String getSoort(){
        return vis.getSoort();
    }

    public String getDatumToevoeging() {
        return datumToevoeging.toString();
    }

    public Timestamp getDatumToegevoegd() {
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

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setVis(Vissoort vis) {
        this.vis = vis;
    }

    public void setDatumToevoeging(Timestamp datumToevoeging) {
        this.datumToevoeging = datumToevoeging;
    }

    public void setHuidigeVissen(int huidigeVissen) {
        this.huidigeVissen = huidigeVissen;
    }

    public void setOorspronkelijkeVissen(int oorspronkelijkeVissen) {
        this.oorspronkelijkeVissen = oorspronkelijkeVissen;
    }

    public void setLeverancier(String leverancier) {
        this.leverancier = leverancier;
    }
}
