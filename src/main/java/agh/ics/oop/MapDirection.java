package agh.ics.oop;

import java.util.Random;

public enum MapDirection {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    public String toString() {
        return switch (this) {
            case NORTH -> "🡡";
            case NORTH_EAST -> "🡥";
            case EAST -> "🡢";
            case SOUTH_EAST -> "🡦";
            case SOUTH -> "🡣";
            case SOUTH_WEST -> "🡧";
            case WEST -> "🡠";
            case NORTH_WEST -> "🡤";
        };
    }

    private static final MapDirection[] values = values();

    public MapDirection next() {
        return values[(this.ordinal() + 1) % values.length];
    }

    public MapDirection previous() {
        int i = this.ordinal() - 1;
        if(i < 0) {
            i = values.length-1;
        }
        return values[i];
    }

    public MapDirection opposite() {
        return values[(this.ordinal() + values.length/2) % values.length];
    }

    public MapDirection turn(int amount) {
        return values[(this.ordinal() + amount) % values.length];
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case NORTH_EAST -> new Vector2d(1, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTH_EAST -> new Vector2d(1, -1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTH_WEST -> new Vector2d(-1, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTH_WEST -> new Vector2d(-1, 1);
        };
    }

    public static MapDirection random(Random random) {
        return values()[random.nextInt(values().length)];
    }

}
