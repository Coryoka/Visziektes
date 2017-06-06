package Main;

import Domain.Vis;
import Domain.VissenInAquarium;
import datasource.VissenDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jfxtras.scene.control.LocalDateTimeTextField;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class NieuweIndividueleVisController implements Initializable {
    @FXML TextField naamvis;
    @FXML TextField geboortejaar;
    @FXML LocalDateTimeTextField datumtoegevoegd;
    @FXML TextArea opmerking;
    @FXML Button opslaan;

    private VissenInAquarium vissenInAquarium;
    private int aquariumId;
    private VissenDAO vissenDAO;
    private DagboekController controller;

    public NieuweIndividueleVisController(VissenInAquarium vissenInAquarium, int aquariumId, VissenDAO vissenDAO, DagboekController controller) {
        this.vissenInAquarium = vissenInAquarium;
        this.aquariumId = aquariumId;
        this.vissenDAO = vissenDAO;
        this.controller = controller;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(vissenInAquarium != null){
            datumtoegevoegd.setLocalDateTime(vissenInAquarium.getDatumToegevoegd().toLocalDateTime());
        }
        opslaan.setOnAction(event -> {
            if(naamvis != null || geboortejaar != null || opmerking != null){
                Vis vis = new Vis(naamvis.getText(), opmerking.getText(), Integer.parseInt(geboortejaar.getText()), vissenInAquarium.getGroupId());
                ZonedDateTime zdt = datumtoegevoegd.getLocalDateTime().atZone(ZoneId.of("Europe/Amsterdam"));
                Timestamp dag = new Timestamp(zdt.toInstant().toEpochMilli());
                try {
                    vissenDAO.saveIndividueleVis(vis,dag, aquariumId);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            controller.laadIndividueleVissenTableView();
            closeStage();
        });

    }
    public void closeStage() {
        Stage stage = (Stage) opslaan.getScene().getWindow();
        stage.close();
    }
}
