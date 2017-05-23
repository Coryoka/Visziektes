package Main;

import Domain.Gebruiker;
import datasource.AquariumDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AquariumToevoegenController implements Initializable {
    private Gebruiker gebruiker;
    private AquariumDAO aquariumDAO;
    @FXML TextField VolumeInLiters;
    @FXML TextField Tempratuur;
    @FXML TextField AqWatertype;
    @FXML TextField AantalverversingenPerWeek;
    @FXML TextField ProcentwaterPerVerversing;
    @FXML TextField AqLengteInCm;
    @FXML TextField AqBreedteinCm;
    @FXML TextField AqHoogteInCm;
    @FXML Button AqAdd;
    @FXML Button  AqAnnuleren;

    public AquariumToevoegenController(Gebruiker gebruiker){this.gebruiker=gebruiker;}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            aquariumDAO = new AquariumDAO();
            AqAnnuleren.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("aquariums.fxml"));
                    AquariumsController controller = new AquariumsController(gebruiker);
                    loader.setController(controller);
                    Stage stage = (Stage)AqAnnuleren.getScene().getWindow();
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
            AqAdd.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        aquariumDAO.InsertAquarium(gebruiker.getNaam(),Integer.parseInt(VolumeInLiters.getText()),Integer.parseInt(Tempratuur.getText()),AqWatertype.getText(),Integer.parseInt(AantalverversingenPerWeek.getText()),
                                Integer.parseInt(ProcentwaterPerVerversing.getText()),Integer.parseInt(AqLengteInCm.getText()),Integer.parseInt(AqBreedteinCm.getText()),Integer.parseInt(AqHoogteInCm.getText()));
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("aquariums.fxml"));
                        AquariumsController controller = new AquariumsController(gebruiker);
                        loader.setController(controller);
                        Stage stage = (Stage)AqAdd.getScene().getWindow();
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Scene scene = new Scene(root, 1280, 720);
                        stage.setScene(scene);
                        stage.show();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
