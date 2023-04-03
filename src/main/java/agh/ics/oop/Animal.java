package agh.ics.oop;

import java.util.List;
import java.util.Random;

public class Animal extends AbstractMapElement {
    private final List<IAnimalEventObserver> animalEventObservers;
    private final AbstractWorldMap worldMap;
    private final Random random;
    private final Genome genome;
    private int energy;

    public Animal(Random random, List<IAnimalEventObserver> observers, int genomeSize, AbstractWorldMap worldMap, Vector2d position) {
        this.worldMap = worldMap;
        this.random = random;
        this.animalEventObservers = observers;
        this.posDir = new PosDir(position);
        this.energy = random.nextInt(10)+15;
        this.genome = new Genome(random, genomeSize);

        notifyObservers(new BirthEvent(posDir.position(), this));
    }

    private Animal(Random random, List<IAnimalEventObserver> observers, AbstractWorldMap worldMap, PosDir posDir, Genome genome) {
        this.worldMap = worldMap;
        this.random = random;
        this.animalEventObservers = observers;
        this.posDir = posDir;
        this.energy = random.nextInt(10)+1;
        this.genome = genome;
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public String toString() {
        return "Z " + posDir.position();
    }

    public void wakeUp() {
        energy -= 1;
        if(energy == 0) {
            notifyObservers(new DeathEvent(posDir.position(), this));
        }
    }

    public void move() {
        Vector2d oldPosition = posDir.position();
        posDir = worldMap.getPosDirToMove(this, genome.nextGene());
        notifyObservers(new PositionChangedEvent(oldPosition, posDir.position(), this));
    }

    public Animal reproduce(Animal weaker) {
        float ratio = ((float) energy)/(energy + weaker.energy);
        Side side = Side.random(random);
        Genome childGenome = new Genome(random, this.genome, weaker.genome, ratio, side);
        return new Animal(random, animalEventObservers, worldMap, posDir, childGenome);
    }

    public void addObserver(IAnimalEventObserver observer) {
        animalEventObservers.add(observer);
    }

    public void removeObserver(IAnimalEventObserver observer) {
        animalEventObservers.remove(observer);
    }

    private void notifyObservers(AnimalEvent animalEvent) {
        for(IAnimalEventObserver observer : animalEventObservers) {
            observer.animalEvent(animalEvent);
        }
    }

    @Override
    public String getImageFilename() {
        return "animal.png";
    }
}
