package agh.ics.oop;

public enum MapDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public String toString() {
        return switch (this) {
            case NORTH -> "🡡";
            case EAST -> "🡢";
            case SOUTH -> "🡣";
            case WEST -> "🡠";
        };
    }

    public String getFilename() {
        return switch (this) {
            case NORTH -> "up.png";
            case EAST -> "right.png";
            case SOUTH -> "down.png";
            case WEST -> "left.png";
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
        return values[(this.ordinal() + 2) % values.length];
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTH -> new Vector2d(0, -1);
            case WEST -> new Vector2d(-1, 0);
        };
    }
}
