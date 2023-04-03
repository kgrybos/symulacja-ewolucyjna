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
        MapDirection newDirection = direction.turn(moveDirection.getValue());
        Vector2d newPosition = position.add(newDirection.toUnitVector());
        return new PosDir(newPosition, newDirection);
    }
}
