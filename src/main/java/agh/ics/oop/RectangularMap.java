package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class RectangularMap implements IWorldMap {
    private final int width;
    private final int height;
    private final List<Animal> animals = new ArrayList<>();
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;

    RectangularMap(int width, int height) {
        this.width = width;
        this.height = height;

        lowerLeft = new Vector2d(0, 0);
        upperRight  = new Vector2d(width-1, height-1);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if(isOccupied(position)) {
            return false;
        }

        return position.follows(lowerLeft) && position.precedes(upperRight);
    }

    @Override
    public boolean place(Animal animal) {
        if(canMoveTo(animal.getPosition())) {
            animals.add(animal);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for(Animal animal : animals) {
            if(animal.getPosition().equals(position)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for(Animal animal : animals) {
            if(animal.getPosition().equals(position)) {
                return animal;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        MapVisualizer mapVisualizer = new MapVisualizer(this);

        return mapVisualizer.draw(lowerLeft, upperRight);
    }
}
