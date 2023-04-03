package agh.ics.oop.AnimalBehaviours;

import java.util.Random;

public class PredestinicBehaviour implements AnimalBehaviour {
    @Override
    public int getNext(Random random, int i, int genomSize) {
        return (i+1)%genomSize;
    }
}
