package Main;

import Domain.Filter;
import datasource.AquariumDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jfxtras.scene.control.LocalDateTimeTextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

/**
 * Created by Cas on 13-6-2017.
 */
public class FilterMediumToevoegen implements Initializable{
    AquariumDAO aquariumDAO;
    Filter filter;
    int aquariumID;
    @FXML
    TextField naamApparaat;
    @FXML
    Button ButtonSave;
    @FXML
    LocalDateTimeTextField installatieDatum;
    @FXML
    LocalDateTimeTextField laatstSchoongemaakt;
    public FilterMediumToevoegen(int aquariumID, Filter filter) {
        this.filter = filter;
        this.aquariumID = aquariumID;
    }
    public void initialize(URL location, ResourceBundle resources) {
        ButtonSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    aquariumDAO = new AquariumDAO();
                    ZonedDateTime zdt = installatieDatum.getLocalDateTime().atZone(ZoneId.of("Europe/Amsterdam"));
                    Timestamp Installatiedag = new Timestamp(zdt.toInstant().toEpochMilli());
                    aquariumDAO.InsertMedium(aquariumID,filter.getNaamApparaat(),filter.getDatumToevoeging(),Installatiedag,naamApparaat.getText());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Stage stage = (Stage) ButtonSave.getScene().getWindow();
                stage.close();
            }
        });
    }
}
