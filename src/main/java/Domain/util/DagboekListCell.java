//package Domain.util;
//
//import Domain.Aquarium;
//import Domain.AquariumDagOpname;
//import Domain.Meting;
//import Domain.Tijd;
//import javafx.geometry.Insets;
//import javafx.scene.control.Label;
//import javafx.scene.control.ListCell;
//import javafx.scene.layout.GridPane;
//
//import java.sql.Time;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
//public class DagboekListCell extends ListCell<AquariumDagOpname> {
//    private GridPane grid;
//    private Label dag;
//    private ArrayList<Label> tijden;
//    private List<List<Label>> metingen = new ArrayList<List<Label>>(30);
//    private ArrayList<Label> opmerkingen;
//
//    public DagboekListCell() {
//        for(int i = 0; i< 20; i++){
//            metingen.add(new ArrayList<Label>());
//        }
//    }
//
//    private void configureGrid() {
//        grid.setHgap(10);
//        grid.setVgap(4);
//        grid.setPadding(new Insets(5, 5, 5, 5));
//    }
//
//    @Override
//    public void updateItem(AquariumDagOpname opname, boolean empty) {
//        if (empty) {
//            clearContent();
//        } else {
//            configureCell();
//
//            Collections.sort(opname.getOpnames(), new Comparator<Tijd>() {
//                public int compare(Tijd o1, Tijd o2) {
//                    return o1.getTime().compareTo(o2.getTime());
//                }
//            });
//
//            Time tijd1 = null;
//            int currentMeting = -1;
//            for (Tijd tijd : opname.getOpnames()){
//                if(tijd1 != null){
//                    if(tijd.getTime().equals(tijd1.getTime())){
//                        currentMeting++;
//                        if(tijd instanceof Meting) {
//                           // metingen.get(currentMeting).add();
//                        }
//                    } else {
//                        tijden.add(new Label(tijd.getTime().toString()));
//                    }
//                }
//                tijd1 = tijd.getTime();
//
//            }
//        }
//    }
//
//    private void clearContent() {
//        setText(null);
//        setGraphic(null);
//    }
//
//
//    private void configureCell() {
//        grid  = new GridPane();
//        dag = new Label();
//        tijden = new ArrayList<Label>();
//        opmerkingen = new ArrayList<Label>();
//
//        configureGrid();
//    }
//
//
//}
