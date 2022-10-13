package backend;

import common.Line;
import interfaces.StationAPI;

import java.util.HashMap;
import java.util.Objects;

public class Station implements StationAPI {
    private final Integer ID;
    private final String name;
    private final Integer[] adjacentStations;
    private final HashMap<Integer, Double> adjacentCosts;
    private final HashMap<Integer, Line> adjacentLines;


    public Station(Integer ID, String name, Integer[] adjacentStations, HashMap<Integer, Line> adjacentLines) {
        this.ID = ID;
        this.name = name;
        this.adjacentStations = adjacentStations;
        // add adjacent lines
        this.adjacentLines = new HashMap<>();
        for (Integer i : adjacentLines.keySet()) {
            this.adjacentLines.put(i, adjacentLines.get(i));
        }
        // generate adjacent costs based on lines
        this.adjacentCosts = new HashMap<>();
        // for each adjacent station
        for (Integer adjacentID : adjacentStations) {
            Double cost = Line.getCostForLine(getLineForAdjacentStation(adjacentID));
            adjacentCosts.put(adjacentID, cost);
        }
    }

    @Override
    public Integer getID() {
        return ID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer[] getAdjacentStations() {
        return adjacentStations;
    }

    @Override
    public Line getLineForAdjacentStation(Integer adjacentID) {
        return adjacentLines.get(adjacentID);
    }

    @Override
    public Double getCostForAdjacentStation(Integer adjacentID) {
        return adjacentCosts.get(adjacentID);
    }

    @Override
    public String toString() {
        // regex from https://stackoverflow.com/questions/20677326/how-to-add-whitepaces-between-each-capital-letter
        String withSpaces = this.getName().replaceAll("\\d+", "").replaceAll("(.)([A-Z])", "$1 $2");

        return withSpaces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return getID().equals(station.getID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID());
    }

    @Override
    public int compareTo(StationAPI station) {
        return this.getName().compareTo(station.getName());
    }
}

