package frontend;

import backend.Metro;
import common.Line;
import interfaces.MetroAPI;
import interfaces.PathAPI;
import interfaces.StationAPI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class Controller {

    StationAPI[] stations;
    MetroAPI metro;
    StationAPI fromStation;
    StationAPI toStation;
    PathAPI path;

    @FXML
    private Label timeTaken;

    @FXML
    private Label startStation;

    @FXML
    private ListView<Line> linesList;

    @FXML
    private Label Lines;

    @FXML
    private ListView<StationAPI> plannedStations;

    @FXML
    private Label yourJourney;

    @FXML
    private ComboBox<StationAPI> fromStationBox;

    @FXML
    private ComboBox<StationAPI> toStationBox;

    @FXML
    public void getRoutePressed() {
        fromStation = fromStationBox.getValue();
        toStation = toStationBox.getValue();
        if(fromStation == null || toStation == null) {
            return;
        }
        path = metro.getPath(fromStation.getID(), toStation.getID());
        String minutesString = "Your journey will take " + path.getMinutesTaken() + " minutes.";
        timeTaken.setText(minutesString);
        startStation.setText("Your start station is " + fromStation.toString() + " and your end station is " + toStation.toString() + ".");
        linesList.setItems(FXCollections.observableArrayList(path.getSpannedLines()));
        linesList.setVisible(true);
        Lines.setVisible(true);
        plannedStations.setItems(FXCollections.observableArrayList(path.getStations()));
        plannedStations.setVisible(true);
        yourJourney.setVisible(true);
    }

    @FXML
    public void initialize(){
        metro = new Metro();
        stations = metro.getAllStations();

        // Initialise combo boxes
        ObservableList<StationAPI> stationsList  = FXCollections.observableArrayList(stations);
        FXCollections.sort(stationsList);
        fromStationBox.setItems(stationsList);
        toStationBox.setItems(stationsList);
    }

}
