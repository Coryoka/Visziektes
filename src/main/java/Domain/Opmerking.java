package Domain;

import java.sql.Time;

public class Opmerking implements Tijd {
    private Time tijd;
    private String tekts;

    @Override
    public Time getTime() {
        return tijd;
    }
}
