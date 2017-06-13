package datasource;

import Domain.Analyse;
import Domain.Symptoom;
import Domain.SymptoomInVis;
import Domain.Vis;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SymptoomDAO extends DAO {

    public SymptoomDAO() throws IOException, ClassNotFoundException {
        super();
    }

    public ArrayList<Symptoom> getAllSymptomsOnBreed(Vis vis, String taal) throws SQLException {
        ArrayList<Symptoom> symptooms = new ArrayList<>();
        checkDbConnection();
        ResultSet resultSet = con.prepareStatement("select * from SymptoomInZiekte SIZ INNER JOIN ZiekteInSoort ZIS ON SIZ.ZiekteID = ZIS.ZiekteID LEFT JOIN SymptoomMedia SM " +
                "ON ZIS.ZiekteID = SM.ZiekteID AND ZIS.NaamVisGenus = SM.NaamVisGenus AND ZIS.NaamVissoort = SM.NaamVissoort AND SIZ.SymptoomID = SM.SymptoomID " +
                "INNER JOIN SymptoomVertaling SV ON SIZ.SymptoomID = SV.SymptoomID " +
                "            WHERE ZIS.NaamVisGenus = '"+vis.getSoort().getGenus()+"' AND ZIS.NaamVissoort = '"+vis.getSoort().getSoort()+"' AND TaalNaam = 'English'").executeQuery();
        while(resultSet.next()){
            boolean zelfdeZiekte = false;
            zelfdeZiekte = isZelfdeZiekte(symptooms, resultSet);

            if(!zelfdeZiekte) {
                Symptoom symptoom = new Symptoom(resultSet.getInt("SymptoomId"), resultSet.getString("URLBestand"),
                        resultSet.getString("NaamSymptoom"));
                symptoom.getZiekteId().add(resultSet.getInt("ZiekteID"));
                symptooms.add(symptoom);
            }
        }
        return symptooms;
    }

    private boolean isZelfdeZiekte(ArrayList<Symptoom> symptooms, ResultSet resultSet) throws SQLException {
        for(Symptoom symptoom: symptooms){
            if(symptoom.getSymptoomId() == resultSet.getInt("SymptoomId")){
                symptoom.getZiekteId().add(resultSet.getInt("ZiekteID"));
                symptoom.setMediaUrls(resultSet.getString("URLBestand"));
                return true;
            }
        }
        return false;
    }

    public void saveSymptomsOnFish(ArrayList<SymptoomInVis> symptomen, int visId) throws SQLException {
        checkDbConnection();
        Gson gson = new Gson();
        con.prepareStatement("EXEC SP_SymptomenOpVis @symptoomJSON = '" + gson.toJson(symptomen) + "', @VisId = " + visId).executeUpdate();

    }

    public ArrayList<SymptoomInVis> getAllSymptomsOnFish(int visId) throws SQLException {
        ArrayList<SymptoomInVis> symptoms = new ArrayList<>();
        checkDbConnection();
        ResultSet resultSet = con.prepareStatement("select SymptoomID, DatumEersteObservatie, IntensiteitSymptoom from SymptoomInVis where VisId = "+ visId).executeQuery();
        while(resultSet.next()){
            symptoms.add(new SymptoomInVis(resultSet.getInt("SymptoomID"), null, null, resultSet.getTimestamp("DatumEersteObservatie"), resultSet.getInt("IntensiteitSymptoom")));
        }
        return symptoms;
    }

    public ArrayList<Analyse> doAnalyse(int visId) throws SQLException {
        Gson gson = new Gson();
        checkDbConnection();

        CallableStatement cstmt = con.prepareCall("{call USP_ziekteAnalyse(?,?,?)}");
        cstmt.setObject(1,Types.INTEGER);
        cstmt.setInt(1, visId);
        cstmt.registerOutParameter(3, Types.VARCHAR);
        cstmt.setObject(2, Types.VARCHAR);
        cstmt.setString(2, "English");
        cstmt.execute();
        Type type = new TypeToken<List<Analyse>>(){}.getType();
        return gson.fromJson(cstmt.getString(3), type);
    }

}
