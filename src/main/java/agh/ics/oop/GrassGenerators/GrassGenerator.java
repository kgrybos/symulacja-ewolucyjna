package agh.ics.oop.GrassGenerators;

import agh.ics.oop.SimulationEngine;
import agh.ics.oop.WorldMaps.AbstractWorldMap;

public interface GrassGenerator {
    void generate(AbstractWorldMap map, SimulationEngine engine, int number);
}
