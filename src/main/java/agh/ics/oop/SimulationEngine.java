package agh.ics.oop;

import java.util.Iterator;
import java.util.List;

import static java.lang.Thread.sleep;

public class SimulationEngine implements IEngine, Runnable {
    private final MoveDirection[] moves;
    private final List<Animal> animals;
    private final int moveDelay;

    public SimulationEngine(MoveDirection[] moves, List<Animal> animals, int moveDelay) {
        this.moves = moves;
        this.animals = animals;
        this.moveDelay = moveDelay;
    }

    @Override
    public void run() {
        Iterator<Animal> animalIterator = animals.iterator();

        for(MoveDirection move : moves) {
            try {
                sleep(moveDelay);
            } catch(InterruptedException exception) {
                System.out.println("Simulation stopped.");
            }

            Animal currentAnimal = animalIterator.next();
            currentAnimal.move(move);

            if(!animalIterator.hasNext()) {
                animalIterator = animals.iterator();
            }
        }
    }
}
