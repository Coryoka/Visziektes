package Main;

import Domain.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

public class DagboekController implements Initializable {
    @FXML TableView<AquariumDagOpname> dagboekTableView;
    @FXML TableColumn<AquariumDagOpname, String> dagKolom;
    @FXML TableColumn<AquariumDagOpname, String> tijdKolom;
    private ArrayList<TableColumn<AquariumDagOpname,String>> kolommen = new ArrayList<>();
    @FXML TableColumn<String, AquariumDagOpname> opmerkingKolom;
    @FXML TableColumn<String, AquariumDagOpname> voeding;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dagKolom.setCellValueFactory(new PropertyValueFactory<AquariumDagOpname, String>("dag"));
        tijdKolom.setCellValueFactory(new PropertyValueFactory<AquariumDagOpname, String>("tijd"));
        opmerkingKolom.setCellValueFactory(new PropertyValueFactory<String, AquariumDagOpname>("opmerking"));
        voeding.setCellValueFactory(new PropertyValueFactory<String, AquariumDagOpname>("voeding"));

        ArrayList<AquariumDagOpname> opnames = new ArrayList<AquariumDagOpname>();
        ArrayList<Meting> metingen = new ArrayList<Meting>();
        metingen.add(new Meting("Zuurstof", "O2", 10));
        metingen.add(new Meting("Zuurgraad", "PH", 6));
        metingen.add(new Meting("Temperatuur", "Temp", 25));
        ArrayList<Meting> metingen1 = new ArrayList<Meting>();
        metingen1.add(new Meting("Zuurstof", "O2", 14));
        metingen1.add(new Meting("Zuurgraad", "PH", 7));
        metingen1.add(new Meting("waterhardheid", "GH", 30));

        Voeding voeding = new Voeding("brokken ", "5 kilo");
        Opname opname = new Opname(new Time(1494922302), metingen, "Vissen leven nog", voeding);
        Opname opname1 = new Opname(new Time(1495922402), metingen1, "er is een vis doodgegaan", voeding);
        opnames.add(new AquariumDagOpname(new Date(1494922281L),  opname));
        opnames.add(new AquariumDagOpname(new Date(1495023820L), opname1));
        Aquarium aquarium = new Aquarium(opnames);

        dagKolom.setSortType(TableColumn.SortType.DESCENDING);
        tijdKolom.setSortType(TableColumn.SortType.DESCENDING);

        for (String variabel: aquarium.metingVariabelen()) {
            kolommen.add(new TableColumn(variabel));
            System.out.println(variabel);
        }
        dagboekTableView.getColumns().addAll((Collection<? extends TableColumn<AquariumDagOpname, ?>>) kolommen);

        for(TableColumn kolom: kolommen){
            kolom.setCellValueFactory(new PropertyValueFactory<String, AquariumDagOpname>(kolom.getText()));
        }

        dagboekTableView.getItems().setAll(opnames);
        dagboekTableView.getSortOrder().add(dagKolom);
        dagboekTableView.getSortOrder().add(tijdKolom);


    }
}
