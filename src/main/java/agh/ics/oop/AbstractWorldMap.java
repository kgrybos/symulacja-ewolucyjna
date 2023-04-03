package agh.ics.oop;

import agh.ics.oop.gui.GraphicalMapVisualizer;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected final Map<Vector2d, IMapElement> mapElements = new HashMap<>();
    private final MapVisualizer mapVisualizer = new MapVisualizer(this);

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal animal = (Animal) mapElements.get(oldPosition);
        mapElements.remove(oldPosition);
        mapElements.put(newPosition, animal);
    }

    @Override
    public boolean place(Animal animal) {
        Vector2d position = animal.getPosition();
        if(canMoveTo(position)) {
            animal.addObserver(this);
            mapElements.put(position, animal);
        } else {
            throw new IllegalArgumentException("Animal can't be placed on position: " + position);
        }

        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return mapElements.get(position);
    }

    public abstract Vector2d lowerLeft();
    public abstract Vector2d upperRight();

    @Override
    public String toString() {
        return mapVisualizer.draw(lowerLeft(), upperRight());
    }
}
