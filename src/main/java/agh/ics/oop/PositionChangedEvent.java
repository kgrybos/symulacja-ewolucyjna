package agh.ics.oop;

public class PositionChangedEvent extends AnimalEvent {
    public final Vector2d oldPosition;
    public final Vector2d newPosition;
    public final Animal animal;

    public PositionChangedEvent(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
        this.animal = animal;
    }
}
