package agh.ics.oop;

import java.util.Iterator;
import java.util.List;

import static java.lang.Thread.sleep;

public class SimulationEngine implements IEngine, Runnable {
    private MoveDirection[] moves = new MoveDirection[]{};
    private final List<Animal> animals;
    private final int moveDelay;

    public SimulationEngine(MoveDirection[] moves, List<Animal> animals, int moveDelay) {
        this.moves = moves;
        this.animals = animals;
        this.moveDelay = moveDelay;
    }

    public SimulationEngine(List<Animal> animals, int moveDelay) {
        this.animals = animals;
        this.moveDelay = moveDelay;
    }

    public void setMoveDirections(String directions) {
        String[] directionsSplitted = directions.split("\\s+");
        moves = OptionsParser.parse(directionsSplitted);
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
