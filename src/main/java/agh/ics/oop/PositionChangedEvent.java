package agh.ics.oop;

public class PositionChangedEvent extends AnimalEvent {
    public Vector2d oldPosition;
    public Vector2d newPosition;
    public Animal animal;

    public PositionChangedEvent(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
        this.animal = animal;
    }
}
