package agh.ics.oop;

public class RectangularMap extends AbstractWorldMap {
    RectangularMap(int width, int height) {
        super(width, height);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (isOccupied(position)) {
            return false;
        }

        return position.follows(lowerLeft) && position.precedes(upperRight);
    }
}
