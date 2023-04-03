package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal extends AbstractMapElement {
    private final List<IPositionChangeObserver> positionChangeObservers = new ArrayList<>();
    private final AbstractWorldMap worldMap;
    private final Random random;
    private final Genome genome;
    private int energy;

    public Animal(Random random, int genomeSize, AbstractWorldMap worldMap, Vector2d position) {
        this.worldMap = worldMap;
        this.random = random;
        this.posDir = new PosDir(position);
        this.energy = random.nextInt(10);
        this.genome = new Genome(random, genomeSize);
    }

    private Animal(Random random, AbstractWorldMap worldMap, PosDir posDir, Genome genome) {
        this.worldMap = worldMap;
        this.random = random;
        this.posDir = posDir;
        this.energy = random.nextInt(10);
        this.genome = genome;
    }

    @Override
    public String toString() {
        return "Z " + posDir.position();
    }

    public void move(MoveDirection direction) {
        Vector2d oldPosition = posDir.position();
        PosDir newPosDir = posDir.move(direction);
        posDir = worldMap.getPosDirToMove(this, newPosDir);
        positionChanged(oldPosition, posDir.position());
    }

    public Animal reproduce(Animal weaker) {
        float ratio = ((float) energy)/(energy + weaker.energy);
        Side side = Side.random(random);
        Genome childGenome = new Genome(random, this.genome, weaker.genome, ratio, side);
        return new Animal(random, worldMap, posDir, childGenome);
    }

    public void addObserver(IPositionChangeObserver observer) {
        positionChangeObservers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        positionChangeObservers.remove(observer);
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for(IPositionChangeObserver observer : positionChangeObservers) {
            observer.positionChanged(oldPosition, newPosition, this);
        }
    }

    @Override
    public String getImageFilename() {
        return "animal.png";
    }
}
