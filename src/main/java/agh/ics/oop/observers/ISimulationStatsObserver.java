package agh.ics.oop.observers;

import agh.ics.oop.SimulationStats;

public interface ISimulationStatsObserver {
    void updateSimulationStats(SimulationStats simulationStats);
}
