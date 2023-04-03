package agh.ics.oop;

public class Animal {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position = new Vector2d(2, 2);

    @Override
    public String toString() {
        return String.format("Pozycja: %s, orientacja: %s", position, orientation);
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public void move(MoveDirection direction) {
        orientation = switch(direction) {
            case LEFT -> orientation.previous();
            case RIGHT -> orientation.next();
            default -> orientation;
        };

        position = switch (direction) {
            case FORWARD -> position.add(orientation.toUnitVector());
            case BACKWARD -> position.subtract(orientation.toUnitVector());
            default -> position;
        };

        if(position.x > 4) {
            position = new Vector2d(4, position.y);
        }

        if(position.x < 0) {
            position = new Vector2d(0, position.y);
        }

        if(position.y > 4) {
            position = new Vector2d(position.x, 4);
        }

        if(position.y < 0) {
            position = new Vector2d(position.x, 0);
        }
    }
}
