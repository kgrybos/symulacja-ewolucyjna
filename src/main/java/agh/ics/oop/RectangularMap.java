package agh.ics.oop;

public class RectangularMap extends AbstractWorldMap {
    private final int width;
    private final int height;
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
        if (isOccupied(position)) {
            return false;
        }

        return position.follows(lowerLeft) && position.precedes(upperRight);
    }

    @Override
    public Vector2d lowerLeft() { return lowerLeft; }

    @Override
    public Vector2d upperRight() {
        return upperRight;
    }
}
