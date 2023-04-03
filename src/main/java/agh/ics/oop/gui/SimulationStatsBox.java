package agh.ics.oop.gui;

import agh.ics.oop.SimulationStats;
import agh.ics.oop.observers.ISimulationStatsObserver;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Arrays;

public class SimulationStatsBox extends GridPane implements ISimulationStatsObserver {
    private final Label animalNumber = new Label();
    private final Label grassNumber = new Label();
    private final Label freeNumber = new Label();
    private final Label averageEnergy = new Label();
    private final Label averageLifetime = new Label();
    private final Label genotype1 = new Label();
    private final Label genotype2 = new Label();
    private final Label genotype3 = new Label();
    public SimulationStatsBox() {
        this.setHgap(20);
        this.setPrefWidth(300);

        this.add(new Label("Liczba żywych zwierząt:"), 0, 0);
        this.add(new Label("Liczba trawy:"), 0, 1);
        this.add(new Label("Liczba wolnych pól:"), 0, 2);
        this.add(new Label("Średnia energia zwierząt:"), 0, 3);
        this.add(new Label("Średni czas życia:"), 0, 4);
        this.add(new Label("Najpopularniejsze genotypy:"), 0, 5);

        this.add(animalNumber, 1, 0);
        this.add(grassNumber, 1, 1);
        this.add(freeNumber, 1, 2);
        this.add(averageEnergy, 1, 3);
        this.add(averageLifetime, 1, 4);
        this.add(genotype1, 0, 6, 2, 1);
        this.add(genotype2, 0, 7, 2, 1);
        this.add(genotype3, 0, 8, 2, 1);
    }

    @Override
    public void updateSimulationStats(SimulationStats simulationStats) {
        Platform.runLater(() -> {
            animalNumber.setText(Integer.toString(simulationStats.animalNumber()));
            grassNumber.setText(Integer.toString(simulationStats.grassNumber()));
            freeNumber.setText(Integer.toString(simulationStats.freeNumber()));
            averageEnergy.setText(String.format("%.2f", simulationStats.averageEnergy()));
            averageLifetime.setText(String.format("%.2f", simulationStats.averageLifetime()));
            genotype1.setText("1. " + Arrays.toString(simulationStats.mostPopularGenotypes().get(0)));
            genotype2.setText("2. " + Arrays.toString(simulationStats.mostPopularGenotypes().get(1)));
            genotype3.setText("3. " + Arrays.toString(simulationStats.mostPopularGenotypes().get(2)));
        });
    }
}
