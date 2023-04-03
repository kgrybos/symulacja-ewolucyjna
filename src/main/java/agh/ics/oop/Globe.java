package agh.ics.oop;

import java.util.Date;
import java.util.Random;

import static java.lang.Math.sqrt;

public class Globe extends AbstractWorldMap {
    public Globe(int width, int height, int tuftNumber, long seed) {
        super(width, height);

        Random rand = new Random(seed);
        for(int i = 0; i < tuftNumber; i++) {
            int x = rand.nextInt((int) sqrt(tuftNumber*10)+1);
            int y = rand.nextInt((int) sqrt(tuftNumber*10)+1);
            Vector2d position = new Vector2d(x,y);
            Grass grass = new Grass(position);
            mapElements.put(position, grass);
        }
    }

    public Globe(int width, int height, int tuftNumber) {
        this(width, height, tuftNumber, new Date().getTime());
    }

    public boolean place(Animal animal) {
        return super.place(animal);
    }

    @Override
    public PosDir getPosDirToMove(AbstractMapElement element, PosDir requestedPosDir) {
        if(requestedPosDir.position().follows(lowerLeft) && requestedPosDir.position().precedes(upperRight)) {
            return requestedPosDir;
        } else if(requestedPosDir.position().isLeft(lowerLeft)) {
            Vector2d newPosition = new Vector2d(upperRight.x, element.posDir.position().y);
            return requestedPosDir.changePosition(newPosition);
        } else if(requestedPosDir.position().isRight(upperRight)) {
            Vector2d newPosition = new Vector2d(lowerLeft.x, element.posDir.position().y);
            return requestedPosDir.changePosition(newPosition);
        } else {
            return element.posDir.changeDirectionOpposite();
        }
    }
}
