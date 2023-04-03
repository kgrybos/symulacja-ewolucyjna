package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
    @Test
    void equalsTest() {
        Vector2d a = new Vector2d(1000, -1000);
        Vector2d b = new Vector2d(1000, -1000);
        assertEquals(a, b);

        a = new Vector2d(1000, -1000);
        b = new Vector2d(5000, 5000);
        assertNotEquals(a, b);

        Object c = new Object();
        assertNotEquals(a, b);
    }

    @Test
    void toStringTest() {
        Vector2d a = new Vector2d(1, 0);
        assertEquals("(1, 0)", a.toString());

        a = new Vector2d(-1000, -1000);
        assertEquals("(-1000, -1000)", a.toString());
    }

    @Test
    void precedesTest() {
        Vector2d a = new Vector2d(1, 1);
        Vector2d b = new Vector2d(3, 2);
        Vector2d c = new Vector2d(3, 4);

        assertTrue(a.precedes(a));
        assertTrue(b.precedes(b));
        assertTrue(c.precedes(c));

        assertTrue(a.precedes(b));
        assertTrue(a.precedes(c));
        assertTrue(b.precedes(c));

        assertFalse(c.precedes(a));
        assertFalse(c.precedes(b));
        assertFalse(b.precedes(a));
    }

    @Test
    void followsTest() {
        Vector2d a = new Vector2d(1, 1);
        Vector2d b = new Vector2d(3, 2);
        Vector2d c = new Vector2d(3, 4);

        assertTrue(a.follows(a));
        assertTrue(b.follows(b));
        assertTrue(c.follows(c));

        assertFalse(a.follows(b));
        assertFalse(a.follows(c));
        assertFalse(b.follows(c));

        assertTrue(c.follows(a));
        assertTrue(c.follows(b));
        assertTrue(b.follows(a));
    }

    @Test
    void upperRightlowerLeftTest() {
        Vector2d a  = new Vector2d(1, 4);
        Vector2d b = new Vector2d(3, 1);

        assertEquals(new Vector2d(3, 4), a.upperRight(b));
        assertEquals(new Vector2d(1, 1), a.lowerLeft(b));

        a = new Vector2d(3, 4);
        b = new Vector2d(1, 1);

        assertEquals(new Vector2d(3, 4), a.upperRight(b));
        assertEquals(new Vector2d(1, 1), a.lowerLeft(b));
    }

    @Test
    void addSubstractTest() {
        Vector2d a = new Vector2d(5, -5);
        Vector2d b = new Vector2d(7, 8);
        assertEquals(new Vector2d(12, 3), a.add(b));
        assertEquals(new Vector2d(-2, -13), a.substract(b));
        assertEquals(new Vector2d(2, 13), b.substract(a));

        a = new Vector2d(1000, -1000);
        b = new Vector2d(2000, 2000);
        assertEquals(new Vector2d(3000, 1000), a.add(b));
        assertEquals(new Vector2d(-1000, -3000), a.substract(b));
    }

    @Test
    void oppositeTest() {
        Vector2d a = new Vector2d(5, -5);
        Vector2d b = new Vector2d(7, 8);
        assertEquals(new Vector2d(-5, 5), a.opposite());
        assertEquals(new Vector2d(-7, -8), b.opposite());
    }
}
