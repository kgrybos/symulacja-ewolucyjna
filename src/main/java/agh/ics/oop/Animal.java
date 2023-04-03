package agh.ics.oop;

public class Animal extends AbstractMapElement {
    private MapDirection orientation = MapDirection.NORTH;
    private final IWorldMap worldMap;

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
        return orientation.toString();
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
            position = newPosition;
        }
    }
}
