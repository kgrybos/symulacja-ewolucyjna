package agh.ics.oop;

import agh.ics.oop.GrassGenerators.EquatorGrassGenerator;
import agh.ics.oop.GrassGenerators.GrassGenerator;
import agh.ics.oop.GrassGenerators.ToxicCorpsesGrassGenerator;
import agh.ics.oop.WorldMaps.AbstractWorldMap;
import agh.ics.oop.WorldMaps.Globe;
import agh.ics.oop.WorldMaps.HellPortal;
import agh.ics.oop.gui.GraphicalMapVisualizer;
import agh.ics.oop.observers.BirthEvent;
import agh.ics.oop.observers.ElementEvent;
import agh.ics.oop.observers.IElementEventObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class SimulationEngine implements Runnable, IElementEventObserver {
    private final List<Animal> animals = new ArrayList<>();
    private final int dayDelay;
    private boolean paused = true;
    private final AbstractWorldMap worldMap;
    private final GraphicalMapVisualizer graphicalMapVisualizer;
    private GrassGenerator grassGenerator;
    private final Config config;

    public SimulationEngine(int dayDelay, Config config) {
        this.dayDelay = dayDelay;
        this.config = config;

        Random random = new Random(0);

        worldMap = switch (config.worldMap()) {
            case GLOBE -> new Globe(config.mapWidth(), config.mapHeight());
            case HELL -> new HellPortal(random, config.mapWidth(), config.mapHeight());
        };
        graphicalMapVisualizer = new GraphicalMapVisualizer(worldMap);
        worldMap.addPositionsChangedObserver(graphicalMapVisualizer);

        for (int i = 0; i < config.initialAnimalNumber(); i++) {
            new Animal.Builder(worldMap, config)
                    .setRandom(random)
                    .setEnergy(config.initialAnimalEnergy())
                    .addAnimalEventObserver(worldMap)
                    .addAnimalEventObserver(this)
                    .buildNew(config.genomeSize());
        }

        switch(config.grassGenerator()) {
            case EQUATOR -> grassGenerator = new EquatorGrassGenerator(random, config, worldMap.width, worldMap.height);
            case TOXIC -> grassGenerator = new ToxicCorpsesGrassGenerator(random, config, worldMap.width, worldMap.height);
        }
        grassGenerator.generate(worldMap, config.initialGrassNumber());

        if(grassGenerator instanceof ToxicCorpsesGrassGenerator toxicCorpsesGrassGenerator) {
            for(Animal animal : animals) {
                animal.addEventObserver(toxicCorpsesGrassGenerator);
            }
        }
    }

    public GraphicalMapVisualizer getGraphicalMapVisualizer() {
        return graphicalMapVisualizer;
    }

    public void simulateDay() {
        for(Animal animal : animals) {
            animal.wakeUp();
        }
        animals.removeIf((animal -> animal.getEnergy() <= 0));

        for(Animal animal : animals) {
            animal.move();
        }

        for(Animal animal : animals) {
            animal.eat();
        }

        List<Animal> animalsCopy = new ArrayList<>(animals);
        for(Animal animal : animalsCopy) {
            animal.reproduce();
        }

        grassGenerator.generate(worldMap, config.dailyNewGrass());
    }

    @Override
    public void handleElementEvent(ElementEvent elementEvent) {
        if(elementEvent instanceof BirthEvent event) {
            if(event.element instanceof Animal animal) {
                animals.add(animal);
            }
        }
    }

    public Animal[] getAnimals() {
        return animals.toArray(new Animal[]{});
    }

    public boolean isPaused() {
        return this.paused;
    }

    public void pause() {
        paused = true;
    }

    public synchronized void resume() {
        paused = false;
        notify();
    }

    @Override
    public synchronized void run() {
        try {
            wait();
            while(true) {
                simulateDay();
                if (paused) {
                    wait();
                } else {
                    sleep(dayDelay);
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
