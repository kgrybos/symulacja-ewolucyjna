package agh.ics.oop.GrassGenerators;

public enum GrassGeneratorType {
    EQUATOR,
    TOXIC;

    @Override
    public String toString() {
        return switch(this) {
            case EQUATOR -> "zalesione równiki";
            case TOXIC -> "toksyczne trupy";
        };
    }

    public static GrassGeneratorType idToGrassGenerator(String id) {
        return switch (id) {
            case "equator" -> GrassGeneratorType.EQUATOR;
            //TODO
            case "toxic" -> GrassGeneratorType.TOXIC;
            default -> throw new IllegalArgumentException("Nie ma takiego typu generatora trawy");
        };
    }
}
