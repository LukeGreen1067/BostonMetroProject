package backend;

import common.Line;
import interfaces.StationAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.LinkedHashSet;


public class PathTests {

    Path path;

    @BeforeEach
    void setup() {
        path = new Path();
        HashMap<Integer, Line> adjacentLines = new HashMap<>();
        Station dummy1 = new Station(29, "ParkStreet",  new Integer[]{}, adjacentLines);
        Station dummy2 = new Station(30, "DowntownCrossing",  new Integer[]{}, adjacentLines);
        Station dummy3 = new Station(28, "State",  new Integer[]{}, adjacentLines);
        path.stationsOnPath.add(dummy1);
        path.stationsOnPath.add(dummy2);
        path.stationsOnPath.add(dummy3);
        path.minutesTaken = 1.9;
        path.spannedLines = new LinkedHashSet<>();
        path.spannedLines.add(Line.Orange);
        path.spannedLines.add(Line.Red);
        path.spannedLines.add(Line.Red);
    }

    @Test
    void testRoundedCost() {
        assertEquals(2L, path.getMinutesTaken());
    }

    @Test
    void testStations() {
        StationAPI[] stations = path.getStations();
        assertEquals(stations[0].getID(), 29);
        assertEquals(stations[1].getID(), 30);
        assertEquals(stations[2].getID(), 28);
    }

    @Test
    void testLinesReversed() {
        Line[] lines = path.getSpannedLines();
        assertEquals(2, lines.length);
        assertEquals(Line.Red, lines[0]);
        assertEquals(Line.Orange, lines[1]);
    }

}
