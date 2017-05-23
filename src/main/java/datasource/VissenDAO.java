package datasource;

import Domain.Vis;
import Domain.VissenInAquarium;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

public class VissenDAO extends DAO {


    public VissenDAO() throws IOException, ClassNotFoundException {
        super();

    }

    public ArrayList<VissenInAquarium> getVissenVanAquarium(int aquariumId) throws SQLException {
        checkDbConnection();
        ArrayList<VissenInAquarium> vissenInAquariums = new ArrayList<>();
        ResultSet resultSet = con.prepareStatement("SELECT * FROM VissenInAquarium WHERE AquariumID = " + aquariumId).executeQuery();
        while(resultSet.next()){
            Vis vis = new Vis(resultSet.getString("NaamVisGenus"), resultSet.getString("NaamVissoort"));
            vissenInAquariums.add(new VissenInAquarium(resultSet.getInt("GroupID"),vis , resultSet.getTimestamp("DatumToevoeging"),
                    resultSet.getInt("AantalHuidigeVissen"), resultSet.getInt("AantalOorspronkelijkeVissen"), resultSet.getString("Leverancier")));

        }
        return vissenInAquariums;
    }

    public void saveVisInAquarium(int aquariumId, VissenInAquarium vissenInAquarium) throws SQLException {
        checkDbConnection();
        con.prepareStatement("INSERT INTO VissenInAquarium VALUES('" + vissenInAquarium.getVis().getGenus() + "', '"+ vissenInAquarium.getVis().getSoort() + "', "
                + aquariumId + ", '" + vissenInAquarium.getDatumToevoeging() + "', " + vissenInAquarium.getHuidigeVissen() + ", " + vissenInAquarium.getHuidigeVissen() +
                ", '" + vissenInAquarium.getLeverancier() + "')").executeUpdate();
    }

    public ArrayList<Vis> alleVissen() throws SQLException {
        checkDbConnection();
        ArrayList<Vis> vissen = new ArrayList<>();
        ResultSet resultSet = con.prepareStatement("SELECT * FROM Vissoort").executeQuery();
        while(resultSet.next()){
            vissen.add(new Vis(resultSet.getString("NaamVisGenus"), resultSet.getString("NaamVissoort")));
        }
        vissen
                .stream()
                .sorted(Comparator.comparing(Vis::getGenus));
        return vissen;
    }
}
