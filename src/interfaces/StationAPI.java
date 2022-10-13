package interfaces;

import common.Line;

import java.util.HashMap;

public interface StationAPI extends Comparable<StationAPI>{
    Integer getID();
    String getName();
    Integer[] getAdjacentStations();
    Double getCostForAdjacentStation(Integer adjacentID);
    Line getLineForAdjacentStation(Integer adjacentID);
}
