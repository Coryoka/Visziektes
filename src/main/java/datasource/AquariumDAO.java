package datasource;

import Domain.Aquarium;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AquariumDAO extends DAO {
    private AquariumDagOpnameDAO aquariumDagOpnameDAO;

    public AquariumDAO() throws IOException, ClassNotFoundException {
        super();
        aquariumDagOpnameDAO = new AquariumDagOpnameDAO();
    }


    public ArrayList<Aquarium> getAquariumOfGebruiker(String gebruikerId) throws SQLException {
        checkDbConnection();
        ArrayList<Aquarium> aquariums = new ArrayList<Aquarium>();

        ResultSet resultSet = con.prepareStatement("SELECT * FROM AQUARIUM WHERE GebruikerNaam = '" + gebruikerId + "'").executeQuery();
        while(resultSet.next()) {
            Aquarium aquarium = new Aquarium(resultSet.getInt("AquariumID"), resultSet.getString("GebruikerNaam"), resultSet.getInt("VolumeInLiters"),
                    resultSet.getInt("Temperatuur"), resultSet.getString("AqWaterType"), resultSet.getInt("AantalVerversingenPerWeek"),
                    resultSet.getInt("ProcentwaterPerVerversing"), resultSet.getInt("AqLengteInCm"),
                    resultSet.getInt("AqBreedteInCm"), resultSet.getInt("AqHoogteInCm"));
            aquarium.setDagboek(aquariumDagOpnameDAO.getDagOpnamesVanAquarium(aquarium.getAquariumId()));
            aquariums.add(aquarium);
        }

        return aquariums;
    }

    public Aquarium getAquarium(int aquariumId) throws SQLException {
        checkDbConnection();
        ArrayList<Aquarium> aquariums = new ArrayList<Aquarium>();

        ResultSet resultSet = con.prepareStatement("SELECT * FROM AQUARIUM WHERE AquariumID = " + aquariumId).executeQuery();
        while(resultSet.next()) {
            Aquarium aquarium = new Aquarium(resultSet.getInt("AquariumID"), resultSet.getString("GebruikerNaam"), resultSet.getInt("VolumeInLiters"),
                    resultSet.getInt("Temperatuur"), resultSet.getString("AqWaterType"), resultSet.getInt("AantalVerversingenPerWeek"),
                    resultSet.getInt("ProcentwaterPerVerversing"), resultSet.getInt("AqLengteInCm"),
                    resultSet.getInt("AqBreedteInCm"), resultSet.getInt("AqHoogteInCm"));
            aquarium.setDagboek(aquariumDagOpnameDAO.getDagOpnamesVanAquarium(aquarium.getAquariumId()));
            aquariums.add(aquarium);
        }

        return aquariums.get(0);
    }

    public void InsertAquarium(String Gebruikersnaam,int VolumeInLiters, int Tempratuur, String AqWaterType, int AantalverversingenPerWeek, int ProcentwaterPerVerversing, int AqLengteInCm, int AqBreedteinCm, int AqHoogteInCm) throws SQLException {
        checkDbConnection();
        con.prepareStatement("INSERT INTO Aquarium VALUES('"+Gebruikersnaam+ "',"+VolumeInLiters+","+Tempratuur+",'"+AqWaterType+"',"+AantalverversingenPerWeek+","+ProcentwaterPerVerversing+","+AqLengteInCm+","+AqBreedteinCm+","+AqHoogteInCm+")").executeUpdate();
    }
}
