package datasource;

import Domain.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

    public ArrayList<Meter> getMeters(int aquariumID, Gebruiker Gebruikersnaam) throws SQLException {
        checkDbConnection();
        ArrayList<Meter> meters = new ArrayList<>();
        ResultSet resultSet = con.prepareStatement("SELECT * " +
                "FROM Meters m INNER JOIN Aquarium a ON m.AquariumID=a.AquariumID " +
                "WHERE a.GebruikerNaam = '" +Gebruikersnaam.getNaam()+"' AND m.AquariumID = " + aquariumID).executeQuery();
        while(resultSet.next()) {
            Meter meter = new Meter(resultSet.getInt("AquariumID"), resultSet.getString("NaamApparaat"), resultSet.getTimestamp("InstallatieDatum"));
            meters.add(meter);
        }
        return meters;
    }

    public ArrayList<String> getVariabelen() throws SQLException {
        checkDbConnection();
        ArrayList<String> variabelen = new ArrayList<>();
        ResultSet resultSet = con.prepareStatement("SELECT * FROM MeetbareVariabelen").executeQuery();
        while(resultSet.next()){
            variabelen.add(resultSet.getString("VariabeleID"));
        }
        return variabelen;
    }


    public ArrayList<Filter> getFilters(int aquariumID,Gebruiker gebruikersnaam) throws SQLException{
        checkDbConnection();
        ArrayList<Filter> filters = new ArrayList<>();
        ResultSet resultSet = con.prepareStatement("SELECT *\n" +
                "FROM Filters f INNER JOIN Aquarium a ON f.AquariumID=a.AquariumID \n" +
                "WHERE a.GebruikerNaam = '" +gebruikersnaam.getNaam()+"' AND f.AquariumID = " + aquariumID).executeQuery();
        while(resultSet.next()) {
            Filter filter = new Filter(resultSet.getInt("AquariumId"),resultSet.getString("NaamApparaat"),resultSet.getTimestamp("InstallatieDatum"), resultSet.getTimestamp("FilterLaatstSchoongemaakt"));
            filters.add(filter);
        }
        return filters;
    }

    public ArrayList<Pomp> getPomp(int aquariumID,Gebruiker gebruikersnaam) throws SQLException{
        checkDbConnection();
        ArrayList<Pomp> pomps = new ArrayList<>();
        ResultSet resultSet = con.prepareStatement("SELECT *\n" +
                "FROM Pompen p INNER JOIN Aquarium a ON p.AquariumID=a.AquariumID \n" +
                "WHERE a.GebruikerNaam = '" +gebruikersnaam.getNaam()+"' AND p.AquariumID = " + aquariumID).executeQuery();
        while(resultSet.next()) {
            Pomp pomp = new Pomp(resultSet.getInt("AquariumId"),resultSet.getString("NaamApparaat"),resultSet.getTimestamp("InstallatieDatum"), resultSet.getTimestamp("PompLaatstSchoongemaakt"));
            pomps.add(pomp);
        }
        return pomps;
    }
    public ArrayList<Verlichting> getVerlichting(int aquariumID,Gebruiker gebruikersnaam) throws SQLException{
        checkDbConnection();
        ArrayList<Verlichting> verlichtings = new ArrayList<>();
        ResultSet resultSet = con.prepareStatement("SELECT *\n" +
                "FROM Verlichting V INNER JOIN Aquarium a ON V.AquariumID=a.AquariumID \n" +
                "WHERE a.GebruikerNaam = '" +gebruikersnaam.getNaam()+"' AND V.AquariumID = " + aquariumID).executeQuery();
        while(resultSet.next()) {
            Verlichting verlichting = new Verlichting(resultSet.getInt("AquariumId"),resultSet.getString("NaamApparaat"),resultSet.getTimestamp("InstallatieDatum"), resultSet.getString("Lichtsterkte"));
            verlichtings.add(verlichting);
        }
        return verlichtings;
    }



    public void InsertAquarium(String Gebruikersnaam,int VolumeInLiters, int Tempratuur, String AqWaterType, int AantalverversingenPerWeek, int ProcentwaterPerVerversing, int AqLengteInCm, int AqBreedteinCm, int AqHoogteInCm) throws SQLException {
        checkDbConnection();
        con.prepareStatement("INSERT INTO Aquarium VALUES('"+Gebruikersnaam+ "',"+VolumeInLiters+","+Tempratuur+",'"+AqWaterType+"',"+AantalverversingenPerWeek+","+ProcentwaterPerVerversing+","+AqLengteInCm+","+AqBreedteinCm+","+AqHoogteInCm+")").executeUpdate();
    }

    public void InsertFilter(int aquariumId, String naamApparaat, Timestamp datumToevoeging, Timestamp filterLaatstSchoongemaakt) throws SQLException {
        checkDbConnection();
        con.prepareStatement("INSERT INTO Filters VALUES("+aquariumId+",'"+naamApparaat+"','"+datumToevoeging+"','"+filterLaatstSchoongemaakt+"')").executeUpdate();
    }

    public void InsertPomp(int aquariumId, String naamApparaat, Timestamp datumToevoeging, Timestamp laatstSchoonGemaakt) throws SQLException {
        checkDbConnection();
        con.prepareStatement("INSERT INTO Pompen VALUES("+aquariumId+",'"+naamApparaat+"','"+datumToevoeging+"','"+laatstSchoonGemaakt+"')").executeUpdate();
    }

    public void InsertMeter(int aquariumId, String naamApparaat, Timestamp datumToevoeging, String variabeleId) throws SQLException {
        checkDbConnection();
        con.prepareStatement("INSERT INTO Meters VALUES("+aquariumId+",'"+naamApparaat+"','"+datumToevoeging+"')").executeUpdate();
        con.prepareStatement("INSERT INTO MeetbareVariabele_van_Meter VALUES("+aquariumId+",'"+naamApparaat+"','"+datumToevoeging+"','"+ variabeleId+"')").executeUpdate();
    }

    public void InsertMedium(int aquariumId, String naamApparaat, Timestamp datumToevoeging, Timestamp mediumDatumToevoeging, String MediumNaam) throws SQLException {
        checkDbConnection();
        con.prepareStatement("INSERT INTO Filtermediums VALUES ("+aquariumId+",'"+naamApparaat+"','"+datumToevoeging+"','"+mediumDatumToevoeging+",'"+MediumNaam+")");
    }


    public void InsertVerlichting(int aquariumId, String naamApparaat, Timestamp datumToevoeging, String lichtsterkte) throws SQLException {
        checkDbConnection();
        con.prepareStatement("INSERT INTO Verlichting VALUES("+aquariumId+",'"+naamApparaat+"','"+datumToevoeging+"','"+lichtsterkte+"')").executeUpdate();
    }
}
