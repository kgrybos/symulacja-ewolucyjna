package agh.ics.oop;

import agh.ics.oop.WorldMaps.AbstractWorldMap;
import agh.ics.oop.WorldMaps.Globe;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimulationEngineTest {
    @Test
    void mostPopularGenomeTest() {
        Config config = Config.getFromFile("zero");
        SimulationEngine simulationEngine = new SimulationEngine(0, config);
        AbstractWorldMap worldMap = new Globe(10, 10);
        Random random = new Random(0);

        for(int i = 0; i < 10; i++) {
            new Animal.Builder(worldMap, config)
                    .addAnimalEventObserver(simulationEngine)
                    .buildBorn(new Genome(random, config, 1));
        }

        assertEquals(MoveDirection.FORWARD_LEFT, simulationEngine.getStats().mostPopularGenotypes().get(0)[0]);
    }
}
