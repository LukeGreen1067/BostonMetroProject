package common;

public enum Line {
    Orange,
    Blue,
    Green,
    GreenB,
    GreenC,
    GreenD,
    GreenE,
    Red,
    RedA,
    RedB,
    Mattapan;

    public static Double getCostForLine(Line line) {
        switch (line) {
            case Red:
            case RedA:
                return 1.0;
            case RedB:
                return 1.1;
            case Blue:
                return 1.2;
            case Green:
            case GreenB:
            case GreenC:
            case GreenD:
                return 1.3;
            case GreenE:
                return 1.1;
            case Orange:
                return 0.9;
            case Mattapan:
                return 1.0;
            default:
                return 1.0;
        }
    }
    @Override
    public String toString() {
        return this.name();
    }


}
