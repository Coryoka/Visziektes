package Main;

import Domain.Vis;
import Domain.VissenInAquarium;
import datasource.VissenDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import jfxtras.scene.control.LocalDateTimeTextField;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class VisToevoegenController implements Initializable {
    private DagboekController dagboekController;
    private VissenDAO vissenDAO;
    @FXML ChoiceBox<Vis> vissen;
    @FXML LocalDateTimeTextField datumToevoeging;
    @FXML TextField aantalVissen;
    @FXML TextArea leverancier;
    @FXML Button opslaan;

    public VisToevoegenController(DagboekController dagboekController, VissenDAO vissenDAO) {
        this.dagboekController = dagboekController;
        this.vissenDAO = vissenDAO;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            vissen.getItems().setAll(vissenDAO.alleVissen());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        opslaan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(vissen.getSelectionModel().getSelectedItem() != null && datumToevoeging.getLocalDateTime() != null
                        && aantalVissen != null && leverancier != null){
                    ZonedDateTime zdt = datumToevoeging.getLocalDateTime().atZone(ZoneId.of("Europe/Amsterdam"));
                    Timestamp dag = new Timestamp(zdt.toInstant().toEpochMilli());
                    VissenInAquarium vissenInAquarium = new VissenInAquarium(vissen.getSelectionModel().getSelectedItem(),
                            dag, Integer.parseInt(aantalVissen.getText()), leverancier.getText());
                    try {
                        vissenDAO.saveVisInAquarium(dagboekController.getAquariumId(), vissenInAquarium);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    dagboekController.laadVissenTableView();
                    closeStage();
                }
            }
        });

    }
    public void closeStage() {
        Stage stage = (Stage) opslaan.getScene().getWindow();
        stage.close();
    }
}
