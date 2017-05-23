package Main;

import Domain.*;
import datasource.AquariumDAO;
import datasource.AquariumDagOpnameDAO;
import datasource.VissenDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.BatchUpdateException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

public class DagboekController implements Initializable {
    private AquariumDAO aquariumDAO;
    private VissenDAO vissenDAO;
    @FXML TableView<AquariumDagOpname> dagboekTableView;
    @FXML TableColumn<AquariumDagOpname, String> dagKolom;
    @FXML TableColumn<AquariumDagOpname, String> tijdKolom;
    private ArrayList<TableColumn<AquariumDagOpname,String>> kolommen = new ArrayList<>();
    @FXML TableColumn<String, AquariumDagOpname> opmerkingKolom;
    @FXML TableColumn<String, AquariumDagOpname> voeding;
    @FXML Button nieuweInvoer;
    private int aquariumId;
    private Aquarium aquarium;

    @FXML TableView<VissenInAquarium> vissenInAquarium;
    @FXML TableColumn<VissenInAquarium, String> visGenus;
    @FXML TableColumn<VissenInAquarium, String> vissoort;
    @FXML TableColumn<VissenInAquarium, String> datumToegevoegd;
    @FXML TableColumn<VissenInAquarium, String> aantalHuidigeVissen;
    @FXML TableColumn<VissenInAquarium, String> aantalOorspronkelijkeVissen;
    @FXML TableColumn<VissenInAquarium, String> leverancier;
    @FXML Button vissenToevoegen;

    public DagboekController(int aquariumId) {
        this.aquariumId = aquariumId;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            aquariumDAO = new AquariumDAO();
            vissenDAO = new VissenDAO();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        dagKolom.setCellValueFactory( AquariumDagOpname -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(format.format(AquariumDagOpname.getValue().getDag()));
            return property;
        });
        tijdKolom.setCellValueFactory(new PropertyValueFactory<AquariumDagOpname, String>("tijd"));
        opmerkingKolom.setCellValueFactory(new PropertyValueFactory<String, AquariumDagOpname>("opmerking"));
        voeding.setCellValueFactory(new PropertyValueFactory<String, AquariumDagOpname>("voeding"));

        laadTableView();
        laadVissenTableView();


        nieuweInvoer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                NieuwInvoerController controller = new NieuwInvoerController(aquariumId, getThis());
                openPane(nieuweInvoer, "dagboekInvoer.fxml", controller);
            }
        });

        vissenToevoegen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VisToevoegenController controller = new VisToevoegenController(getThis(), vissenDAO);
                openPane(vissenToevoegen, "nieuweVissen.fxml", controller);
            }
        });
    }
    private void openPane(Button button, String fxml, Initializable initializable) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxml));
        loader.setController(initializable);
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root, 800, 480);
        stage.setScene(scene);
        stage.initOwner(button.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    public void laadVissenTableView() {
        visGenus.setCellValueFactory(new PropertyValueFactory<VissenInAquarium, String>("genus"));
        vissoort.setCellValueFactory(new PropertyValueFactory<VissenInAquarium, String>("soort"));
        datumToegevoegd.setCellValueFactory(new PropertyValueFactory<VissenInAquarium, String>("datumToevoeging"));
        aantalHuidigeVissen.setCellValueFactory(new PropertyValueFactory<VissenInAquarium, String>("huidigeVissen"));
        aantalOorspronkelijkeVissen.setCellValueFactory(new PropertyValueFactory<VissenInAquarium, String>("oorspronkelijkeVissen"));
        leverancier.setCellValueFactory(new PropertyValueFactory<VissenInAquarium, String>("leverancier"));
        datumToegevoegd.setSortType(TableColumn.SortType.DESCENDING);
        try {
            aquarium.setVissenInAquarium(vissenDAO.getVissenVanAquarium(aquariumId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        vissenInAquarium.getItems().setAll(aquarium.getVissenInAquarium());
        vissenInAquarium.getSortOrder().add(datumToegevoegd);
    }

    public DagboekController getThis() {
        return this;
    }

    public void laadTableView() {
        try {
            aquarium = aquariumDAO.getAquarium(aquariumId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        dagKolom.setSortType(TableColumn.SortType.DESCENDING);
        tijdKolom.setSortType(TableColumn.SortType.DESCENDING);

        dagboekTableView.getColumns().removeAll(kolommen);
        kolommen = new ArrayList<>();
        for (String variabel: aquarium.metingVariabelen()) {
            if(!kolommen.contains(new TableColumn(variabel))) {
                kolommen.add(new TableColumn(variabel));
            }
        }
        dagboekTableView.getColumns().addAll((Collection<? extends TableColumn<AquariumDagOpname, ?>>) kolommen);

        for(TableColumn kolom: kolommen){
            kolom.setCellFactory(getCellfactory());
            kolom.setCellValueFactory(new PropertyValueFactory<String, AquariumDagOpname>(kolom.getText()));
        }

        dagboekTableView.getItems().setAll(aquarium.getDagboek());
        dagboekTableView.getSortOrder().add(dagKolom);
        dagboekTableView.getSortOrder().add(tijdKolom);
    }

    public Callback<TableColumn, TableCell> getCellfactory() {
        Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        TableCell cell = new TableCell<AquariumDagOpname, String>() {
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                setText(empty ? null : getString());
                                setGraphic(null);
                            }

                            private String getString() {
                                return getItem() == null ? "" : getItem().toString();
                            }
                        };

                        cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (event.getClickCount() > 1) {
                                    showChart(cell.getTableColumn());
                                }
                            }
                        });
                        return cell;
                    }
                };
        return cellFactory;
    }

    public void showChart(TableColumn column){
        String variabel = column.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("waardeGeschiedenis.fxml"));
        ChartController controller = new ChartController(variabel,aquarium.getDagboek());
        loader.setController(controller);
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root, 800, 480);
        stage.setScene(scene);
        stage.initOwner(nieuweInvoer.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();


    }

    public int getAquariumId() {
        return aquariumId;
    }
}
