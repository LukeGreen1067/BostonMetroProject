package backend;

import common.Line;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileLoader {
    private final File file;

    private final Integer MAX_LINES = 124;

    private StationLoader[] stationData = new StationLoader[MAX_LINES + 1];

    public FileLoader(File file) {
        this.file = file;
        for(int i=0; i<MAX_LINES + 1; i++) {
            stationData[i] = new StationLoader();
        }
    }

    public Station[] loadData() {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                StationLoader stationL = parseFileLineToStation(scanner.nextLine());
                stationData[stationL.id] = stationL;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Station> stations = new ArrayList<>();
        for(int i=1; i<stationData.length; i++) {
            StationLoader s = stationData[i];
            stations.add(new Station(
                    i, s.name, s.adjacentStations.toArray(new Integer[]{}), s.adjacentLine
            ));
        }
        return stations.toArray(new Station[]{});
    }

    StationLoader parseFileLineToStation(String fileLine) {
        StationLoader stationLoader = new StationLoader();
        String[] tokens = fileLine.trim().split("\\s+");
        stationLoader.id = Integer.parseInt(tokens[0]);
        String name = tokens[1];
        stationLoader.name = name;
        for(int i=2; i<tokens.length; i+=3) {
            Line adjacentLine = Line.valueOf(tokens[i]);
            int adjacentId1 = Integer.parseInt(tokens[i+1]);
            int adjacentId2 = Integer.parseInt(tokens[i+2]);
            if(adjacentId1 != 0) {
                // add station node as adjacent to currently parsed station
                stationLoader.adjacentStations.add(adjacentId1);
                // set line for edge to adjacent station
                stationLoader.addAdjacentLine(adjacentId1, adjacentLine);
            }
            if(adjacentId2 != 0) {
                // same as above
                stationLoader.adjacentStations.add(adjacentId2);
                stationLoader.addAdjacentLine(adjacentId2, adjacentLine);
            }
        }
        return stationLoader;
    }

    class StationLoader {
        Integer id;
        String name;
        HashSet<Integer> adjacentStations = new HashSet<>();
        HashMap<Integer, Line> adjacentLine = new HashMap<>();

        void addAdjacentLine(Integer adjacentId, Line line) {
            if(adjacentLine.containsKey(adjacentId)) {
                // pick line with lowest cost, since we don't have to account for waiting time between lines
                Double previousCost = Line.getCostForLine(adjacentLine.get(adjacentId));
                if(previousCost > Line.getCostForLine(line)) {
                    // previous cost was higher, add current one
                    adjacentLine.put(adjacentId, line);
                }
            } else {
                adjacentLine.put(adjacentId, line);
            }
        }

    }
}
