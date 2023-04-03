package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReproductionTest {
    @Test
    void reproduceTest() {
        Globe worldMap = new Globe(10, 10);

        Animal animal1 = new Animal.Builder(worldMap)
                .setPosDir(new PosDir(new Vector2d(5, 5)))
                .setEnergy(10)
                .addAnimalEventObserver(worldMap)
                .buildNew(8);

        Animal animal2 = new Animal.Builder(worldMap)
                .setPosDir(new PosDir(new Vector2d(5, 5)))
                .setEnergy(20)
                .addAnimalEventObserver(worldMap)
                .buildNew(8);

        animal1.reproduce();
        animal2.reproduce();

        assertEquals(1, animal1.getNumberOfChildren());
        assertEquals(1, animal2.getNumberOfChildren());
        assertEquals(3, worldMap.objectsAt(new Vector2d(5, 5)).size());
    }

    @Test
    void dontReproduceTest() {
        Globe worldMap = new Globe(10, 10);

        Animal animal1 = new Animal.Builder(worldMap)
                .setPosDir(new PosDir(new Vector2d(7, 6)))
                .setEnergy(10)
                .addAnimalEventObserver(worldMap)
                .buildNew(8);

        Animal animal2 = new Animal.Builder(worldMap)
                .setPosDir(new PosDir(new Vector2d(5, 5)))
                .setEnergy(20)
                .addAnimalEventObserver(worldMap)
                .buildNew(8);

        animal1.reproduce();
        animal2.reproduce();

        assertEquals(0, animal1.getNumberOfChildren());
        assertEquals(0, animal2.getNumberOfChildren());
        assertEquals(1, worldMap.objectsAt(new Vector2d(5, 5)).size());
        assertEquals(1, worldMap.objectsAt(new Vector2d(7, 6)).size());
    }

    @Test
    void foreverAloneTest() {
        Globe worldMap = new Globe(10, 10);

        Animal animal = new Animal.Builder(worldMap)
                .setPosDir(new PosDir(new Vector2d(5, 5)))
                .setEnergy(10)
                .addAnimalEventObserver(worldMap)
                .buildNew(8);

        animal.reproduce();

        assertEquals(0, animal.getNumberOfChildren());
        assertEquals(1, worldMap.objectsAt(new Vector2d(5, 5)).size());
    }

    @Test
    void conflictReproduceTest() {
        Globe worldMap = new Globe(10, 10);

        Animal animal1 = new Animal.Builder(worldMap)
                .setPosDir(new PosDir(new Vector2d(5, 5)))
                .setEnergy(10)
                .addAnimalEventObserver(worldMap)
                .buildNew(8);

        Animal animal2 = new Animal.Builder(worldMap)
                .setPosDir(new PosDir(new Vector2d(5, 5)))
                .setEnergy(20)
                .addAnimalEventObserver(worldMap)
                .buildNew(8);

        Animal animal3 = new Animal.Builder(worldMap)
                .setPosDir(new PosDir(new Vector2d(5, 5)))
                .setEnergy(15)
                .addAnimalEventObserver(worldMap)
                .buildNew(8);

        Animal animal4 = new Animal.Builder(worldMap)
                .setPosDir(new PosDir(new Vector2d(5, 5)))
                .setEnergy(7)
                .addAnimalEventObserver(worldMap)
                .buildNew(8);

        animal1.reproduce();
        animal2.reproduce();
        animal3.reproduce();
        animal4.reproduce();

        assertEquals(0, animal1.getNumberOfChildren());
        assertEquals(1, animal2.getNumberOfChildren());
        assertEquals(1, animal3.getNumberOfChildren());
        assertEquals(0, animal4.getNumberOfChildren());
        assertEquals(5, worldMap.objectsAt(new Vector2d(5, 5)).size());
    }
}
