package Main;

import Domain.Aquarium;
import Domain.AquariumDagOpname;
import Domain.Meting;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ChartController implements Initializable {
    private String variabel;
    private ArrayList<AquariumDagOpname> opnames;

    @FXML NumberAxis numberAxis;
    @FXML CategoryAxis categoryAxis;
    @FXML LineChart waardeGeschiedenis;


    public ChartController(String variabel, ArrayList<AquariumDagOpname> opnames) {
        this.variabel = variabel;
        this.opnames = opnames;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoryAxis.setLabel("Tijd");
        numberAxis.setLabel("Waarde");
        waardeGeschiedenis.setTitle("Geschiedenis van: " + variabel);
        Collections.sort(opnames, new Comparator<AquariumDagOpname>() {
            public int compare(AquariumDagOpname o1, AquariumDagOpname o2) {
                return o1.getDag().compareTo(o2.getDag());
            }
        });

        XYChart.Series series = new XYChart.Series();
        for (AquariumDagOpname opname : opnames){
            if(opname.getOpname().getMetingen() != null){
                for(Meting meting : opname.getOpname().getMetingen()){
                    if(meting.getVariabeleId().equals(variabel)){
                        series.getData().add(new XYChart.Data(opname.getDag().toString(), meting.getWaarde()));
                    }
                }
            }
        }
        waardeGeschiedenis.getData().add(series);
    }
}
