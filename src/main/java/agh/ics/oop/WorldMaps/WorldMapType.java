package agh.ics.oop.WorldMaps;

public enum WorldMapType {
    GLOBE,
    HELL;

    @Override
    public String toString() {
        return switch(this) {
            case GLOBE -> "kula ziemska";
            case HELL -> "piekielny portal";
        };
    }

    public static WorldMapType idToWorldMapType(String id) {
        return switch (id) {
            case "globe" -> WorldMapType.GLOBE;
            //TODO
            case "hell" -> WorldMapType.HELL;
            default -> throw new IllegalArgumentException("Nie ma takiego typu generatora trawy");
        };
    }
}
