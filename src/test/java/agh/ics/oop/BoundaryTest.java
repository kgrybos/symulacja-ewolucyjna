package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoundaryTest {
    @Test
    void isInsideTest() {
        Boundary boundary = new Boundary(new Vector2d(2, 3), new Vector2d(10, 18));
        assertTrue(boundary.isInside(new Vector2d(5, 13)));
        assertTrue(boundary.isInside(new Vector2d(2, 3)));
        assertTrue(boundary.isInside(new Vector2d(10, 18)));
        assertTrue(boundary.isInside(new Vector2d(9, 4)));

        assertFalse(boundary.isInside(new Vector2d(-1, -20)));
        assertFalse(boundary.isInside(new Vector2d(1, 10)));
        assertFalse(boundary.isInside(new Vector2d(8, 19)));
        assertFalse(boundary.isInside(new Vector2d(5, 100)));
    }

    @Test
    void isLeftTest() {
        Boundary boundary = new Boundary(new Vector2d(2, 3), new Vector2d(10, 18));
        assertTrue(boundary.isLeft(new Vector2d(-10, -10)));
        assertTrue(boundary.isLeft(new Vector2d(1, 10)));

        assertFalse(boundary.isLeft(new Vector2d(7, 12)));
        assertFalse(boundary.isLeft(new Vector2d(15, 12)));
    }

    @Test
    void isRightTest() {
        Boundary boundary = new Boundary(new Vector2d(2, 3), new Vector2d(10, 18));
        assertTrue(boundary.isRight(new Vector2d(15, 12)));
        assertTrue(boundary.isRight(new Vector2d(12, 100)));

        assertFalse(boundary.isRight(new Vector2d(7, 12)));
        assertFalse(boundary.isRight(new Vector2d(-15, 12)));
    }

    @Test
    void randomInsideTest() {
        Boundary boundary = new Boundary(new Vector2d(2, 3), new Vector2d(10, 18));
        Random random = new Random();
        assertTrue(boundary.isInside(boundary.randomInside(random)));
        assertTrue(boundary.isInside(boundary.randomInside(random)));
        assertTrue(boundary.isInside(boundary.randomInside(random)));
        assertTrue(boundary.isInside(boundary.randomInside(random)));
        assertTrue(boundary.isInside(boundary.randomInside(random)));
        assertTrue(boundary.isInside(boundary.randomInside(random)));
        assertTrue(boundary.isInside(boundary.randomInside(random)));
        assertTrue(boundary.isInside(boundary.randomInside(random)));
        assertTrue(boundary.isInside(boundary.randomInside(random)));
        assertTrue(boundary.isInside(boundary.randomInside(random)));
        assertTrue(boundary.isInside(boundary.randomInside(random)));
    }
}
