package interfaces;

import common.Line;

public interface PathAPI {
    StationAPI[] getStations();
    Line[] getSpannedLines();
    Long getMinutesTaken();
}
