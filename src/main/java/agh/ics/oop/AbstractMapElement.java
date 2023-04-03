package agh.ics.oop;

public class AbstractMapElement implements IMapElement {
    protected Vector2d position;

    public Vector2d getPosition() { return position; }

    public boolean isAt(Vector2d position){ return this.position.equals(position); }
}
