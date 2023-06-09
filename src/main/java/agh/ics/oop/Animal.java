package agh.ics.oop;

import agh.ics.oop.WorldMaps.AbstractMapElement;
import agh.ics.oop.WorldMaps.AbstractWorldMap;
import agh.ics.oop.observers.*;

import java.util.*;

public class Animal extends AbstractMapElement {
    private final List<IElementEventObserver> animalEventObservers;
    private final List<IAnimalStatsObserver> animalStatsObservers;
    private final AbstractWorldMap worldMap;
    private final Random random;
    private final Genome genome;
    private int energy;
    private int daysAlive = 0;
    private int numberOfChildren = 0;
    private int grassEaten = 0;
    private final int birthday;
    private final Config config;

    private Animal(Builder builder) {
        this.config = builder.config;
        this.worldMap = builder.worldMap;
        this.random = Objects.requireNonNullElseGet(builder.random, Random::new);
        this.animalEventObservers = builder.animalEventObservers;
        this.animalStatsObservers = builder.animalStatsObservers;
        this.posDir = Objects.requireNonNullElseGet(builder.posDir, () -> new PosDir(worldMap.boundary.randomInside(random)));
        this.energy = Objects.requireNonNullElseGet(builder.energy, () -> random.nextInt(10)+15);
        this.genome = Objects.requireNonNullElseGet(builder.genome, () -> new Genome(random, config, builder.genomeSize));
        this.birthday = Objects.requireNonNullElse(builder.birthday, 0);

        notifyEventObservers(new BirthEvent(this.posDir.position(), this));
    }

    public int getEnergy() {
        return energy;
    }
    public int getDaysAlive() {
        return daysAlive;
    }
    public int getNumberOfChildren() {
        return numberOfChildren;
    }
    public MoveDirection[] getGenes() {
        return genome.getGenes();
    }

    @Override
    public String toString() {
        return "Zwierze " + posDir.position();
    }

    public void reproductionEnergyDecrease() {
        energy -= config.energyForNewborn();
    }

    public void wakeUp() {
        energy -= 1;
        if(energy == 0) {
            notifyEventObservers(new DeathEvent(posDir.position(), this));
        }
        daysAlive += 1;
        notifyStatsObservers(getStats());
    }

    public void move() {
        Vector2d oldPosition = posDir.position();
        posDir = worldMap.getPosDirToMove(this, genome.nextGene());
        notifyEventObservers(new PositionChangedEvent(oldPosition, posDir.position(), this));
    }

    public void move(MoveDirection move) {
        Vector2d oldPosition = posDir.position();
        posDir = worldMap.getPosDirToMove(this, move);
        notifyEventObservers(new PositionChangedEvent(oldPosition, posDir.position(), this));
    }

    public void eat() {
        int amount = worldMap.getFood(this);
        energy += amount;
        grassEaten += amount;
    }

    public void reproduce() {
        Optional<Animal> partner = worldMap.getPartner(this);
        if(partner.isPresent()) {
            Animal weaker = partner.get();
            if(this.energy > config.satiatedEnergy() && weaker.energy > config.satiatedEnergy()){
                this.numberOfChildren += 1;
                weaker.numberOfChildren += 1;

                this.energy -= config.energyForNewborn();
                weaker.energy -= config.energyForNewborn();

                float ratio = ((float) energy) / (energy + weaker.energy);
                Side side = Side.random(random);
                Genome childGenome = new Genome(random, this.genome, weaker.genome, ratio, side, config);
                PosDir childPosDir = posDir.changeDirection(MapDirection.random(random));
                new Builder(worldMap, config)
                        .setRandom(random)
                        .addAnimalEventObserverAll(animalEventObservers)
                        .setEnergy(config.energyForNewborn()*2)
                        .setPosDir(childPosDir)
                        .setBirthday(birthday + daysAlive)
                        .buildBorn(childGenome);
            }
        }
    }

    public void addEventObserver(IElementEventObserver observer) {
        animalEventObservers.add(observer);
    }

    public void removeEventObserver(IElementEventObserver observer) {
        animalEventObservers.remove(observer);
    }
    public void addStatsObserver(IAnimalStatsObserver observer) {
        animalStatsObservers.add(observer);
    }

    public void removeStatsObserver(IAnimalStatsObserver observer) {
        animalStatsObservers.remove(observer);
    }

    private void notifyEventObservers(ElementEvent elementEvent) {
        for(IElementEventObserver observer : animalEventObservers) {
            observer.handleElementEvent(elementEvent);
        }
    }

    public AnimalStats getStats() {
        return new AnimalStats(
                genome.getGenes(),
                genome.getActiveGene(),
                energy,
                grassEaten,
                numberOfChildren,
                daysAlive,
                energy <= 0 ? Optional.of(birthday+daysAlive) : Optional.empty()
        );
    }

    private void notifyStatsObservers(AnimalStats animalStats) {
        for(IAnimalStatsObserver observer : animalStatsObservers) {
            observer.updateAnimalStats(animalStats);
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
        private Integer birthday;
        private final Config config;
        private final List<IElementEventObserver> animalEventObservers = new ArrayList<>();
        private final List<IAnimalStatsObserver> animalStatsObservers = new ArrayList<>();

        public Builder(AbstractWorldMap worldMap, Config config) {
            this.worldMap = worldMap;
            this.config = config;
        }

        public Builder setRandom(Random random) {
            this.random = random;
            return this;
        }

        public Builder addAnimalEventObserver(IElementEventObserver observer) {
            this.animalEventObservers.add(observer);
            return this;
        }
        public Builder addAnimalEventObserverAll(List<IElementEventObserver> observers) {
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

        public Builder setBirthday(int birthday) {
            this.birthday = birthday;
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
