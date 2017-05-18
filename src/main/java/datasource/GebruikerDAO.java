package datasource;

import Domain.Gebruiker;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GebruikerDAO extends DAO {

    public GebruikerDAO() throws IOException, ClassNotFoundException {
        super();
    }

    public Gebruiker getGebruiker(String naam, String wachtwoord) throws SQLException {
        checkDbConnection();
        Gebruiker gebruiker = null;
        ResultSet resultSet = con.prepareStatement("SELECT * FROM Gebruiker WHERE GebruikerNaam = '" + naam +
                "' AND GebruikerWachtwoord = '" + wachtwoord + "'").executeQuery();
        while(resultSet.next()){
            gebruiker = new Gebruiker(naam);
        }
        return gebruiker;
    }

}
