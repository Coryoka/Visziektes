package Main;

import Domain.Analyse;
import Domain.AquariumDagOpname;
import Domain.Meting;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

public class AnalyseController implements Initializable {
    private ArrayList<Analyse> analyses;

    public AnalyseController(ArrayList<Analyse> analyses) {
        this.analyses = analyses;
    }

    @FXML
    NumberAxis waarde;
    @FXML
    CategoryAxis ziekte;
    @FXML
    BarChart analyse;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ziekte.setLabel("Disease");
        waarde.setLabel("Probabilty");
        analyse.setTitle("Analyses");

        Collections.sort(analyses, new Comparator<Analyse>() {
            @Override
            public int compare(Analyse o1, Analyse o2) {

                return o1.getZiekteID() - o2.getZiekteID();
            }
        });

        XYChart.Series series = new XYChart.Series();
        for(Analyse analyse: analyses){
            System.out.println("Analyse: " + analyse.getZiekteID() + ", " + analyse.getWaarschijnlijkheid() + ", " + analyse.getNaamZiekte());
            series.getData().add(new XYChart.Data(analyse.getNaamZiekte(), analyse.getWaarschijnlijkheid()));
        }

        analyse.getData().add(series);
    }
}
