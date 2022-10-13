package backend;

import common.Line;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

public class FileLoaderTests {

    FileLoader loader;
    Station[] results;

    Integer ashmontID = 105;
    Integer cedarGroveID = 106;

    @BeforeEach
    void setup() {
        ClassLoader classLoader = getClass().getClassLoader();
        File testFile = new File(classLoader.getResource("test_file.txt").getFile());
        loader = new FileLoader(testFile);
        results = loader.loadData();
    }

    @Test
    void testLoadedStations() {
        assertNotNull(results[ashmontID - 1]);
        assertNotNull(results[cedarGroveID - 1]);

        Station ashmont = results[ashmontID - 1];
        Station cedarGrove = results[cedarGroveID - 1];
        assertEquals("Ashmont", ashmont.getName());
        assertEquals("CedarGrove", cedarGrove.getName());
    }

    @Test
    void testLoadedStationsAdjacents() {
        Station ashmont = results[ashmontID - 1];
        Station cedarGrove = results[cedarGroveID - 1];
        // Check adjacent stations
        Integer[] ashmontAdjacent = ashmont.getAdjacentStations();
        assertEquals(104, ashmontAdjacent[0]);
        assertEquals(cedarGroveID, ashmontAdjacent[1]);
        Integer[] cedarGroveAdjacent = cedarGrove.getAdjacentStations();
        assertEquals(ashmontID, cedarGroveAdjacent[0]);
        assertEquals(107, cedarGroveAdjacent[1]);
    }

    @Test
    void testLoadedStationsAdjacentLines() {
        Station ashmont = results[ashmontID - 1];
        Station cedarGrove = results[cedarGroveID - 1];
        // Check lines
        assertEquals(Line.Mattapan, cedarGrove.getLineForAdjacentStation(ashmontID));
        assertEquals(Line.Mattapan, ashmont.getLineForAdjacentStation(cedarGroveID));
    }

    @Test
    void testLoadedStationsCosts() {
        Station ashmont = results[ashmontID - 1];
        Station cedarGrove = results[cedarGroveID - 1];
        // Check costs
        assertEquals(Line.getCostForLine(Line.Mattapan), cedarGrove.getCostForAdjacentStation(ashmontID));
        assertEquals(Line.getCostForLine(Line.Mattapan), ashmont.getCostForAdjacentStation(cedarGroveID));
    }

    @Test
    void testLineParser() {
        // parse line
        String line = " 29 ParkStreet\t\t\t    Red 25 30  Green 31 27";
        FileLoader.StationLoader stationData = loader.parseFileLineToStation(line);
        // checks
        assertNotNull(loader);
        Station parkStreet = new Station(stationData.id, stationData.name, stationData.adjacentStations.toArray(new Integer[]{}), stationData.adjacentLine);
        assertEquals("ParkStreet", parkStreet.getName());
        List<Integer> adjacents = List.of(parkStreet.getAdjacentStations());
        assertTrue(adjacents.containsAll(List.of(new Integer[] {25, 30, 31, 27})));
        assertEquals(Line.Green, parkStreet.getLineForAdjacentStation(31));
    }
}
