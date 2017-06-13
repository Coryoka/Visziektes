package Domain;

import java.util.ArrayList;

public class Symptoom {
    private int symptoomId;
    private ArrayList<Integer> ziekteId;
    private String mediaUrls;
    private String name;


    public Symptoom(int symptoomId, String mediaUrls, String name) {
        ziekteId = new ArrayList<>();
        this.symptoomId = symptoomId;
        this.mediaUrls = mediaUrls;
        this.name = name;
    }

    public ArrayList<Integer> getZiekteId() {
        return ziekteId;
    }

    public String getMediaUrls() {
        return mediaUrls;
    }

    public String getName() {
        return name;
    }

    public int getSymptoomId() {
        return symptoomId;
    }

    public void setMediaUrls(String mediaUrls) {
        this.mediaUrls = mediaUrls;
    }

    public void setName(String name) {
        this.name = name;
    }
}
