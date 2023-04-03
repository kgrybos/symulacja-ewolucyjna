package agh.ics.oop;

public class Globe extends AbstractWorldMap {
    public Globe(int width, int height) {
        super(width, height);
    }

    public boolean place(Animal animal) {
        return super.place(animal);
    }

    @Override
    public PosDir getPosDirToMove(AbstractMapElement element, PosDir requestedPosDir) {
        if(boundary.isInside(requestedPosDir.position())) {
            return requestedPosDir;
        } else if(boundary.isLeft(requestedPosDir.position())) {
            Vector2d newPosition = new Vector2d(boundary.upperRight().x, element.posDir.position().y);
            return requestedPosDir.changePosition(newPosition);
        } else if(boundary.isRight(requestedPosDir.position())) {
            Vector2d newPosition = new Vector2d(boundary.lowerLeft().x, element.posDir.position().y);
            return requestedPosDir.changePosition(newPosition);
        } else {
            return element.posDir.changeDirectionOpposite();
        }
    }
}
