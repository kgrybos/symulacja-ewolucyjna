package agh.ics.oop;

import agh.ics.oop.AnimalBehaviours.AnimalBehaviourType;
import agh.ics.oop.GrassGenerators.GrassGeneratorType;
import agh.ics.oop.Mutators.MutatorType;
import agh.ics.oop.WorldMaps.WorldMapType;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public record Config (
        String name,
        int mapWidth,
        int mapHeight,
        WorldMapType worldMap,
        int initialGrassNumber,
        int energyFromGrass,
        int dailyNewGrass,
        GrassGeneratorType grassGenerator,
        int initialAnimalNumber,
        int initialAnimalEnergy,
        int satiatedEnergy,
        int energyForNewborn,
        int minMutations,
        int maxMutations,
        MutatorType mutator,
        int genomeSize,
        AnimalBehaviourType animalBehaviour,
        boolean saveStats
) {
    public static Config getFromFile(String configName) {
        try {
            return getFromFile(Paths.get(World.class.getResource("configs/" + configName + ".properties").toURI()));
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static Config getFromFile(Path path) {
        Properties props;
        try {
            InputStream propsInput = Files.newInputStream(path);
            props = new Properties();
            props.load(propsInput);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        String name = props.getProperty("NAME");
        int mapWidth = Integer.parseInt(props.getProperty("MAP_WIDTH"));
        int mapHeight = Integer.parseInt(props.getProperty("MAP_HEIGHT"));
        WorldMapType worldMap = WorldMapType.idToWorldMapType(props.getProperty("WORLD_MAP"));
        int initialGrassNumber = Integer.parseInt(props.getProperty("INITIAL_GRASS_NUMBER"));
        int energyFromGrass = Integer.parseInt(props.getProperty("ENERGY_FROM_GRASS"));
        int dailyNewGrass = Integer.parseInt(props.getProperty("DAILY_NEW_GRASS"));
        GrassGeneratorType grassGenerator = GrassGeneratorType.idToGrassGenerator(props.getProperty("GRASS_GENERATOR"));
        int initialAnimalNumber = Integer.parseInt(props.getProperty("INITIAL_ANIMAL_NUMBER"));
        int initialAnimalEnergy = Integer.parseInt(props.getProperty("INITIAL_ANIMAL_ENERGY"));
        int satiatedEnergy = Integer.parseInt(props.getProperty("SATIATED_ENERGY"));
        int energyForNewborn = Integer.parseInt(props.getProperty("ENERGY_FOR_NEWBORN"));
        int minMutations = Integer.parseInt(props.getProperty("MIN_MUTATIONS"));
        int maxMutations = Integer.parseInt(props.getProperty("MAX_MUTATIONS"));
        MutatorType mutator = MutatorType.idToMutatorType(props.getProperty("MUTATOR"));
        int genomeSize = Integer.parseInt(props.getProperty("GENOME_SIZE"));
        AnimalBehaviourType animalBehaviour = AnimalBehaviourType.idToAnimalBehaviour(props.getProperty("ANIMAL_BEHAVIOUR"));

        return new Config(
                name,
                mapWidth,
                mapHeight,
                worldMap,
                initialGrassNumber,
                energyFromGrass,
                dailyNewGrass,
                grassGenerator,
                initialAnimalNumber,
                initialAnimalEnergy,
                satiatedEnergy,
                energyForNewborn,
                minMutations,
                maxMutations,
                mutator,
                genomeSize,
                animalBehaviour,
                false
        );
    }
}
