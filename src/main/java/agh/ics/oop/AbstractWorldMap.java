package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWorldMap implements IWorldMap {
    protected final List<IMapElement> mapElements = new ArrayList<>();

    @Override
    public boolean place(Animal animal) {
        if(canMoveTo(animal.getPosition())) {
            mapElements.add(animal);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for(IMapElement mapElement : mapElements) {
            if(mapElement.getPosition().equals(position)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for(IMapElement mapElement : mapElements) {
            if(mapElement.getPosition().equals(position)) {
                return mapElement;
            }
        }

        return null;
    }

    protected abstract Vector2d lowerLeft();
    protected abstract Vector2d upperRight();

    @Override
    public String toString() {
        MapVisualizer mapVisualizer = new MapVisualizer(this);
        return mapVisualizer.draw(lowerLeft(), upperRight());
    }
}
