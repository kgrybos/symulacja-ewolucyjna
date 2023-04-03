package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {
    @Test
    void toStringTest() {
        RectangularMap map = new RectangularMap(5, 5);
        assertEquals("🡡", new Animal(map).toString());
    }

    @Test
    void isAtTest() {
        RectangularMap map = new RectangularMap(5, 5);

        Animal animal = new Animal(map);
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.FORWARD);

        assertTrue(animal.isAt(new Vector2d(3, 2)));
        assertFalse(animal.isAt(new Vector2d(0, 0)));
        assertFalse(animal.isAt(new Vector2d(2, 2)));
    }

    @Test
    void moveTest() {
        RectangularMap map = new RectangularMap(5, 5);

        Animal animal = new Animal(map);
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(4, 2), animal.getPosition());
        assertEquals(MapDirection.EAST, animal.getOrientation());

        animal = new Animal(map);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.LEFT);
        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(1, 3), animal.getPosition());
        assertEquals(MapDirection.WEST, animal.getOrientation());

        animal = new Animal(map);
        animal.move(MoveDirection.LEFT);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(1, 3), animal.getPosition());
        assertEquals(MapDirection.NORTH, animal.getOrientation());

        animal = new Animal(map);
        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.BACKWARD);
        assertEquals(new Vector2d(1, 1), animal.getPosition());
        assertEquals(MapDirection.EAST, animal.getOrientation());

        animal = new Animal(map);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.LEFT);
        animal.move(MoveDirection.LEFT);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.LEFT);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(0, 0), animal.getPosition());
        assertEquals(MapDirection.SOUTH, animal.getOrientation());
    }
}