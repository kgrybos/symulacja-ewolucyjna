package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RectangularMapTest {
    @Test
    void occupationTests() {
        RectangularMap map = new RectangularMap(10, 7);
        Animal animal = new Animal(map, new Vector2d(1, 2));
        map.place(animal);

        assertTrue(map.isOccupied(new Vector2d(1, 2)));
        assertFalse(map.isOccupied(new Vector2d(1, 3)));

        assertFalse(map.canMoveTo(new Vector2d(1, 2)));
        assertTrue(map.canMoveTo(new Vector2d(1, 3)));

        assertFalse(map.canMoveTo(new Vector2d(10, 10)));
    }

    @Test
    void objectAtTest() {
        RectangularMap map = new RectangularMap(10, 7);

        Animal animal1 = new Animal(map, new Vector2d(1, 2));
        map.place(animal1);

        Animal animal2 = new Animal(map, new Vector2d(1, 3));
        map.place(animal2);

        assertEquals(animal1, map.objectAt(new Vector2d(1, 2)));
        assertEquals(animal2, map.objectAt(new Vector2d(1, 3)));

        assertNull(map.objectAt(new Vector2d(1, 4)));

        assertNotEquals(animal1, map.objectAt(new Vector2d(1, 3)));
    }

    @Test
    void toStringTest() {
        RectangularMap map = new RectangularMap(3, 3);
        Animal animal = new Animal(map, new Vector2d(1, 2));
        map.place(animal);

        String expected = """
                y\\x  0 1 2
                 3: -------
                 2: | |🡡| |
                 1: | | | |
                 0: | | | |
                -1: -------
               """;

        assertEquals(expected, map.toString());
    }

    @Test
    void placeTwiceTest() {
        RectangularMap map = new RectangularMap(3, 3);
        Animal animal1 = new Animal(map, new Vector2d(1, 2));
        Animal animal2 = new Animal(map, new Vector2d(1, 2));
        map.place(animal1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> map.place(animal2));
        assertEquals("Animal can't be placed on position: (1, 2)", exception.getMessage());
    }
}
