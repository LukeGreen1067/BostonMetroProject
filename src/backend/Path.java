package backend;

import common.Line;
import interfaces.PathAPI;
import interfaces.StationAPI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;

public class Path implements PathAPI {

    ArrayList<StationAPI> stationsOnPath = new ArrayList<>();
    LinkedHashSet<Line> spannedLines = new LinkedHashSet<>();
    Double minutesTaken = 0.0;


    @Override
    public StationAPI[] getStations() {
        return stationsOnPath.toArray(new StationAPI[]{});
    }

    @Override
    public Line[] getSpannedLines() {
        ArrayList<Line> linesList = new ArrayList<>(spannedLines);
        Collections.reverse(linesList);
        return linesList.toArray(new Line[]{});
    }

    @Override
    public Long getMinutesTaken() {
        return Math.round(minutesTaken);
    }

}


