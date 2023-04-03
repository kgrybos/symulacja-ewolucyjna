package agh.ics.oop;

public record PosDir(
        Vector2d position,
        MapDirection direction
) {
    public PosDir(Vector2d position) {
        this(position, MapDirection.NORTH);
    }
    public PosDir changePosition(Vector2d newPosition) {
        return new PosDir(newPosition, direction);
    }
    public PosDir changeDirection(MapDirection newDirection) {
        return new PosDir(position, newDirection);
    }
    public PosDir changeDirectionOpposite() {
        return this.changeDirection(direction.opposite());
    }

    public PosDir move(MoveDirection moveDirection) {
        MapDirection newDirection = switch(moveDirection) {
            case LEFT -> direction.previous();
            case RIGHT -> direction.next();
            default -> direction;
        };

        Vector2d newPosition = switch (moveDirection) {
            case FORWARD -> position.add(newDirection.toUnitVector());
            case BACKWARD -> position.subtract(newDirection.toUnitVector());
            default -> position;
        };

        return new PosDir(newPosition, newDirection);
    }
}
