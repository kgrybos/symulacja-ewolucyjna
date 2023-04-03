package agh.ics.oop;

import java.util.Date;
import java.util.Random;

import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap {

    private final int tuftNumber;

    GrassField(int tuftNumber, long seed) {
        this.tuftNumber = tuftNumber;

        Random rand = new Random(seed);
        for(int i = 0; i < tuftNumber; i++) {
            int x = rand.nextInt((int) sqrt(tuftNumber*10)+1);
            int y = rand.nextInt((int) sqrt(tuftNumber*10)+1);
            mapElements.add(new Grass(new Vector2d(x,y)));
        }
    }

    GrassField(int tuftNumber) {
        this(tuftNumber, new Date().getTime());
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(isOccupied(position) && objectAt(position) instanceof Animal);
    }

    @Override
    protected Vector2d lowerLeft() {
        return mapElements
                .stream()
                .map(IMapElement::getPosition)
                .reduce(Vector2d::lowerLeft)
                .orElse(new Vector2d(-8, -8))
                .subtract(new Vector2d(1, 1));
    }

    @Override
    protected Vector2d upperRight() {
        return mapElements
                .stream()
                .map(IMapElement::getPosition)
                .reduce(Vector2d::upperRight)
                .orElse(new Vector2d(8, 8))
                .add(new Vector2d(1, 1));
    }
}
