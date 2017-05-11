package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML Button homeButton;
    @FXML Text welcomeText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(welcomeText.getText().equals("text")){
                    welcomeText.setText("welcome");
                } else {
                    welcomeText.setText("text");
                }
            }
        });
    }
}
