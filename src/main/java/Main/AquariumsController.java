package Main;

import Domain.Aquarium;
import Domain.Gebruiker;
import datasource.AquariumDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    }

    private void initinalizeTableView() throws SQLException {
        aquariumID.setCellValueFactory(new PropertyValueFactory<Aquarium, Integer>("aquariumId"));
        volume.setCellValueFactory(new PropertyValueFactory<Aquarium, Integer>("volumeInLiters"));
        temperatuur.setCellValueFactory(new PropertyValueFactory<Aquarium, Integer>("temperatuur"));
        watertype.setCellValueFactory(new PropertyValueFactory<Aquarium, String>("waterType"));
        verversing.setCellValueFactory(new PropertyValueFactory<Aquarium, Integer>("aantalVerversingen"));
        procentPerVerversing.setCellValueFactory(new PropertyValueFactory<Aquarium, Integer>("procentWaterPerVerversing"));
        aquariums.getItems().setAll(aquariumDAO.getAquariumOfGebruiker(gebruiker.getGebruikerId()));
    }
}
