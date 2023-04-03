package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GrassFieldTest {
    @Test
    void occupationTests() {
        GrassField map = new GrassField(10, 1);
        Animal animal = new Animal(map, new Vector2d(1, 2));
        map.place(animal);

        assertTrue(map.isOccupied(new Vector2d(1, 2)));
        assertFalse(map.isOccupied(new Vector2d(1, 3)));

        assertFalse(map.canMoveTo(new Vector2d(1, 2)));
        assertTrue(map.canMoveTo(new Vector2d(1, 3)));

        assertTrue(map.isOccupied(new Vector2d(1, 4)));
    }

    @Test
    void objectAtTest() {
        GrassField map = new GrassField(10, 1);

        Animal animal1 = new Animal(map, new Vector2d(1, 2));
        map.place(animal1);

        Animal animal2 = new Animal(map, new Vector2d(1, 3));
        map.place(animal2);

        assertEquals(animal1, map.objectAt(new Vector2d(1, 2)));
        assertEquals(animal2, map.objectAt(new Vector2d(1, 3)));

        assertNull(map.objectAt(new Vector2d(9, 9)));

        assertNotEquals(animal1, map.objectAt(new Vector2d(1, 3)));

        assertTrue(map.objectAt(new Vector2d(1, 4)) instanceof Grass);
    }

    @Test
    void toStringTest() {
        GrassField map = new GrassField(10, 1);
        Animal animal = new Animal(map, new Vector2d(1, 2));
        map.place(animal);

        String expected = """
                y\\x  0 1 2
                 4: -------
                 3: | | | |
                 2: | |🡡| |
                 1: | | |*|
                 0: -------
               """;

        assertEquals(expected, map.toString());
    }
}
