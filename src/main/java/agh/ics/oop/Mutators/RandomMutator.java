package agh.ics.oop.Mutators;

import agh.ics.oop.MoveDirection;

import java.util.Random;

public class RandomMutator  implements Mutator {
    @Override
    public MoveDirection mutate(Random random, MoveDirection gene) {
        return MoveDirection.random(random);
    }
}
