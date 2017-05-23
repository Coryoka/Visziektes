package Domain;

public class Vis {
    private String genus;
    private String soort;

    public Vis(String genus, String soort) {
        this.genus = genus;
        this.soort = soort;
    }

    public String getGenus() {
        return genus;
    }

    public String getSoort() {
        return soort;
    }

    @Override
    public String toString() {
        return genus + " " + soort;
    }
}
