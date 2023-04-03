package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapBoundaryTest {
    @Test
    void commonTest() {
        GrassField map = new GrassField(10, 0);
        Animal animal1 = new Animal(map, new Vector2d(0, 0));
        Animal animal2 = new Animal(map, new Vector2d(10, 10));
        map.place(animal1);
        map.place(animal2);

        assertEquals(new Vector2d(-1, -1), map.lowerLeft());
        assertEquals(new Vector2d(11, 11), map.upperRight());
    }

    @Test
    void bigTest() {
        GrassField map = new GrassField(10, 0);
        Animal animal1 = new Animal(map, new Vector2d(-1000, -1000));
        Animal animal2 = new Animal(map, new Vector2d(1000, 1000));
        map.place(animal1);
        map.place(animal2);

        assertEquals(new Vector2d(-1001, -1001), map.lowerLeft());
        assertEquals(new Vector2d(1001, 1001), map.upperRight());
    }

    @Test
    void oneAnimalTest() {
        GrassField map = new GrassField(0, 0);
        Animal animal1 = new Animal(map, new Vector2d(15, 7));
        map.place(animal1);

        assertEquals(new Vector2d(14, 6), map.lowerLeft());
        assertEquals(new Vector2d(16, 8), map.upperRight());
    }
}
