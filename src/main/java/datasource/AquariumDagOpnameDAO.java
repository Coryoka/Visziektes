package datasource;

import Domain.AquariumDagOpname;
import Domain.Meting;
import Domain.Opname;
import Domain.Voeding;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AquariumDagOpnameDAO extends DAO {

    public AquariumDagOpnameDAO() throws IOException, ClassNotFoundException {
        super();
    }


    public ArrayList<AquariumDagOpname> getDagOpnamesVanAquarium(int AquariumId) throws SQLException {
        checkDbConnection();
        HashMap<Timestamp, AquariumDagOpname> opnames = new HashMap<>();
        ResultSet resultSet = con.prepareStatement("SELECT ADO.Dag, M.VariabeleID, M.TijdMeting, M.WaardeMeting FROM AquariumDagOpname ADO INNER JOIN Meting M ON ADO.AquariumID = M.AquariumID AND ADO.Dag = M.Dag WHERE ADO.AquariumID = " +AquariumId + " ORDER BY TijdMeting").executeQuery();
        while(resultSet.next()) {
            Timestamp dag = resultSet.getTimestamp("Dag");
            Timestamp tijd = resultSet.getTimestamp("TijdMeting");
            Opname opname = new Opname();
            opname.setTijd(new Time(tijd.getTime()));
            Meting meting = new Meting("", resultSet.getString("VariabeleID"), resultSet.getInt("WaardeMeting"));
            opname.addMeting(meting);
            if(opnames.get(dag) == null){
                opnames.put(dag, new AquariumDagOpname(dag,opname));
            } else if(opnames.get(dag).getOpname().getTijd().equals(opname.getTijd())){
                opnames.get(dag).getOpname().addMeting(meting);
            } else {

                opnames.put(dag, new AquariumDagOpname(dag, opname));
            }
        }
        ResultSet resultSet1 = con.prepareStatement("SELECT ADO.Dag, O.OpmerkingTekst, O.TijdOpmerking FROM AquariumDagOpname ADO INNER JOIN Opmerking O ON ADO.AquariumID = O.AquariumID AND ADO.Dag = O.Dag WHERE ADO.AquariumID = " +AquariumId + " ORDER BY TijdOpmerking").executeQuery();
        while (resultSet1.next()){
            Timestamp dag = resultSet1.getTimestamp("Dag");
            Timestamp tijd = resultSet1.getTimestamp("TijdOpmerking");
            Opname opname = new Opname();
            opname.setTijd(new Time(tijd.getTime()));
            opname.setOpmerking(resultSet1.getString("OpmerkingTekst"));
            if(opnames.get(dag)== null){
                opnames.put(dag, new AquariumDagOpname(dag, opname));
            } else if(opnames.get(dag).getOpname().getTijd().equals(opname.getTijd())){
                opnames.get(dag).getOpname().setOpmerking(opname.getOpmerking());
            } else {
                opnames.put(dag, new AquariumDagOpname(dag, opname));
            }
        }
        ResultSet resultSet2 = con.prepareStatement("SELECT ADO.Dag, V.VoedingNaam, V.Dosering, V.TijdVoeding FROM AquariumDagOpname ADO INNER JOIN Voeding V ON ADO.AquariumID = V.AquariumID AND ADO.Dag = V.Dag WHERE ADO.AquariumID = " +AquariumId + " ORDER BY TijdVoeding").executeQuery();
        while (resultSet2.next()){
            Timestamp dag = resultSet2.getTimestamp("Dag");
            Timestamp tijd = resultSet2.getTimestamp("TijdVoeding");
            Opname opname = new Opname();
            opname.setTijd(new Time(tijd.getTime()));
            opname.setVoeding(new Voeding(resultSet2.getString("VoedingNaam"),resultSet2.getString("Dosering")));
            if(opnames.get(dag)== null){
                opnames.put(dag, new AquariumDagOpname(dag, opname));
            } else if(opnames.get(dag).getOpname().getTijd().equals(opname.getTijd())){
                opnames.get(dag).getOpname().setVoeding(opname.getVoeding());
            } else {
                opnames.put(dag, new AquariumDagOpname(dag, opname));
            }
        }
        return new ArrayList<>(opnames.values());

    }


    public void saveInvoer(Opname opname, int aquariumId, Timestamp dag) throws SQLException {
        checkDbConnection();

        ResultSet resultSet = con.prepareStatement("SELECT * FROM AquariumDagOpname WHERE AquariumID = "+ aquariumId +" AND Dag = '"+ dag.toString() +"'").executeQuery();


        if(!resultSet.next()){
            con.prepareStatement("INSERT INTO AquariumDagOpname VALUES (" +aquariumId +", '"+ dag.toString() +"')").executeUpdate();
        }

        if(opname.getVoeding() != null){
            con.prepareStatement("INSERT INTO Voeding VALUES("+ aquariumId + ", '" + dag.toString() + "', '"+ opname.getTijd() + "', '"+ opname.getVoeding().getNaam() +"', '" + opname.getVoeding().getDosering() +"')").executeUpdate();
        }

        if(opname.getMetingen() != null){
            for (Meting meting : opname.getMetingen()){
                con.prepareStatement("INSERT INTO Meting VALUES ('"+ meting.getVariabeleId() + "', " + aquariumId + ", '" + dag.toString() + "', '" + opname.getTijd().toString() + "', " + meting.getWaarde() +")").executeUpdate();
            }
        }

        if (opname.getOpmerking() != null){
            con.prepareStatement("INSERT INTO Opmerking VALUES("+aquariumId +", '" + dag.toString() + "', '" + opname.getTijd().toString() + "', '" + opname.getOpmerking() + "')").executeUpdate();
        }
    }
}
