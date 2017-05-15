package Main;

import Domain.Gebruiker;
import datasource.GebruikerDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private GebruikerDAO gebruikerDAO;
    @FXML TextField gebruikersNaam;
    @FXML PasswordField wachtwoord;
    @FXML Button inlogButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inlogButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    gebruikerDAO = new GebruikerDAO();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                String naam = gebruikersNaam.getText();
                String ww = wachtwoord.getText();
                if(naam != null && ww != null){
                    Gebruiker gebruiker = null;
                    try {
                        gebruiker = gebruikerDAO.getGebruiker(naam, ww);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (gebruiker != null){
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("home.fxml"));
                        Controller controller = new Controller(gebruiker);
                        loader.setController(controller);
                        Stage stage = (Stage)inlogButton.getScene().getWindow();
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
                }
            }
        });
    }
}
