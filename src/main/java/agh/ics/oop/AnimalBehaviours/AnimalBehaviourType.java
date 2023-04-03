package agh.ics.oop.AnimalBehaviours;

public enum AnimalBehaviourType {
    PREDESTINATION,
    CRAZY;

    @Override
    public String toString() {
        return switch(this) {
            case PREDESTINATION -> "pełna predestynacja";
            case CRAZY -> "nieco szaleństwa";
        };
    }

    public static AnimalBehaviourType idToAnimalBehaviour(String id) {
        return switch (id) {
            case "predestination" -> AnimalBehaviourType.PREDESTINATION;
            case "crazy" -> AnimalBehaviourType.CRAZY;
            default -> throw new IllegalArgumentException("Nie ma takiego wariantu zachowania zwierzęcia");
        };
    }
}
