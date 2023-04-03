package agh.ics.oop;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public record Config(
        int mapWidth,
        int mapHeight,
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
        AnimalBehaviourType animalBehaviour
) {
    public static Config getFromFile(String configName) {
        Properties props;
        try {
            InputStream propsInput = World.class.getResourceAsStream(configName + ".properties");
            props = new Properties();
            props.load(propsInput);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        int mapWidth = Integer.parseInt(props.getProperty("MAP_WIDTH"));
        int mapHeight = Integer.parseInt(props.getProperty("MAP_HEIGHT"));
        int initialGrassNumber = Integer.parseInt(props.getProperty("INITIAL_GRASS_NUMBER"));
        int energyFromGrass = Integer.parseInt(props.getProperty("ENERGY_FROM_GRASS"));
        int dailyNewGrass = Integer.parseInt(props.getProperty("DAILY_NEW_GRASS"));
        GrassGeneratorType grassGenerator = switch (props.getProperty("GRASS_GENERATOR")) {
            case "equator" -> GrassGeneratorType.EQUATOR;
            //TODO
            case "toxic" -> GrassGeneratorType.TOXIC;
            default -> throw new IllegalArgumentException("Nie ma takiego typu generatora trawy");
        };
        int initialAnimalNumber = Integer.parseInt(props.getProperty("INITIAL_ANIMAL_NUMBER"));
        int initialAnimalEnergy = Integer.parseInt(props.getProperty("INITIAL_ANIMAL_ENERGY"));
        int satiatedEnergy = Integer.parseInt(props.getProperty("SATIATED_ENERGY"));
        int energyForNewborn = Integer.parseInt(props.getProperty("ENERGY_FOR_NEWBORN"));
        int minMutations = Integer.parseInt(props.getProperty("MIN_MUTATIONS"));
        int maxMutations = Integer.parseInt(props.getProperty("MAX_MUTATIONS"));
        MutatorType mutator = switch (props.getProperty("MUTATOR")) {
            case "random" -> MutatorType.RANDOM;
            //TODO
            case "slight" -> MutatorType.SLIGHT;
            default -> throw new IllegalArgumentException("Nie ma takiego wariantu mutacji");
        };
        int genomeSize = Integer.parseInt(props.getProperty("GENOME_SIZE"));
        AnimalBehaviourType animalBehaviour = switch (props.getProperty("ANIMAL_BEHAVIOUR")) {
            case "predestination" -> AnimalBehaviourType.PREDESTINATION;
            //TODO
            case "crazy" -> AnimalBehaviourType.CRAZY;
            default -> throw new IllegalArgumentException("Nie ma takiego wariantu zachowania zwierzęcia");
        };

        return new Config(
                mapWidth,
                mapHeight,
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
                animalBehaviour
        );
    }
}
