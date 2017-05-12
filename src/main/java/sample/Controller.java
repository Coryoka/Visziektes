package sample;

import Domain.Aquarium;
import datasource.AquariumDAO;
import datasource.DAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    private AquariumDAO aquariumDAO;
    @FXML Button homeButton;
    @FXML Text welcomeText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (welcomeText.getText().equals("text")) {
                    welcomeText.setText("welcome");
                } else {
                    welcomeText.setText("text");
                }
            }
        });

        try {
            aquariumDAO = new AquariumDAO();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(aquariumDAO.getAquariumOfGebruiker(1).get(0).getAqLengte());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
