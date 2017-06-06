package Domain;

public class Vis {
    private int visId;
    private int groupId;
    private String naam;
    private String opmerking;
    private int geboortejaar;

    public Vis(int visId, int groupId, String naam, String opmerking, int geboortejaar) {
        this.visId = visId;
        this.groupId = groupId;
        this.naam = naam;
        this.opmerking = opmerking;
        this.geboortejaar = geboortejaar;
    }

    public Vis(String naam, String opmerking, int geboortejaar, int groupId) {
        this.naam = naam;
        this.opmerking = opmerking;
        this.geboortejaar = geboortejaar;
        this.groupId = groupId;
    }

    public int getVisId() {
        return visId;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getNaam() {
        return naam;
    }

    public String getOpmerking() {
        return opmerking;
    }

    public int getGeboortejaar() {
        return geboortejaar;
    }
}
