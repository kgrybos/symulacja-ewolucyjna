package agh.ics.oop;

import agh.ics.oop.WorldMaps.Globe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EatingTest {
    @Test
    void eatTest() {
        Globe worldMap = new Globe(10, 10);

        Animal animal = new Animal.Builder(worldMap, Config.getFromFile("normal"))
                .setPosDir(new PosDir(new Vector2d(5, 5)))
                .addAnimalEventObserver(worldMap)
                .buildNew(8);

        new Grass.Builder(new Vector2d(5, 5), 10)
                .addGrassEventObserver(worldMap)
                .build();

        int before_energy = animal.getEnergy();
        animal.eat();
        int after_energy = animal.getEnergy();
        assertEquals(10, after_energy-before_energy);
    }

    @Test
    void dontEatTest() {
        Globe worldMap = new Globe(10, 10);

        Animal animal = new Animal.Builder(worldMap, Config.getFromFile("normal"))
                .setPosDir(new PosDir(new Vector2d(7, 7)))
                .addAnimalEventObserver(worldMap)
                .buildNew(8);

        new Grass.Builder(new Vector2d(5, 5), 10)
            .addGrassEventObserver(worldMap)
            .build();

        int before_energy = animal.getEnergy();
        animal.eat();
        int after_energy = animal.getEnergy();
        assertEquals(0, after_energy-before_energy);
    }

    @Test
    void conflictTest() {
        Globe worldMap = new Globe(10, 10);

        Animal animal1 = new Animal.Builder(worldMap, Config.getFromFile("normal"))
                .setPosDir(new PosDir(new Vector2d(5, 5)))
                .setEnergy(16)
                .addAnimalEventObserver(worldMap)
                .buildNew(8);
        for(int i = 0; i < 1; i++) {
            animal1.wakeUp();
        }

        Animal animal2 = new Animal.Builder(worldMap, Config.getFromFile("normal"))
                .setPosDir(new PosDir(new Vector2d(5, 5)))
                .setEnergy(20)
                .addAnimalEventObserver(worldMap)
                .buildNew(8);
        for(int i = 0; i < 5; i++) {
            animal2.wakeUp();
        }

        Animal animal3 = new Animal.Builder(worldMap, Config.getFromFile("normal"))
                .setPosDir(new PosDir(new Vector2d(5, 5)))
                .setEnergy(20)
                .addAnimalEventObserver(worldMap)
                .buildNew(8);
        for(int i = 0; i < 10; i++) {
            animal3.wakeUp();
        }

        new Grass.Builder(new Vector2d(5, 5), 10)
                .addGrassEventObserver(worldMap)
                .build();

        int before_energy1 = animal1.getEnergy();
        int before_energy2 = animal2.getEnergy();
        int before_energy3 = animal3.getEnergy();
        animal1.eat();
        animal2.eat();
        animal3.eat();
        int after_energy1 = animal1.getEnergy();
        int after_energy2 = animal2.getEnergy();
        int after_energy3 = animal3.getEnergy();
        assertEquals(0, after_energy1-before_energy1);
        assertEquals(10, after_energy2-before_energy2);
        assertEquals(0, after_energy3-before_energy3);
    }
}
