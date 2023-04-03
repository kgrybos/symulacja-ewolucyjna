package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class Animal extends AbstractMapElement {
    private MapDirection orientation = MapDirection.NORTH;
    private final IWorldMap worldMap;

    private final List<IPositionChangeObserver> positionChangeObservers = new ArrayList<>();

    Animal(IWorldMap map) {
        worldMap = map;
        position = new Vector2d(2, 2);
    }

    Animal(IWorldMap map, Vector2d initialPosition) {
        worldMap = map;
        position = initialPosition;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    @Override
    public String toString() {
        return "Z " + position;
    }

    public void move(MoveDirection direction) {
        orientation = switch(direction) {
            case LEFT -> orientation.previous();
            case RIGHT -> orientation.next();
            default -> orientation;
        };

        Vector2d newPosition = switch (direction) {
            case FORWARD -> position.add(orientation.toUnitVector());
            case BACKWARD -> position.subtract(orientation.toUnitVector());
            default -> position;
        };

        if(worldMap.canMoveTo(newPosition)) {
            Vector2d oldPosition = position;
            position = newPosition;
            positionChanged(oldPosition, newPosition);
        }
    }

    public void addObserver(IPositionChangeObserver observer) {
        positionChangeObservers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        positionChangeObservers.remove(observer);
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for(IPositionChangeObserver observer : positionChangeObservers) {
            observer.positionChanged(oldPosition, newPosition);
        }
    }

    @Override
    public String getImageFilename() {
        return orientation.getFilename();
    }
}
