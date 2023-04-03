package agh.ics.oop.AnimalBehaviours;

import java.util.Random;

public class CrazyBehaviour implements AnimalBehaviour {
    @Override
    public int getNext(Random random, int i, int genomSize) {
        float choice = random.nextFloat();
        if(choice < 0.8) {
            return (i + 1) % genomSize;
        } else {
            return random.nextInt(genomSize);
        }
    }
}
