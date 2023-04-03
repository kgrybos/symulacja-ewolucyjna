package agh.ics.oop.Mutators;

import agh.ics.oop.MoveDirection;

import java.util.Random;

public class SlightMutator implements Mutator {
    @Override
    public MoveDirection mutate(Random random, MoveDirection gene) {
        int choice = random.nextInt(2);
        if(choice == 0) {
            return gene.previous();
        } else {
            return gene.next();
        }
    }
}
