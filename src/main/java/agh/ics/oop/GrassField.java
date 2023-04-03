package agh.ics.oop;

import java.util.Date;
import java.util.Random;

import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap {

    private final int tuftNumber;
    private final MapBoundary mapBoundary = new MapBoundary();

    GrassField(int tuftNumber, long seed) {
        this.tuftNumber = tuftNumber;

        Random rand = new Random(seed);
        for(int i = 0; i < tuftNumber; i++) {
            int x = rand.nextInt((int) sqrt(tuftNumber*10)+1);
            int y = rand.nextInt((int) sqrt(tuftNumber*10)+1);
            Vector2d position = new Vector2d(x,y);
            Grass grass = new Grass(position);
            mapElements.put(position, grass);
            mapBoundary.put(grass);
        }
    }

    public GrassField(int tuftNumber) {
        this(tuftNumber, new Date().getTime());
    }

    public boolean place(Animal animal) {
        boolean result = super.place(animal);
        mapBoundary.put(animal);
        return result;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(isOccupied(position) && objectAt(position) instanceof Animal);
    }

    @Override
    public Vector2d lowerLeft() {
        return mapBoundary.lowerLeft().subtract(new Vector2d(1, 1));
    }

    @Override
    public Vector2d upperRight() {
        return mapBoundary.upperRight().add(new Vector2d(1, 1));
    }
}
