package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GlobeTest {
    @Test
    void occupationTests() {
        Globe map = new Globe(5, 5);
        Random random = new Random();

        Animal animal = new Animal(random, map, new Vector2d(1, 2));
        map.place(animal);

        assertTrue(map.isOccupied(new Vector2d(1, 2)));
        assertFalse(map.isOccupied(new Vector2d(1, 3)));
        assertFalse(map.isOccupied(new Vector2d(4, 0)));
    }

    @Test
    void objectAtTest() {
        Globe map = new Globe(5, 5);
        Random random = new Random();

        Animal animal1 = new Animal(random, map, new Vector2d(1, 2));
        map.place(animal1);

        Animal animal2 = new Animal(random, map, new Vector2d(1, 3));
        map.place(animal2);

        assertEquals(animal1, map.objectAt(new Vector2d(1, 2)));
        assertEquals(animal2, map.objectAt(new Vector2d(1, 3)));

        assertNull(map.objectAt(new Vector2d(9, 9)));

        assertNotEquals(animal1, map.objectAt(new Vector2d(1, 3)));
    }

    @Test
    void LeftRightBorderTest() {
        Globe map = new Globe(5, 5);
        Random random = new Random();

        Animal animal1 = new Animal(random, map, new Vector2d(1, 2));
        map.place(animal1);

        animal1.move(MoveDirection.LEFT);
        animal1.move(MoveDirection.LEFT);
        animal1.move(MoveDirection.FORWARD);
        animal1.move(MoveDirection.FORWARD);
        animal1.move(MoveDirection.FORWARD);
        animal1.move(MoveDirection.FORWARD);

        assertEquals(new Vector2d(2, 2), animal1.getPosition());
    }
    @Test
    void UpDownBorderTest() {
        Globe map = new Globe(5, 5);
        Random random = new Random();

        Animal animal1 = new Animal(random, map, new Vector2d(1, 2));
        map.place(animal1);

        animal1.move(MoveDirection.FORWARD);
        animal1.move(MoveDirection.FORWARD);
        animal1.move(MoveDirection.FORWARD);
        animal1.move(MoveDirection.FORWARD);

        assertEquals(new Vector2d(1, 3), animal1.getPosition());
    }
}
