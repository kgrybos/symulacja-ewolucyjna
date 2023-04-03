package agh.ics.oop.WorldMaps;

import agh.ics.oop.*;

public class Globe extends AbstractWorldMap {
    public Globe(int width, int height) {
        super(width, height);
    }

    @Override
    public PosDir getPosDirToMove(Animal animal, MoveDirection moveDirection) {
        PosDir newPosDir = animal.posDir.move(moveDirection);

        if(boundary.isInside(newPosDir.position())) {
            return newPosDir;
        } else if(boundary.isLeft(newPosDir.position())) {
            Vector2d newPosition = new Vector2d(boundary.upperRight().x, animal.posDir.position().y);
            return newPosDir.changePosition(newPosition);
        } else if(boundary.isRight(newPosDir.position())) {
            Vector2d newPosition = new Vector2d(boundary.lowerLeft().x, animal.posDir.position().y);
            return newPosDir.changePosition(newPosition);
        } else {
            return animal.posDir.changeDirectionOpposite();
        }
    }
}
