package interfaces;

public interface MetroAPI {
    StationAPI[] getAllStations();
    StationAPI getStation(Integer ID);
    PathAPI getPath(Integer startStationID , Integer intendStationID);
}
