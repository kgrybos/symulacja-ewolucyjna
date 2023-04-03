package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GlobeTest {
    @Test
    void occupationTests() {
        Globe map = new Globe(5, 5);
        Random random = new Random();

        new Animal.Builder(map)
                .setRandom(random)
                .addAnimalEventObserver(map)
                .setPosDir(new PosDir(new Vector2d(1, 2)))
                .buildNew(8);

        assertTrue(map.isOccupied(new Vector2d(1, 2)));
        assertFalse(map.isOccupied(new Vector2d(1, 3)));
        assertFalse(map.isOccupied(new Vector2d(4, 0)));
    }

    @Test
    void objectAtTest() {
        Globe map = new Globe(5, 5);
        Random random = new Random();

        Animal animal1 =new Animal.Builder(map)
                .setRandom(random)
                .addAnimalEventObserver(map)
                .setPosDir(new PosDir(new Vector2d(1, 2)))
                .buildNew(8);

        Animal animal2 = new Animal.Builder(map)
                .setRandom(random)
                .addAnimalEventObserver(map)
                .setPosDir(new PosDir(new Vector2d(1, 3)))
                .buildNew(8);

        assertEquals(animal1, map.objectAt(new Vector2d(1, 2), Animal.class).get());
        assertEquals(animal2, map.objectAt(new Vector2d(1, 3), Animal.class).get());

        assertEquals(Optional.empty(), map.objectAt(new Vector2d(9, 9), AbstractMapElement.class));

        assertNotEquals(animal1, map.objectAt(new Vector2d(1, 3), AbstractMapElement.class).get());
    }

    @Test
    void LeftRightBorderTest() {
        Globe map = new Globe(5, 5);
        Random random = new Random();

        Animal animal1 = new Animal.Builder(map)
                .setRandom(random)
                .addAnimalEventObserver(map)
                .setPosDir(new PosDir(new Vector2d(1, 2)))
                .buildNew(8);

        animal1.move(MoveDirection.LEFT);
        animal1.move(MoveDirection.FORWARD);
        animal1.move(MoveDirection.FORWARD);
        animal1.move(MoveDirection.FORWARD);

        assertEquals(new Vector2d(2, 2), animal1.getPosition());
    }
    @Test
    void UpDownBorderTest() {
        Globe map = new Globe(5, 5);
        Random random = new Random();

        Animal animal1 = new Animal.Builder(map)
                .setRandom(random)
                .addAnimalEventObserver(map)
                .setPosDir(new PosDir(new Vector2d(1, 2)))
                .buildNew(8);

        animal1.move(MoveDirection.FORWARD);
        animal1.move(MoveDirection.FORWARD);
        animal1.move(MoveDirection.FORWARD);
        animal1.move(MoveDirection.FORWARD);

        assertEquals(new Vector2d(1, 3), animal1.getPosition());
    }
}
