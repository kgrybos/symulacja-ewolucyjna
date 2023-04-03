package agh.ics.oop;

import java.util.List;

public record SimulationStats (
    int animalNumber,
    int grassNumber,
    int freeNumber,
    List<MoveDirection[]> mostPopularGenotypes,
    double averageEnergy,
    double averageLifetime
) {}
