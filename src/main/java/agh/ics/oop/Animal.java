package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal extends AbstractMapElement {
    private final List<IPositionChangeObserver> positionChangeObservers = new ArrayList<>();
    private final AbstractWorldMap worldMap;
    private Genome genome;

    public Animal(Random random, AbstractWorldMap worldMap, Vector2d initialPosition) {
        this.worldMap = worldMap;
        this.posDir = new PosDir(initialPosition);
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
