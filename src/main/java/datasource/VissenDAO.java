package datasource;

import Domain.Vis;
import Domain.Vissoort;
import Domain.VissenInAquarium;

import java.io.IOException;
import java.sql.*;
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
            Vissoort vis = new Vissoort(resultSet.getString("NaamVisGenus"), resultSet.getString("NaamVissoort"));
            vissenInAquariums.add(new VissenInAquarium(resultSet.getInt("GroepID"),vis , resultSet.getTimestamp("DatumToevoeging"),
                    resultSet.getInt("AantalHuidigeVissen"), resultSet.getInt("AantalOorspronkelijkeVissen"), resultSet.getString("VisLeverancierNaam")));
        }
        return vissenInAquariums;
    }

    public String saveVisInAquarium(int aquariumId, VissenInAquarium vissenInAquarium) throws SQLException {
        checkDbConnection();
        PreparedStatement statement = con.prepareStatement("INSERT INTO VissenInAquarium VALUES('" + vissenInAquarium.getVis().getGenus() + "', '"+ vissenInAquarium.getVis().getSoort() + "', "
                + aquariumId + ", '" +vissenInAquarium.getLeverancier() +"', '" + vissenInAquarium.getDatumToevoeging() + "', " + vissenInAquarium.getHuidigeVissen() + ", " + vissenInAquarium.getHuidigeVissen() +
                ")");
        statement.executeUpdate();
        SQLWarning warning = statement.getWarnings();

        if(warning != null) {
            return warning.getMessage();
        }
        return null;

    }

    public String updateVisInAquarium(VissenInAquarium vissenInAquarium) throws SQLException {
        checkDbConnection();
        PreparedStatement preparedStatement = con.prepareStatement("UPDATE VissenInAquarium " +
                "SET NaamVisGenus = '"+ vissenInAquarium.getGenus() + "', NaamVissoort = '"+ vissenInAquarium.getSoort()+"', DatumToevoeging = '"+vissenInAquarium.getDatumToevoeging()+"', AantalHuidigeVissen = " + vissenInAquarium.getHuidigeVissen()+", Leverancier = '"+ vissenInAquarium.getLeverancier()+"'" +
                " WHERE GroepID = "+ vissenInAquarium.getGroupId());
        Long result = preparedStatement.executeLargeUpdate();
        SQLWarning warning = preparedStatement.getWarnings();
        if(warning != null) {
            return warning.getMessage();
        }
        return null;
    }

    public void deleteVissenInAquarium(VissenInAquarium vissenInAquarium) throws SQLException {
        checkDbConnection();
        con.prepareStatement("DELETE FROM VissenInAquarium WHERE GroepId = " + vissenInAquarium.getGroupId()).executeUpdate();
    }

    public ArrayList<Vissoort> alleVissen() throws SQLException {
        checkDbConnection();
        ArrayList<Vissoort> vissen = new ArrayList<>();
        ResultSet resultSet = con.prepareStatement("SELECT * FROM Vissoort").executeQuery();
        while(resultSet.next()){
            vissen.add(new Vissoort(resultSet.getString("NaamVisGenus"), resultSet.getString("NaamVissoort")));
        }
        vissen
                .stream()
                .sorted(Comparator.comparing(Vissoort::toString));
        return vissen;
    }

    public ArrayList<Vis> individueleVissenBijAquarium(int aquariumId) throws SQLException {
        checkDbConnection();
        ArrayList<Vis> individueleVissen = new ArrayList<>();
        ResultSet resultSet = con.prepareStatement("SELECT * FROM Vis V INNER JOIN VisGeschiedenis VG ON V.VisId = VG.VisId INNER JOIN VissenInAquarium VIA ON V.GroupID = VIA.GroepID WHERE VIA.AquariumID = " + aquariumId).executeQuery();
        while(resultSet.next()) {
            Vis vis = new Vis(resultSet.getInt("VisId"),resultSet.getInt("GroupId"),
                    resultSet.getString("NaamVis"), resultSet.getString("Opmerking_Vis"), resultSet.getInt("GeboortejaarVis"));
            vis.setSoort(new Vissoort(resultSet.getString("NaamVisGenus"), resultSet.getString("NaamVissoort")));
            individueleVissen.add(vis);
        }

        return individueleVissen;
    }

    public void saveIndividueleVis(Vis vis, Timestamp toegevoegd, int aquariumId) throws SQLException {
        checkDbConnection();
        System.out.println(toegevoegd.toString());
        con.prepareStatement("EXEC SP_ToevoegenIndividueleVis @GroepId = "+vis.getGroupId()+", @NaamVis = '"+vis.getNaam()+"', @OpmerkingVis = '"+vis.getOpmerking()+"', @GeboorteJaar = "+ vis.getGeboortejaar() +
        ", @aquariumId = "+ aquariumId+ ", @Toegevoegd = '"+toegevoegd.toString()+"'").executeUpdate();

    }



}
