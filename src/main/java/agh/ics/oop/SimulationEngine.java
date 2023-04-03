package agh.ics.oop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimulationEngine implements IEngine{
    private final MoveDirection[] moves;
    private final IWorldMap map;
    private final List<Animal> animals = new ArrayList<>();

    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] initialAnimalPositions) {
        this.moves = moves;
        this.map = map;

        for(Vector2d initialPosition : initialAnimalPositions) {
            Animal newAnimal = new Animal(map, initialPosition);
            map.place(newAnimal);
            animals.add(newAnimal);
        }
    }

    @Override
    public void run() {
        Iterator<Animal> animalIterator = animals.iterator();

        for(MoveDirection move : moves) {
            Animal currentAnimal = animalIterator.next();
            currentAnimal.move(move);

            if(!animalIterator.hasNext()) {
                animalIterator = animals.iterator();
            }
        }
    }
}
