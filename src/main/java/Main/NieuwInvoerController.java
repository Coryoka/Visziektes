package Main;

import Domain.AquariumDagOpname;
import Domain.Meting;
import Domain.Opname;
import Domain.Voeding;
import datasource.AquariumDagOpnameDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import jfxtras.scene.control.LocalDateTimeTextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NieuwInvoerController implements Initializable {
    private AquariumDagOpnameDAO aquariumDagOpnameDAO;
    private int aquariumId;
    private DagboekController controller;
    @FXML TextArea opmerking;
    @FXML TextArea voeding;
    @FXML LocalDateTimeTextField datumTijd;
    @FXML Button opslaan;

    @FXML TextField PH;
    @FXML TextField O2;
    @FXML TextField Temp;
    @FXML TextField naam;
    @FXML TextField dosering;

    public NieuwInvoerController(int aquariumId, DagboekController controller) {
        this.aquariumId = aquariumId;
        this.controller = controller;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            aquariumDagOpnameDAO = new AquariumDagOpnameDAO();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        opslaan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(datumTijd.getLocalDateTime() != null){
                    Opname opname = new Opname();

                    opname.setTijd(new Time((datumTijd.getLocalDateTime().getLong(ChronoField.SECOND_OF_DAY) *1000) - 3600000));
                    opname.setOpmerking(opmerking.getText());

                    ArrayList<Meting> metingen = new ArrayList<>();
                    if(!PH.getText().equals("")) {
                        metingen.add(new Meting("PH", "PH", Integer.parseInt(PH.getText())));
                    }
                    if(!O2.getText().equals("")) {
                        metingen.add(new Meting("O2", "O2", Integer.parseInt(O2.getText())));
                    }
                    if(!Temp.getText().equals("")) {
                        metingen.add(new Meting("Temp", "Temp", Integer.parseInt(Temp.getText())));
                    }
                    if(metingen.size() > 0) {
                        opname.setMetingen(metingen);
                    }
                    if(!naam.getText().equals("")) {
                        opname.setVoeding(new Voeding(naam.getText(), dosering.getText()));
                    }
                    ZonedDateTime zdt = datumTijd.getLocalDateTime().atZone(ZoneId.of("Europe/Amsterdam"));
                    Timestamp dag = new Timestamp(zdt.toInstant().toEpochMilli());

                    try {
                        aquariumDagOpnameDAO.saveInvoer(opname,aquariumId, dag);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    controller.laadTableView();
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
