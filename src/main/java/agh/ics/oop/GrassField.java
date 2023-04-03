package agh.ics.oop;

import java.util.Date;
import java.util.Random;

import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap {
    GrassField(int width, int height, int tuftNumber, long seed) {
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

    public GrassField(int width, int height, int tuftNumber) {
        this(width, height, tuftNumber, new Date().getTime());
    }

    public boolean place(Animal animal) {
        return super.place(animal);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(lowerLeft) && position.precedes(upperRight);
    }
}
