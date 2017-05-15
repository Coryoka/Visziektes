package Main;

import Domain.Gebruiker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    private Gebruiker gebruiker;
    @FXML Button showAquariums;

    public Controller(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showAquariums.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("aquariums.fxml"));
                AquariumsController controller = new AquariumsController(gebruiker);
                loader.setController(controller);
                Stage stage = (Stage)showAquariums.getScene().getWindow();
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


    }
}
