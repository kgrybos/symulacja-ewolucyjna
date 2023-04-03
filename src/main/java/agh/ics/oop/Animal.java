package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Animal extends AbstractMapElement {
    private final List<IAnimalEventObserver> animalEventObservers;
    private final List<IAnimalStatsObserver> animalStatsObservers;
    private final AbstractWorldMap worldMap;
    private final Random random;
    private final Genome genome;
    private int energy;

    private Animal(Builder builder) {
        this.worldMap = builder.worldMap;
        this.random = Objects.requireNonNullElseGet(builder.random, Random::new);
        this.animalEventObservers = builder.animalEventObservers;
        this.animalStatsObservers = builder.animalStatsObservers;
        this.posDir = Objects.requireNonNullElseGet(builder.posDir, () -> new PosDir(worldMap.boundary.randomInside(random)));

        //TODO: Get value from config
        this.energy = Objects.requireNonNullElseGet(builder.energy, () -> random.nextInt(10)+15);
        this.genome = Objects.requireNonNullElseGet(builder.genome, () -> new Genome(random, builder.genomeSize));

        notifyObservers(new BirthEvent(this.posDir.position(), this));
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

    public void move(MoveDirection move) {
        Vector2d oldPosition = posDir.position();
        posDir = worldMap.getPosDirToMove(this, move);
        notifyObservers(new PositionChangedEvent(oldPosition, posDir.position(), this));
    }

    public Animal reproduce(Animal weaker) {
        float ratio = ((float) energy)/(energy + weaker.energy);
        Side side = Side.random(random);
        Genome childGenome = new Genome(random, this.genome, weaker.genome, ratio, side);
        return new Builder(worldMap)
                .setRandom(random)
                .addAnimalEventObserverAll(animalEventObservers)
                .setPosDir(posDir)
                .buildBorn(childGenome);
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

    public static class Builder {
        private final AbstractWorldMap worldMap;
        private Random random;
        private int genomeSize;
        private Genome genome;
        private PosDir posDir;
        private Integer energy;
        private final List<IAnimalEventObserver> animalEventObservers = new ArrayList<>();
        private final List<IAnimalStatsObserver> animalStatsObservers = new ArrayList<>();

        public Builder(AbstractWorldMap worldMap) {
            this.worldMap = worldMap;
        }

        public Builder setRandom(Random random) {
            this.random = random;
            return this;
        }

        public Builder addAnimalEventObserver(IAnimalEventObserver observer) {
            this.animalEventObservers.add(observer);
            return this;
        }
        public Builder addAnimalEventObserverAll(List<IAnimalEventObserver> observers) {
            this.animalEventObservers.addAll(observers);
            return this;
        }
        public Builder addAnimalStatsObserver(IAnimalStatsObserver observer) {
            this.animalStatsObservers.add(observer);
            return this;
        }
        public Builder addAnimalStatsObserverAll(List<IAnimalStatsObserver> observers) {
            this.animalStatsObservers.addAll(observers);
            return this;
        }

        public Builder setPosDir(PosDir posDir) {
            this.posDir = posDir;
            return this;
        }

        public Builder setEnergy(int energy) {
            this.energy = energy;
            return this;
        }

        public Animal buildNew(int genomeSize) {
            this.genomeSize = genomeSize;
            return new Animal(this);
        }

        public Animal buildBorn(Genome genome) {
            this.genome = genome;
            return new Animal(this);
        }
    }
}
