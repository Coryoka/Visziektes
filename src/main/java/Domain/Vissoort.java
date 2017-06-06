package Domain;

public class Vissoort {
    private String genus;
    private String soort;

    public Vissoort(String genus, String soort) {
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
