package Domain;


public class Voeding{
    private String naam;
    private String dosering;

    public Voeding(String naam, String dosering) {
        this.naam = naam;
        this.dosering = dosering;
    }

    public String getNaam() {
        return naam;
    }

    public String getDosering() {
        return dosering;
    }
}
