package agh.ics.oop.GrassGenerators;

import agh.ics.oop.*;
import agh.ics.oop.WorldMaps.AbstractWorldMap;

import java.util.Random;

public class EquatorGrassGenerator implements GrassGenerator {
    private final Random random;
    private final Boundary northBoundary;
    private final Boundary equatorBoundary;
    private final Boundary southBoundary;
    private final int energyFromGrass;

    public EquatorGrassGenerator(Random random, Config config, int mapWidth, int mapHeight) {
        this.random = random;
        this.energyFromGrass = config.energyFromGrass();

        double equatorArea = mapWidth*mapHeight*0.2;
        int equatorHeight = (int) equatorArea/mapWidth;
        int worseLandHeight = (mapHeight-equatorHeight)/2;

        Vector2d southLowerLeft = new Vector2d(0, 0);
        Vector2d southUpperRight = new Vector2d(mapWidth-1, worseLandHeight-1);
        southBoundary = new Boundary(southLowerLeft, southUpperRight);

        Vector2d equatorLowerLeft = new Vector2d(0, southUpperRight.y+1);
        Vector2d equatorUpperRight = new Vector2d(mapWidth-1, southUpperRight.y+equatorHeight);
        equatorBoundary = new Boundary(equatorLowerLeft, equatorUpperRight);

        Vector2d northLowerLeft = new Vector2d(0, equatorUpperRight.y+1);
        Vector2d northUpperRight = new Vector2d(mapWidth-1, mapHeight-1);
        northBoundary = new Boundary(northLowerLeft, northUpperRight);

    }
    @Override
    public void generate(AbstractWorldMap map, SimulationEngine engine, int number) {
        for(int i = 0; i < number; i++) {
            Vector2d position;
            double regionChoice = random.nextDouble();
            if(regionChoice < 0.1) {
                position = northBoundary.randomInside(random);
            } else if (regionChoice < 0.9) {
                position = equatorBoundary.randomInside(random);
            } else {
                position = southBoundary.randomInside(random);
            }

            if(map.objectAt(position, Grass.class).isEmpty()) {
                new Grass.Builder(position, energyFromGrass)
                        .addGrassEventObserver(map)
                        .addGrassEventObserver(engine)
                        .build();
            }
        }
    }
}
