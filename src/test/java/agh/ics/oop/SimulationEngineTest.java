package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationEngineTest {
    @Test
    void RunTest() {
        MoveDirection[] directions = OptionsParser.parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
        IWorldMap map = new RectangularMap(2, 5);
        Vector2d[] positions = { new Vector2d(0,2), new Vector2d(1,4) };
        List<Animal> animals = new ArrayList<>();
        for(Vector2d position : positions) {
            Animal animal = new Animal(map, position);
            animals.add(animal);
            map.place(animal);
        }

        IEngine engine = new SimulationEngine(directions, animals, 300);
        engine.run();

        assertTrue(map.objectAt(new Vector2d(1, 4)) instanceof Animal);
        assertTrue(map.objectAt(new Vector2d(0, 0)) instanceof Animal);

        assertNull(map.objectAt(new Vector2d(0, 1)));
        assertNull(map.objectAt(new Vector2d(0, 2)));
        assertNull(map.objectAt(new Vector2d(0, 3)));
        assertNull(map.objectAt(new Vector2d(0, 4)));
        assertNull(map.objectAt(new Vector2d(1, 0)));
        assertNull(map.objectAt(new Vector2d(1, 1)));
        assertNull(map.objectAt(new Vector2d(1, 2)));
        assertNull(map.objectAt(new Vector2d(1, 3)));
    }

    @Test
    void collisionTest() {
        MoveDirection[] directions = OptionsParser.parse(new String[]{"r", "l", "f", "f", "f", "f"});
        IWorldMap map = new RectangularMap(6, 1);
        Vector2d[] positions = { new Vector2d(2,0), new Vector2d(3,0) };
        List<Animal> animals = new ArrayList<>();
        for(Vector2d position : positions) {
            Animal animal = new Animal(map, position);
            animals.add(animal);
            map.place(animal);
        }

        IEngine engine = new SimulationEngine(directions, animals, 300);
        engine.run();

        assertTrue(map.objectAt(new Vector2d(2, 0)) instanceof Animal);
        assertTrue(map.objectAt(new Vector2d(3, 0)) instanceof Animal);

        assertNull(map.objectAt(new Vector2d(0, 0)));
        assertNull(map.objectAt(new Vector2d(1, 0)));
        assertNull(map.objectAt(new Vector2d(4, 0)));
        assertNull(map.objectAt(new Vector2d(5, 0)));
    }

    @Test
    void borderTest() {
        MoveDirection[] directions = OptionsParser.parse(new String[]{"f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f"});
        IWorldMap map = new RectangularMap(5, 5);
        Vector2d[] positions = { new Vector2d(0,0) };
        List<Animal> animals = new ArrayList<>();
        for(Vector2d position : positions) {
            Animal animal = new Animal(map, position);
            animals.add(animal);
            map.place(animal);
        }

        IEngine engine = new SimulationEngine(directions, animals, 300);
        engine.run();

        assertTrue(map.objectAt(new Vector2d(0, 4)) instanceof Animal);
    }
}
