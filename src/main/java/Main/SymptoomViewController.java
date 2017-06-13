package Main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class SymptoomViewController implements Initializable {
    private Image image;
    @FXML ImageView symptoom;
    @FXML Pane background;

    public SymptoomViewController(Image image) {
        this.image = image;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        symptoom.setImage(image);
        background.setLayoutY(symptoom.getY());
        background.setLayoutX(symptoom.getX());
    }
}
