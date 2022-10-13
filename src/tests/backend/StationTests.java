package backend;

import java.util.HashMap;
import common.Line;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StationTests {

    Station station;

    @BeforeEach
    void setup() {
        // 29 ParkStreet			    Red 25 30  Green 31 27
        Integer[] adjacentStations = new Integer[]{25, 30, 31, 27};
        HashMap<Integer, Line> adjacentLines = new HashMap<>();
        adjacentLines.put(25, Line.Red);
        adjacentLines.put(30, Line.Red);
        adjacentLines.put(31, Line.Green);
        adjacentLines.put(27, Line.Green);
        station = new Station(29, "ParkStreet", adjacentStations, adjacentLines);
    }

    @Test
    void testID() {
        assertEquals(29, station.getID() );
    }

    @Test
    void testName() {
        assertEquals("ParkStreet", station.getName());
    }

    @Test
    void testAdjacentStations() {
        Integer[] adjacentStations = new Integer[]{25, 30, 31, 27};
        assertArrayEquals(adjacentStations, station.getAdjacentStations());
    }

    @Test
    void testAdjacentLines() {
        assertEquals(Line.Red, station.getLineForAdjacentStation(25));
        assertEquals(Line.Red, station.getLineForAdjacentStation(30));
        assertEquals(Line.Green, station.getLineForAdjacentStation(27));
        assertEquals(Line.Green, station.getLineForAdjacentStation(31));
    }

    @Test
    void testAdjacentCosts() {
        assertEquals(Line.getCostForLine(Line.Red), station.getCostForAdjacentStation(25));
        assertEquals(Line.getCostForLine(Line.Red), station.getCostForAdjacentStation(30));
        assertEquals(Line.getCostForLine(Line.Green), station.getCostForAdjacentStation(27));
        assertEquals(Line.getCostForLine(Line.Green), station.getCostForAdjacentStation(31));
    }

    @Test
    void testExtendedName() {
        assertEquals("Park Street", station.toString());
    }

    @Test
    void testComparison() {
        // stations should be compared (for sorting) by alphabetical order
        HashMap<Integer, Line> adjacentLines = new HashMap<>();
        Station dummyStation = new Station(29, "XLowerNameInAlphabet", new Integer[]{}, adjacentLines);
        assertTrue(station.compareTo(dummyStation) < 0);
    }

    @Test
    void testEquals() {
        // stations should be tested for equality by ID
        HashMap<Integer, Line> adjacentLines = new HashMap<>();
        Station dummyStation = new Station(29, "SomeName", new Integer[]{}, adjacentLines);
        assertEquals(dummyStation, station);
    }
}
