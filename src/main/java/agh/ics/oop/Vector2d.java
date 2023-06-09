package agh.ics.oop;

import java.util.Objects;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return String.format("(%s, %s)", x, y);
    }

    public boolean precedes(Vector2d other) {
        return x <= other.x && y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return x >= other.x && y >= other.y;
    }

    public boolean isLeft(Vector2d other) {
        return x < other.x;
    }

    public boolean isRight(Vector2d other) {
        return x > other.x;
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(x + other.x, y + other.y);
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(x - other.x, y - other.y);
    }

    public Vector2d upperRight(Vector2d other) {
        int new_x = max(x, other.x);
        int new_y = max(y, other.y);
        return new Vector2d(new_x, new_y);
    }

    public Vector2d lowerLeft(Vector2d other) {
        int new_x = min(x, other.x);
        int new_y = min(y, other.y);
        return new Vector2d(new_x, new_y);
    }

    public Vector2d opposite() {
        return new Vector2d(-x, -y);
    }

    public boolean equals(Object other) {
        if(this == other)
            return true;
        if(other instanceof Vector2d that) {
            return x == that.x && y == that.y;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(x, y);
    }
}
