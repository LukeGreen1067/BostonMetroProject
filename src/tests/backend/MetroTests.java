package backend;

import common.Line;
import interfaces.StationAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.List;

public class MetroTests {
    Metro metro = new Metro();
    protected Integer getPathcheckID_1, getPathcheckID_2, getPathcheckID_3, getPathcheckID_4, getPathcheckID_5, getPathcheckID_6;
    protected Long cost1;
    protected Long cost2;
    protected Long cost3;
    protected ArrayList<String> getPathcheck_1 = new ArrayList<String>();
    protected ArrayList<String> getPathcheck_2 = new ArrayList<String>();
    protected ArrayList<String> getPathcheck_3 = new ArrayList<String>();
    protected ArrayList<String> getLinecheck_1 = new ArrayList<String>();
    protected ArrayList<String> getLinecheck_2 = new ArrayList<String>();
    protected ArrayList<String> getLinecheck_3 = new ArrayList<String>();


    @BeforeEach
    protected void setup() {
        getPathcheckID_1 = 8;
        getPathcheckID_2 = 21;
        getLinecheck_1.add("Red");
        getPathcheck_1.addAll(List.of(new String[]{
                "Alewife", "Davis", "Porter", "Harvard", "Central"
        }));
        cost1 = 4L;

        getPathcheckID_3 = 16;
        getPathcheckID_4 = 5;
        getLinecheck_2.add("Blue");
        getLinecheck_2.add("Orange");
        getPathcheck_2.addAll(List.of(new String[]{
                "Airport", "Maverick", "Aquarium", "State", "Haymarket", "NorthStation", "CommunityCollege", "SullivanSquare", "Wellington",
        }));
        cost2 = 8L;

        getPathcheckID_5 = 24;
        getPathcheckID_6 = 25;
        getLinecheck_3.add("Blue");
        getLinecheck_3.add("Green");
        getLinecheck_3.add("Red");
        getPathcheck_3.addAll(List.of(new String[]{
                "Bowdoin", "GovernmentCenter", "ParkStreet", "Charles/MGH"
        }));
        cost3 = 4L;
    }

    @Test
    void testGetPath_1() {
        StationAPI[] stations = metro.getPath(getPathcheckID_1, getPathcheckID_2).getStations();
        ArrayList<String> result = new ArrayList<String>();
        for (StationAPI s: stations) {
            result.add(s.getName());
        }
        assertEquals(getPathcheck_1, result);

    }

    @Test
    void testGetLine_1() {
        Line[] lines = metro.getPath(getPathcheckID_1, getPathcheckID_2).getSpannedLines();
        ArrayList<String> result = new ArrayList<String>();
        for (Line l: lines) {
            result.add(l.name());
        }
        assertEquals(getLinecheck_1, result);

    }

    @Test
    void testGetCost_1() {
        Long cost_Result = metro.getPath(getPathcheckID_1, getPathcheckID_2).getMinutesTaken();
        assertEquals(cost1, cost_Result);

    }

    @Test
    void testGetPath_2() {
        StationAPI[] stations = metro.getPath(getPathcheckID_3, getPathcheckID_4).getStations();
        ArrayList<String> result = new ArrayList<String>();
        for (StationAPI s: stations) {
            result.add(s.getName());
        }
        assertEquals(getPathcheck_2, result);

    }

    @Test
    void testGetLine_2() {
        Line[] lines = metro.getPath(getPathcheckID_3, getPathcheckID_4).getSpannedLines();
        ArrayList<String> result = new ArrayList<String>();
        for (Line l: lines) {
            result.add(l.name());
        }
        assertEquals(getLinecheck_2, result);

    }

    @Test
    void testGetCost_2() {
        Long cost_Result = metro.getPath(getPathcheckID_3, getPathcheckID_4).getMinutesTaken();
        assertEquals(cost2, cost_Result);

    }

    @Test
    void testGetPath_3() {
        StationAPI[] stations = metro.getPath(getPathcheckID_5, getPathcheckID_6).getStations();
        ArrayList<String> result = new ArrayList<String>();
        for (StationAPI s: stations) {
            result.add(s.getName());
        }
        assertEquals(getPathcheck_3, result);

    }

    @Test
    void testGetLine_3() {
        Line[] lines = metro.getPath(getPathcheckID_5, getPathcheckID_6).getSpannedLines();
        ArrayList<String> result = new ArrayList<String>();
        for (Line l: lines) {
            result.add(l.name());
        }
        assertEquals(getLinecheck_3, result);

    }

    @Test
    void testGetCost_3() {
        Long cost_Result = metro.getPath(getPathcheckID_5, getPathcheckID_6).getMinutesTaken();
        assertEquals(cost3, cost_Result);

    }
}
