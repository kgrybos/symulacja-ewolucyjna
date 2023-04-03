package agh.ics.oop;

import java.util.Optional;

public record AnimalStats(
        MoveDirection[] genes,
        int activeGene,
        int energy,
        int grassEaten,
        int numberOfChildren,
        int daysAlive,
        Optional<Integer> deathDay
        ) {}
