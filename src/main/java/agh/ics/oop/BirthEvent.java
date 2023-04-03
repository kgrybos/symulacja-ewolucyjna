package agh.ics.oop;

public class BirthEvent extends AnimalEvent {
    public final Vector2d position;
    public final Animal animal;

    public BirthEvent(Vector2d position, Animal animal) {
        this.position = position;
        this.animal = animal;
    }
}
