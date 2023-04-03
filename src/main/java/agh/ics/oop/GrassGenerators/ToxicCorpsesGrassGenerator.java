package agh.ics.oop.GrassGenerators;

import agh.ics.oop.*;
import agh.ics.oop.WorldMaps.AbstractWorldMap;
import agh.ics.oop.observers.DeathEvent;
import agh.ics.oop.observers.ElementEvent;
import agh.ics.oop.observers.IElementEventObserver;

import java.util.*;

public class ToxicCorpsesGrassGenerator implements GrassGenerator, IElementEventObserver {
    private final HashMap<Vector2d, Integer> corpses = new HashMap<>();
    private final int mapSize;
    private final Random random;
    private final int energyFromGrass;

    public ToxicCorpsesGrassGenerator(Random random, Config config, int mapWidth, int mapHeight) {
        this.random = random;
        this.mapSize = mapWidth*mapHeight;
        this.energyFromGrass = config.energyFromGrass();

        for(int x = 0; x < mapWidth; x++) {
            for(int y = 0; y < mapHeight; y++) {
                corpses.put(new Vector2d(x, y), 0);
            }
        }
    }

    @Override
    public void generate(AbstractWorldMap map, SimulationEngine engine, int number) {
        List<Map.Entry<Vector2d, Integer>> fields = new ArrayList<>(corpses.entrySet());
        Collections.shuffle(fields, random);
        List<Vector2d> sortedFields = fields
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .toList();

        int cutoff_index = Math.round(mapSize*0.8f);
        List<Vector2d> worseFields = sortedFields.subList(0, cutoff_index);
        List<Vector2d> betterFields = sortedFields.subList(cutoff_index, mapSize);

        for(int i = 0; i < number; i++) {
            float choice = random.nextFloat();
            Vector2d position;
            if(choice < 0.8) {
                position = betterFields.get(random.nextInt(betterFields.size()));
            } else {
                position = worseFields.get(random.nextInt(worseFields.size()));
            }

            if(map.objectAt(position, Grass.class).isEmpty()) {
                new Grass.Builder(position, energyFromGrass)
                        .addGrassEventObserver(map)
                        .addGrassEventObserver(engine)
                        .build();
            }
        }
    }

    @Override
    public void handleElementEvent(ElementEvent event) {
        if(event instanceof DeathEvent deathEvent) {
            if(deathEvent.element instanceof Animal) {
                corpses.put(deathEvent.position, corpses.get(deathEvent.position)+1);
            }
        }
    }
}
