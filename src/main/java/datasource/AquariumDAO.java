package datasource;

import Domain.Aquarium;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AquariumDAO extends DAO {


    public AquariumDAO() throws IOException, ClassNotFoundException {
        super();
    }


    public ArrayList<Aquarium> getAquariumOfGebruiker(int gebruikerId) throws SQLException {
        checkDbConnection();
        ArrayList<Aquarium> aquariums = new ArrayList<Aquarium>();

        ResultSet resultSet = con.prepareStatement("SELECT * FROM AQUARIUM WHERE GebruikerID = " + gebruikerId).executeQuery();
        while(resultSet.next()) {
            aquariums.add(new Aquarium(resultSet.getInt("AquariumID"), resultSet.getInt("GebruikerID"), resultSet.getInt("VolumeInLiters"),
                    resultSet.getInt("Temperatuur"), resultSet.getString("AqWaterType"), resultSet.getInt("AantalVerversingenPerWeek"),
                    resultSet.getInt("ProcentwaterPerVerversing"), resultSet.getInt("AqLengteInCm"),
                    resultSet.getInt("AqBreedteInCm"), resultSet.getInt("AqHoogteInCm")));
        }

        return aquariums;
    }
}
