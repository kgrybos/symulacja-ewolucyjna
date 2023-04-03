package agh.ics.oop;

public class BirthEvent extends AnimalEvent {
    public Vector2d position;
    public Animal animal;

    public BirthEvent(Vector2d position, Animal animal) {
        this.position = position;
        this.animal = animal;
    }
}
