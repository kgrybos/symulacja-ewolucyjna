package agh.ics.oop.Mutators;

import agh.ics.oop.MoveDirection;

import java.util.Random;

public interface Mutator {
    MoveDirection mutate(Random random, MoveDirection gene);
}
