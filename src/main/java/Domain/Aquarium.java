package Domain;

public class Aquarium {
    private int aquariumId;
    private int gebruikerId;
    private int volumeInLiters;
    private int temperatuur;
    private String waterType;
    private int aantalVerversingen;
    private int procentWaterPerVerversing;
    private int aqLengte;
    private int aqBreedte;
    private int aqHoogte;

    public Aquarium(int aquariumId, int gebruikerId, int volumeInLiters, int temperatuur, String waterType, int aantalVerversingen, int procentWaterPerVerversing, int aqLengte, int aqBreedte, int aqHoogte) {
        this.aquariumId = aquariumId;
        this.gebruikerId = gebruikerId;
        this.volumeInLiters = volumeInLiters;
        this.temperatuur = temperatuur;
        this.waterType = waterType;
        this.aantalVerversingen = aantalVerversingen;
        this.procentWaterPerVerversing = procentWaterPerVerversing;
        this.aqLengte = aqLengte;
        this.aqBreedte = aqBreedte;
        this.aqHoogte = aqHoogte;
    }


    public int getAquariumId() {
        return aquariumId;
    }

    public int getGebruikerId() {
        return gebruikerId;
    }

    public int getVolumeInLiters() {
        return volumeInLiters;
    }

    public int getTemperatuur() {
        return temperatuur;
    }

    public String getWaterType() {
        return waterType;
    }

    public int getAantalVerversingen() {
        return aantalVerversingen;
    }

    public int getProcentWaterPerVerversing() {
        return procentWaterPerVerversing;
    }

    public int getAqLengte() {
        return aqLengte;
    }

    public int getAqBreedte() {
        return aqBreedte;
    }

    public int getAqHoogte() {
        return aqHoogte;
    }
}
