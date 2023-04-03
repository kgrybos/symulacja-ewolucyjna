package agh.ics.oop;

import java.util.Random;

public record Boundary(
        Vector2d lowerLeft,
        Vector2d upperRight
) {
    public boolean isInside(Vector2d position) {
        return position.follows(lowerLeft) && position.precedes(upperRight);
    }

    public boolean isLeft(Vector2d position) {
        return position.isLeft(lowerLeft);
    }
    public boolean isRight(Vector2d position) {
        return position.isRight(upperRight);
    }

    public Vector2d randomInside(Random random) {
        int width = upperRight.x - lowerLeft.x + 1;
        int height = upperRight.y - lowerLeft.y + 1;

        int x = lowerLeft.x + random.nextInt(width);
        int y = lowerLeft.y + random.nextInt(height);
        return new Vector2d(x, y);
    }
}
