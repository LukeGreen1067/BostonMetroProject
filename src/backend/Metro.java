package backend;

import common.Line;
import interfaces.MetroAPI;
import interfaces.PathAPI;
import interfaces.StationAPI;

import java.io.File;
import java.util.*;

public class Metro implements MetroAPI {

    Station[] stations;

    public Metro() {
        FileLoader loader = new FileLoader(new File("bostonmetro.txt"));
        stations = loader.loadData();
    }

    @Override
    public StationAPI[] getAllStations() {
        return this.stations;
    }

    @Override
    public StationAPI getStation(Integer ID) {
        for (Station station : stations) {
            if (station.getID().equals(ID)) {
                return station;
            }
        }
        return null;
    }

    @Override
    public PathAPI getPath(Integer startStationID, Integer endStationID) {

        // initialise station costs to 0
        HashMap<Integer, Double> cumulativeCosts = new HashMap<>();
        cumulativeCosts.put(startStationID, 0.0);

        // map of preceding station for each station in format <successor: predecessor>
        HashMap<Integer, Integer> ancestors = new HashMap<>();
        ancestors.put(startStationID, 0);
        // our agenda (queue) with starting node
        ArrayList<StationAPI> agenda = new ArrayList<>();
        agenda.add(getStation(startStationID));

        while (!agenda.isEmpty()) {
            // sort agenda items by cost
            agenda.sort((station1, station2) -> {
                Double cost1 = cumulativeCosts.get(station1.getID());
                Double cost2 = cumulativeCosts.get(station2.getID());
                return cost1.compareTo(cost2);
            });
            // get item at front
            StationAPI current = agenda.remove(0);
            if (current.getID().equals(endStationID)) {
                break;
            }
            ArrayList<StationAPI> next = new ArrayList<>();
            Integer[] adjacentIDs = current.getAdjacentStations();
            // add neighbours to agenda if they haven't been visited or were visited with a higher score
            for (int i = 0; i < adjacentIDs.length; i++) {
                Integer adjacentID = adjacentIDs[i];
                StationAPI nextStation = getStation(adjacentID);
                Double nextCost = cumulativeCosts.get(current.getID()) + current.getCostForAdjacentStation(adjacentID);
                if (!cumulativeCosts.containsKey(adjacentID) || cumulativeCosts.get(adjacentID) > nextCost) {
                    next.add(nextStation);
                    ancestors.put(adjacentID, current.getID());
                    cumulativeCosts.put(adjacentID, nextCost);
                }
            }
            agenda.addAll(next);
        }

        Path path = new Path();
        Integer target = endStationID;
        double totalCost = 0.0;

        // while we have an ancestor, that is we haven't reached the start node
        while (true) {
            StationAPI currentTarget = getStation(target);
            // add path
            path.stationsOnPath.add(currentTarget);
            if (ancestors.get(target).equals(0)) {
                break;
            }
            // add spanned lines
            Integer nextTargetID = ancestors.get(target);
            StationAPI nextTarget = getStation(nextTargetID);
            Line l = currentTarget.getLineForAdjacentStation(nextTarget.getID());
            path.spannedLines.add(l);
            totalCost += currentTarget.getCostForAdjacentStation(nextTarget.getID());
            target = nextTargetID;
        }
        path.minutesTaken = totalCost;
        Collections.reverse(path.stationsOnPath);

        return path;
    }

    public void printStations() {
        for (Station s : stations) {
            System.out.println(s);
        }
    }
}
