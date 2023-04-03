package agh.ics.oop.Mutators;

public enum MutatorType {
    RANDOM,
    SLIGHT;

    @Override
    public String toString() {
        return switch(this) {
            case RANDOM -> "pełna losowość";
            case SLIGHT -> "lekka korekta";
        };
    }

    public static MutatorType idToMutatorType(String id) {
        return switch (id) {
            case "random" -> MutatorType.RANDOM;
            case "slight" -> MutatorType.SLIGHT;
            default -> throw new IllegalArgumentException("Nie ma takiego wariantu mutacji");
        };
    }
}
