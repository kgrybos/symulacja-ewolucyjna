package agh.ics.oop.WorldMaps;

import agh.ics.oop.MoveDirection;
import agh.ics.oop.PosDir;
import agh.ics.oop.Vector2d;

public class Globe extends AbstractWorldMap {
    public Globe(int width, int height) {
        super(width, height);
    }

    @Override
    public PosDir getPosDirToMove(AbstractMapElement element, MoveDirection moveDirection) {
        PosDir newPosDir = element.posDir.move(moveDirection);

        if(boundary.isInside(newPosDir.position())) {
            return newPosDir;
        } else if(boundary.isLeft(newPosDir.position())) {
            Vector2d newPosition = new Vector2d(boundary.upperRight().x, element.posDir.position().y);
            return newPosDir.changePosition(newPosition);
        } else if(boundary.isRight(newPosDir.position())) {
            Vector2d newPosition = new Vector2d(boundary.lowerLeft().x, element.posDir.position().y);
            return newPosDir.changePosition(newPosition);
        } else {
            return element.posDir.changeDirectionOpposite();
        }
    }
}
