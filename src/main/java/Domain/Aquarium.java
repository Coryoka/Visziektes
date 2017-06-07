package Domain;

import java.util.ArrayList;

public class Aquarium {
    private int aquariumId;
    private String gebruikerNaam;
    private int volumeInLiters;
    private int temperatuur;
    private String waterType;
    private int aantalVerversingen;
    private int procentWaterPerVerversing;
    private int aqLengte;
    private int aqBreedte;
    private int aqHoogte;
    private ArrayList<AquariumDagOpname> dagboek;

    public Aquarium(ArrayList<AquariumDagOpname> dagboek) {
        this.dagboek = dagboek;
    }

    public Aquarium(int aquariumId, String gebruikerId, int volumeInLiters, int temperatuur, String waterType, int aantalVerversingen, int procentWaterPerVerversing, int aqLengte, int aqBreedte, int aqHoogte) {
        this.aquariumId = aquariumId;
        this.gebruikerNaam = gebruikerId;
        this.volumeInLiters = volumeInLiters;
        this.temperatuur = temperatuur;
        this.waterType = waterType;
        this.aantalVerversingen = aantalVerversingen;
        this.procentWaterPerVerversing = procentWaterPerVerversing;
        this.aqLengte = aqLengte;
        this.aqBreedte = aqBreedte;
        this.aqHoogte = aqHoogte;
    }

    public void setDagboek(ArrayList<AquariumDagOpname> dagboek) {
        this.dagboek = dagboek;
    }

    public ArrayList<AquariumDagOpname> getDagboek() {
        return dagboek;
    }

    public int getAquariumId() {
        return aquariumId;
    }

    public String getGebruikerNaam() {
        return gebruikerNaam;
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

    public ArrayList<String> metingVariabelen() {
        ArrayList<String> variabelen = new ArrayList<>();
        for (AquariumDagOpname dagOpname : dagboek) {
            if(dagOpname.getOpname().getMetingen() != null) {
                for (Meting meting : dagOpname.getOpname().getMetingen()) {
                    if (!variabelen.contains(meting.getVariabeleId())) {
                        variabelen.add(meting.getVariabeleId());
                    }
                }
            }
        }
        return variabelen;
    }
}
