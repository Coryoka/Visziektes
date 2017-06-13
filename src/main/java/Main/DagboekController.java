package Main;

import Domain.*;
import datasource.AquariumDAO;
import datasource.VissenDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DagboekController implements Initializable {
    private AquariumDAO aquariumDAO;
    private VissenDAO vissenDAO;
    @FXML TableView<AquariumDagOpname> dagboekTableView;
    @FXML TableColumn<AquariumDagOpname, String> dagKolom;
    @FXML TableColumn<AquariumDagOpname, String> tijdKolom;
    private ArrayList<TableColumn<AquariumDagOpname,String>> kolommen = new ArrayList<>();
    @FXML TableColumn<String, AquariumDagOpname> opmerkingKolom;
    @FXML TableColumn<String, AquariumDagOpname> voeding;
    @FXML Button nieuweInvoer;
    @FXML Button terug;
    private int aquariumId;
    private Aquarium aquarium;
    private Gebruiker gebruiker;

    @FXML TableView<VissenInAquarium> vissenInAquarium;
    @FXML TableColumn visGenus;
    @FXML TableColumn vissoort;
    @FXML TableColumn datumToegevoegd;
    @FXML TableColumn aantalHuidigeVissen;
    @FXML TableColumn aantalOorspronkelijkeVissen;
    @FXML TableColumn leverancier;
    @FXML Button vissenToevoegen;
    @FXML Button ButtonMeters;
    @FXML Button ButtonPompen;
    @FXML Button ButtonFilters;
    @FXML Button ButtonLichten;

    @FXML TableView<Vis> individueleVissen;
    @FXML TableColumn naamVis;
    @FXML TableColumn geboorteJaar;
    @FXML TableColumn opmerkingVis;
    @FXML Button nieuweVis;




    public DagboekController(int aquariumId, Gebruiker gebruiker) {
        this.aquariumId = aquariumId;
        this.gebruiker = gebruiker;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            aquariumDAO = new AquariumDAO();
            vissenDAO = new VissenDAO();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ButtonMeters.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Meters.fxml"));
                MetersController controller = new MetersController(aquariumId,gebruiker);
                loader.setController(controller);
                Stage stage = (Stage)ButtonMeters.getScene().getWindow();
                Parent root = null;
                try {
                    root = loader.load();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(root, 1280, 720);
                stage.setScene(scene);
                stage.show();

            }
        });
        ButtonPompen.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Pompen.fxml"));
                PompenController controller = new PompenController(aquariumId,gebruiker);
                loader.setController(controller);
                Stage stage = (Stage)ButtonMeters.getScene().getWindow();
                Parent root = null;
                try {
                    root = loader.load();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(root, 1280, 720);
                stage.setScene(scene);
                stage.show();

            }
        });
        ButtonFilters.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Filters.fxml"));
                FilterController controller = new FilterController(aquariumId,gebruiker);
                loader.setController(controller);
                Stage stage = (Stage)ButtonMeters.getScene().getWindow();
                Parent root = null;
                try {
                    root = loader.load();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(root, 1280, 720);
                stage.setScene(scene);
                stage.show();

            }
        });
        ButtonLichten.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Verlichting.fxml"));
                VerlichtingController controller = new VerlichtingController(aquariumId,gebruiker);
                loader.setController(controller);
                Stage stage = (Stage)ButtonMeters.getScene().getWindow();
                Parent root = null;
                try {
                    root = loader.load();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(root, 1280, 720);
                stage.setScene(scene);
                stage.show();

            }
        });


        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        dagKolom.setCellValueFactory( AquariumDagOpname -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(format.format(AquariumDagOpname.getValue().getDag()));
            return property;
        });
        tijdKolom.setCellValueFactory(new PropertyValueFactory<AquariumDagOpname, String>("tijd"));
        opmerkingKolom.setCellValueFactory(new PropertyValueFactory<String, AquariumDagOpname>("opmerking"));
        voeding.setCellValueFactory(new PropertyValueFactory<String, AquariumDagOpname>("voeding"));

        laadTableView();
        laadVissenTableView();
        laadIndividueleVissenTableView();


        nieuweInvoer.setOnAction(event -> {
            NieuwInvoerController controller = new NieuwInvoerController(aquariumId, getThis());
            openPane(nieuweInvoer, "dagboekInvoer.fxml", controller);
        });

        vissenToevoegen.setOnAction(event -> {
            VisToevoegenController controller = new VisToevoegenController(getThis(), vissenDAO, null);
            openPane(vissenToevoegen, "nieuweVissen.fxml", controller);
        });

        terug.setOnAction(event -> {
            AquariumsController controller = new AquariumsController(gebruiker);
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("aquariums.fxml"));
            loader.setController(controller);
            Stage stage = (Stage)terug.getScene().getWindow();
            Parent root = null;
            try {
                root = loader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root, 1280, 720);
            stage.setScene(scene);
            stage.show();

        });

        nieuweVis.setOnAction(event -> {
            if(vissenInAquarium.getSelectionModel().getSelectedItem() != null) {
                VissenInAquarium vissenInAquarium1 = vissenInAquarium.getSelectionModel().getSelectedItem();
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("nieuweIndividuelevis.fxml"));
                NieuweIndividueleVisController controller = new NieuweIndividueleVisController(vissenInAquarium1, aquariumId, vissenDAO, getThis());
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
                stage.initOwner(nieuweInvoer.getScene().getWindow());
                stage.initModality(Modality.WINDOW_MODAL);
                stage.show();
            }
        });

    }

    public void laadIndividueleVissenTableView() {
        naamVis.setCellFactory(getCellfactoryIndividueleVissen());
        naamVis.setCellValueFactory(new PropertyValueFactory<>("naam"));
        geboorteJaar.setCellFactory(getCellfactoryIndividueleVissen());
        geboorteJaar.setCellValueFactory(new PropertyValueFactory<>("geboortejaar"));
        opmerkingVis.setCellFactory(getCellfactoryIndividueleVissen());
        opmerkingVis.setCellValueFactory(new PropertyValueFactory<>("opmerking"));
        try {
            individueleVissen.getItems().setAll(vissenDAO.individueleVissenBijAquarium(aquariumId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        Scene scene = new Scene(root, 800, 480);
        stage.setScene(scene);
        stage.initOwner(button.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    public void laadVissenTableView() {
        visGenus.setCellFactory(getCellfactoryVis());
        visGenus.setCellValueFactory(new PropertyValueFactory<>("genus"));
        vissoort.setCellFactory(getCellfactoryVis());
        vissoort.setCellValueFactory(new PropertyValueFactory<>("soort"));
        datumToegevoegd.setCellFactory(getCellfactoryVis());
        datumToegevoegd.setCellValueFactory(new PropertyValueFactory<>("datumToevoeging"));
        aantalHuidigeVissen.setCellFactory(getCellfactoryVis());
        aantalHuidigeVissen.setCellValueFactory(new PropertyValueFactory<>("huidigeVissen"));
        aantalOorspronkelijkeVissen.setCellFactory(getCellfactoryVis());
        aantalOorspronkelijkeVissen.setCellValueFactory(new PropertyValueFactory<>("oorspronkelijkeVissen"));
        leverancier.setCellFactory(getCellfactoryVis());
        leverancier.setCellValueFactory(new PropertyValueFactory<>("leverancier"));
        datumToegevoegd.setSortType(TableColumn.SortType.DESCENDING);
        try {
            aquarium.setVissenInAquarium(vissenDAO.getVissenVanAquarium(aquariumId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        vissenInAquarium.getItems().setAll(aquarium.getVissenInAquarium());
        vissenInAquarium.getSortOrder().add(datumToegevoegd);
    }

    public DagboekController getThis() {
        return this;
    }

    public void laadTableView() {
        try {
            aquarium = aquariumDAO.getAquarium(aquariumId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        dagKolom.setSortType(TableColumn.SortType.DESCENDING);
        tijdKolom.setSortType(TableColumn.SortType.DESCENDING);

        dagboekTableView.getColumns().removeAll(kolommen);
        kolommen = new ArrayList<>();
        for (String variabel: aquarium.metingVariabelen()) {
            if(!kolommen.contains(new TableColumn(variabel))) {
                kolommen.add(new TableColumn(variabel));
            }
        }
        dagboekTableView.getColumns().addAll(kolommen);

        for(TableColumn kolom: kolommen){
            kolom.setCellFactory(getCellfactory());
            kolom.setCellValueFactory(new PropertyValueFactory<String, AquariumDagOpname>(kolom.getText()));
        }

        dagboekTableView.getItems().setAll(aquarium.getDagboek());
        dagboekTableView.getSortOrder().add(dagKolom);
        dagboekTableView.getSortOrder().add(tijdKolom);
    }

    public Callback<TableColumn, TableCell> getCellfactory() {
        Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        TableCell cell = new TableCell<AquariumDagOpname, String>() {
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                setText(empty ? null : getString());
                                setGraphic(null);
                            }

                            private String getString() {
                                return getItem() == null ? "" : getItem().toString();
                            }
                        };

                        cell.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                            if (event.getClickCount() > 1) {
                                showChart(cell.getTableColumn());
                            }
                        });
                        return cell;
                    }
                };
        return cellFactory;
    }
    public Callback<TableColumn, TableCell> getCellfactoryIndividueleVissen() {
        Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        TableCell cell = new TableCell<Vis, Object>() {
                            @Override
                            public void updateItem(Object item, boolean empty) {
                                super.updateItem(item, empty);
                                setText(empty ? null : getString());
                                setGraphic(null);
                            }

                            private String getString() {
                                return getItem() == null ? "" : getItem().toString();
                            }
                        };

                        cell.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                            if (event.getClickCount() > 1) {
                                VisDagboekController controller = new VisDagboekController(individueleVissen.getSelectionModel().getSelectedItem());
                                openPane(nieuweInvoer,"visDagboek.fxml", controller);
                            }
                        });
                        return cell;
                    }
                };
        return cellFactory;
    }

    public Callback<TableColumn, TableCell> getCellfactoryVis() {
        Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        TableCell cell = new TableCell<VissenInAquarium, Object>() {
                            @Override
                            public void updateItem(Object item, boolean empty) {
                                super.updateItem(item, empty);
                                setText(empty ? null : getString());
                                setGraphic(null);
                            }

                            private String getString() {
                                return getItem() == null ? "" : getItem().toString();
                            }
                        };

                        cell.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                            if (event.getClickCount() > 1) {
                                VisToevoegenController controller = new VisToevoegenController(getThis(),vissenDAO,vissenInAquarium.getSelectionModel().getSelectedItem());
                                openPane(nieuweInvoer,"nieuweVissen.fxml", controller);
                            }
                        });
                        return cell;
                    }
                };
        return cellFactory;
    }

    public void showChart(TableColumn column){
        String variabel = column.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("waardeGeschiedenis.fxml"));
        ChartController controller = new ChartController(variabel,aquarium.getDagboek());
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
        stage.initOwner(nieuweInvoer.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();


    }

    public int getAquariumId() {
        return aquariumId;
    }
}
