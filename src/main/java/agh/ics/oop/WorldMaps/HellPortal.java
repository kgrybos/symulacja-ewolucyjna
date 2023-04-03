package agh.ics.oop.WorldMaps;

import agh.ics.oop.Animal;
import agh.ics.oop.MoveDirection;
import agh.ics.oop.PosDir;

import java.util.Random;

public class HellPortal extends AbstractWorldMap {
    private final Random random;
    public HellPortal(Random random, int width, int height) {
        super(width, height);
        this.random = random;
    }

    @Override
    public PosDir getPosDirToMove(Animal animal, MoveDirection moveDirection) {
        PosDir newPosDir = animal.posDir.move(moveDirection);

        if(boundary.isInside(newPosDir.position())) {
            return newPosDir;
        } else {
            animal.reproductionEnergyDecrease();
            return new PosDir(boundary.randomInside(random));
        }
    }
}
