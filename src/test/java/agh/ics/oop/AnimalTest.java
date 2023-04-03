package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {
    @Test
    void toStringTest() {
        assertEquals("Pozycja: (2, 2), orientacja: Północ", new Animal().toString());
    }

    @Test
    void isAtTest() {
        Animal animal = new Animal();
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.FORWARD);

        assertTrue(animal.isAt(new Vector2d(3, 2)));
        assertFalse(animal.isAt(new Vector2d(0, 0)));
        assertFalse(animal.isAt(new Vector2d(2, 2)));
    }

    @Test
    void moveTest() {
        Animal animal = new Animal();
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        assertEquals("Pozycja: (4, 2), orientacja: Wschód", animal.toString());

        animal = new Animal();
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.LEFT);
        animal.move(MoveDirection.FORWARD);
        assertEquals("Pozycja: (1, 3), orientacja: Zachód", animal.toString());

        animal = new Animal();
        animal.move(MoveDirection.LEFT);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.FORWARD);
        assertEquals("Pozycja: (1, 3), orientacja: Północ", animal.toString());

        animal = new Animal();
        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.BACKWARD);
        assertEquals("Pozycja: (1, 1), orientacja: Wschód", animal.toString());

        animal = new Animal();
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
        assertEquals("Pozycja: (0, 0), orientacja: Południe", animal.toString());
    }
}