package agh.ics.oop;

import java.util.Random;

public enum MoveDirection {
    FORWARD,
    FORWARD_RIGHT,
    RIGHT,
    BACKWARD_RIGHT,
    BACKWARD,
    BACKWARD_LEFT,
    LEFT,
    FORWARD_LEFT;

    @Override
    public String toString() {
        return switch (this) {
            case FORWARD -> "0";
            case FORWARD_RIGHT -> "1";
            case RIGHT -> "2";
            case BACKWARD_RIGHT -> "3";
            case BACKWARD -> "4";
            case BACKWARD_LEFT -> "5";
            case LEFT -> "6";
            case FORWARD_LEFT -> "7";
        };
    }

    public int getValue() {
        return switch (this) {
            case FORWARD -> 0;
            case FORWARD_RIGHT -> 1;
            case RIGHT -> 2;
            case BACKWARD_RIGHT -> 3;
            case BACKWARD -> 4;
            case BACKWARD_LEFT -> 5;
            case LEFT -> 6;
            case FORWARD_LEFT -> 7;
        };
    }

    public static MoveDirection random(Random random) {
        return values()[random.nextInt(values().length)];
    }
}
