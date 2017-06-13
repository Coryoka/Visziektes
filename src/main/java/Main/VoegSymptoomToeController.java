package Main;

import Domain.Analyse;
import Domain.Symptoom;
import Domain.SymptoomInVis;
import Domain.Vis;
import datasource.SymptoomDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.scene.control.LocalDateTimeTextField;

;import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VoegSymptoomToeController implements Initializable {
    private SymptoomDAO symptoomDAO;
    @FXML ListView<Symptoom> symptomen;
    @FXML ListView<SymptoomInVis> symptomenInVis;
    @FXML Button symptoomToevoegen;
    @FXML Button symptoomVerwijderen;
    @FXML Button opslaan;
    @FXML LocalDateTimeTextField eersteWaarneming;
    @FXML TextField intensiteit;
    private Vis vis;

    public VoegSymptoomToeController(Vis vis) throws IOException, ClassNotFoundException {
        this.vis = vis;
        symptoomDAO = new SymptoomDAO();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Symptoom> symptooms = new ArrayList<>();
        ArrayList<SymptoomInVis> symptoomInViss = new ArrayList<>();

        try {
            symptooms = symptoomDAO.getAllSymptomsOnBreed(vis, "Nederlands");
            symptoomInViss = symptoomDAO.getAllSymptomsOnFish(vis.getVisId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        fillListviews(symptooms, symptoomInViss);
        setCellFactorySymptomen();
        setCellFactorySymptomenInVis();

        symptoomToevoegen.setOnAction(event -> {
            if(symptomen.getSelectionModel().getSelectedItem()!=null && eersteWaarneming.getDisplayedLocalDateTime()!=null && intensiteit.getText() != null){
                Symptoom symptoom = symptomen.getSelectionModel().getSelectedItem();
                ZonedDateTime zdt = eersteWaarneming.getLocalDateTime().atZone(ZoneId.of("Europe/Amsterdam"));
                Timestamp dag = new Timestamp(zdt.toInstant().toEpochMilli());
                SymptoomInVis symptoomInVis = new SymptoomInVis(symptoom.getSymptoomId(), symptoom.getMediaUrls(), symptoom.getName(),dag, Integer.parseInt(intensiteit.getText()));
                symptomenInVis.getItems().add(symptoomInVis);
                symptomen.getItems().remove(symptoom);
            }
        });

        symptoomVerwijderen.setOnAction(event -> {
            if(symptomenInVis.getSelectionModel().getSelectedItem()!=null){
                SymptoomInVis symptoomInVis = symptomenInVis.getSelectionModel().getSelectedItem();
                symptomen.getItems().add(new Symptoom(symptoomInVis.getSymptoomId(), symptoomInVis.getMediaUrls(), symptoomInVis.getName()));
                symptomenInVis.getItems().remove(symptoomInVis);
            }
        });

        opslaan.setOnAction(event -> {
            try {
                symptoomDAO.saveSymptomsOnFish(new ArrayList<>(symptomenInVis.getItems()), vis.getVisId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    private void fillListviews(ArrayList<Symptoom> symptooms, ArrayList<SymptoomInVis> symptoomInViss) {
        symptomen.getItems().setAll(symptooms);
        for(Symptoom symptoom: symptooms){
            for(SymptoomInVis symptoomInVis: symptoomInViss){
                if(symptoomInVis.getSymptoomId() == symptoom.getSymptoomId()){
                    symptoomInVis.setMediaUrls(symptoom.getMediaUrls());
                    symptoomInVis.setName(symptoom.getName());
                    symptomenInVis.getItems().add(symptoomInVis);
                    symptomen.getItems().remove(symptoom);
                }
            }
        }
    }

    private void setCellFactorySymptomen() {
        symptomen.setCellFactory(param -> new ListCell<Symptoom>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(Symptoom symptoom, boolean empty) {
                super.updateItem(symptoom, empty);
                imageView.setFitHeight(100);
                imageView.setPreserveRatio(true);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if(symptoom.getMediaUrls()!= null) {
                        imageView.setImage(new Image(symptoom.getMediaUrls()));
                    }
                    setText(symptoom.getName());
                    setGraphic(imageView);
                    setOnMouseClicked(event -> {
                        if(event.getClickCount() > 1) {
                            FXMLLoader loader = new FXMLLoader(getThisf().getClass().getClassLoader().getResource("symptoom.fxml"));
                            SymptoomViewController controller = new SymptoomViewController(new Image(getItem().getMediaUrls()));
                            loader.setController(controller);
                            Parent root = null;
                            try {
                                root = loader.load();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Stage stage = new Stage();
                            Scene scene = new Scene(root, 800, 480);
                            stage.setScene(scene);
                            stage.initOwner(symptomen.getScene().getWindow());
                            stage.initModality(Modality.WINDOW_MODAL);
                            stage.show();
                        }
                    });

                }
            }
        });
    }
    private void setCellFactorySymptomenInVis() {
        symptomenInVis.setCellFactory(param -> new ListCell<SymptoomInVis>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(SymptoomInVis symptoom, boolean empty) {
                super.updateItem(symptoom, empty);
                imageView.setFitHeight(100);
                imageView.setPreserveRatio(true);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if(symptoom.getMediaUrls()!=null) {
                        imageView.setImage(new Image(symptoom.getMediaUrls()));
                    }
                    setText("Date: " + symptoom.getEersteWaarneming() + " \n Intensity: " + symptoom.getIntensiteit() + " \n " + symptoom.getName());
                    setGraphic(imageView);
                }
            }
        });
    }

    private VoegSymptoomToeController getThisf(){
        return this;
    }
}
