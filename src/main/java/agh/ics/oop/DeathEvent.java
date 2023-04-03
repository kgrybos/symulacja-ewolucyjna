package agh.ics.oop;

public class DeathEvent extends AnimalEvent {
    public final Vector2d position;
    public final Animal animal;

    public DeathEvent(Vector2d position, Animal animal) {
        this.position = position;
        this.animal = animal;
    }
}
