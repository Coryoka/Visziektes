package Domain;

public class Gebruiker {
    private int gebruikerId;
    private String naam;

    public Gebruiker(int gebruikerId, String naam) {
        this.gebruikerId = gebruikerId;
        this.naam = naam;
    }

    public int getGebruikerId() {
        return gebruikerId;
    }

    public String getNaam() {
        return naam;
    }
}
