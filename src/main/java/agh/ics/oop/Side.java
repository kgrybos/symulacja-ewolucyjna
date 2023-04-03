package agh.ics.oop;

import java.util.Random;

public enum Side {
    LEFT,
    RIGHT;

    public static Side random(Random random) {
        return values()[random.nextInt(values().length)];
    }
}
