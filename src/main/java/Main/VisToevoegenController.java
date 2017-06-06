package Main;

import Domain.Vissoort;
import Domain.VissenInAquarium;
import datasource.VissenDAO;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VisToevoegenController implements Initializable {
    private DagboekController dagboekController;
    private VissenDAO vissenDAO;
    private VissenInAquarium vissenInAquarium;
    @FXML ChoiceBox<Vissoort> vissen;
    @FXML LocalDateTimeTextField datumToevoeging;
    @FXML TextField aantalVissen;
    @FXML TextArea leverancier;
    @FXML Button opslaan;
    @FXML Button verwijderen;

    public VisToevoegenController(DagboekController dagboekController, VissenDAO vissenDAO, VissenInAquarium vissenInAquarium) {
        this.dagboekController = dagboekController;
        this.vissenDAO = vissenDAO;
        this.vissenInAquarium = vissenInAquarium;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Vissoort> vissenlist = new ArrayList<>();
        try {
            vissenlist.addAll(vissenDAO.alleVissen());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(vissenInAquarium != null){
            Vissoort vis = null;
            for(Vissoort vis1: vissenlist){
                if(vis1.getGenus().equals(vissenInAquarium.getGenus()) && vis1.getSoort().equals(vissenInAquarium.getSoort())){
                    vis = vis1;
                }
            }
            vissen.getItems().setAll(vissenlist);
            vissen.setValue(vis);
            vissen.valueProperty().setValue(vis);
            datumToevoeging.setLocalDateTime(vissenInAquarium.getDatumToegevoegd().toLocalDateTime());
            aantalVissen.setText(vissenInAquarium.getHuidigeVissen() +"");
            leverancier.setText(vissenInAquarium.getLeverancier());
        } else {
            vissen.getItems().setAll(vissenlist);
        }

        opslaan.setOnAction(event -> {
            String error = null;
            if(vissenInAquarium == null) {
                if (vissen.getSelectionModel().getSelectedItem() != null && datumToevoeging.getLocalDateTime() != null
                        && aantalVissen != null && leverancier != null) {
                    VissenInAquarium vissenInAquarium = getVissenInAquarium();
                    try {
                        error = vissenDAO.saveVisInAquarium(dagboekController.getAquariumId(), vissenInAquarium);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    showWarning(error);
                    dagboekController.laadVissenTableView();
                    closeStage();
                }
            } else {

                try {
                    VissenInAquarium vissenInAquarium1 = getVissenInAquarium();
                    vissenInAquarium1.setGroupId(vissenInAquarium.getGroupId());
                    error = vissenDAO.updateVisInAquarium(vissenInAquarium1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                showWarning(error);
                dagboekController.laadVissenTableView();
                closeStage();
            }
        });

        verwijderen.setOnAction(event -> {
            if(vissenInAquarium != null){
                try {
                    vissenDAO.deleteVissenInAquarium(vissenInAquarium);
                    dagboekController.laadVissenTableView();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                closeStage();
            }
        });

    }

    private VissenInAquarium getVissenInAquarium() {
        ZonedDateTime zdt = datumToevoeging.getLocalDateTime().atZone(ZoneId.of("Europe/Amsterdam"));
        Timestamp dag = new Timestamp(zdt.toInstant().toEpochMilli());
        return new VissenInAquarium(vissen.getSelectionModel().getSelectedItem(),
                dag, Integer.parseInt(aantalVissen.getText()), leverancier.getText());
    }

    public void closeStage() {
        Stage stage = (Stage) opslaan.getScene().getWindow();
        stage.close();
    }

    public void showWarning(String error) {
        if (error != null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Waarschuwing");
            alert.setHeaderText(null);
            alert.setContentText(error);
            alert.showAndWait();
        }
    }
}
