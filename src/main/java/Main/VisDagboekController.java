package Main;


import Domain.Analyse;
import Domain.Vis;
import datasource.SymptoomDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VisDagboekController implements Initializable {
    private SymptoomDAO symptoomDAO;
    @FXML Button symptomenToevoegen;
    @FXML Button analyse;
    private Vis vis;

    public VisDagboekController(Vis vis) {
        this.vis = vis;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            symptoomDAO = new SymptoomDAO();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        symptomenToevoegen.setOnAction(event -> {
            VoegSymptoomToeController controller = null;
            try {
                controller = new VoegSymptoomToeController(vis);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            openPane(symptomenToevoegen,"symptomentoevoegen.fxml",controller);
        });

        analyse.setOnAction(event -> {
            ArrayList<Analyse> analyses = null;
            try {
                analyses = symptoomDAO.doAnalyse(vis.getVisId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(analyses != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("analyse.fxml"));
                AnalyseController controller = new AnalyseController(analyses);
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
                stage.initOwner(analyse.getScene().getWindow());
                stage.initModality(Modality.WINDOW_MODAL);
                stage.show();
            }
        });
    }

    private void openPane(Button button, String fxml, Initializable initializable) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxml));
        loader.setController(initializable);
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root, 1280 , 720);
        stage.setScene(scene);
        stage.initOwner(button.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }
}
