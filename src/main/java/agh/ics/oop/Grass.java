package agh.ics.oop;

public class Grass extends AbstractMapElement {
    Grass(Vector2d position) { super.position = position; }

    @Override
    public String toString() {
        return "*";
    }
}