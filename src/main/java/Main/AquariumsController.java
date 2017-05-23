package Main;

import Domain.Aquarium;
import Domain.Gebruiker;
import datasource.AquariumDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AquariumsController implements Initializable {
    private Gebruiker gebruiker;
    private AquariumDAO aquariumDAO;
    @FXML TableView<Aquarium> aquariums;
    @FXML TableColumn<Aquarium, Integer> aquariumID;
    @FXML TableColumn<Aquarium, Integer> volume;
    @FXML TableColumn<Aquarium, Integer> temperatuur;
    @FXML TableColumn<Aquarium, String> watertype;
    @FXML TableColumn<Aquarium, Integer> verversing;
    @FXML TableColumn<Aquarium, Integer> procentPerVerversing;
    @FXML Button terug;

    public AquariumsController(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            aquariumDAO = new AquariumDAO();
            initinalizeTableView();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        terug.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("home.fxml"));
            Controller controller = new Controller(gebruiker);
            loader.setController(controller);
            Stage stage = (Stage)terug.getScene().getWindow();
            Parent root = null;
            try {
                root = loader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root, 1280, 720);
            stage.setScene(scene);
            stage.show();
        });

        aquariums.setRowFactory( tv -> {
            TableRow<Aquarium> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Aquarium rowData = row.getItem();
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("dagboek.fxml"));
                    DagboekController controller = new DagboekController(aquariums.getSelectionModel().getSelectedItem().getAquariumId(), gebruiker);
                    loader.setController(controller);
                    Stage stage = (Stage)row.getScene().getWindow();
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Scene scene = new Scene(root, 1280, 720);
                    stage.setScene(scene);
                    stage.show();
                }
            });
            return row ;
        });
    }

    private void initinalizeTableView() throws SQLException {
        aquariumID.setCellValueFactory(new PropertyValueFactory<Aquarium, Integer>("aquariumId"));
        volume.setCellValueFactory(new PropertyValueFactory<Aquarium, Integer>("volumeInLiters"));
        temperatuur.setCellValueFactory(new PropertyValueFactory<Aquarium, Integer>("temperatuur"));
        watertype.setCellValueFactory(new PropertyValueFactory<Aquarium, String>("waterType"));
        verversing.setCellValueFactory(new PropertyValueFactory<Aquarium, Integer>("aantalVerversingen"));
        procentPerVerversing.setCellValueFactory(new PropertyValueFactory<Aquarium, Integer>("procentWaterPerVerversing"));
        aquariums.getItems().setAll(aquariumDAO.getAquariumOfGebruiker(gebruiker.getNaam()));
    }
}
