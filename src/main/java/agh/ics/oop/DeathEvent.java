package agh.ics.oop;

public class DeathEvent extends AnimalEvent {
    public Vector2d position;
    public Animal animal;

    public DeathEvent(Vector2d position, Animal animal) {
        this.position = position;
        this.animal = animal;
    }
}
