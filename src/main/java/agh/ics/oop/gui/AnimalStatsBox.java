package agh.ics.oop.gui;

import agh.ics.oop.*;
import agh.ics.oop.observers.IAnimalStatsObserver;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Arrays;

public class AnimalStatsBox extends VBox implements IAnimalStatsObserver {
    private final ComboBox<Animal> animalChoice = new ComboBox<>();
    private final Label genesLabel;
    private final Label activeGeneLabel;
    private final Label energyLabel;
    private final Label grassEatenLabel;
    private final Label numberOfChildrenLabel;
    private final Label dayAliveLabel;
    private final Label deathDayLabel;
    public AnimalStatsBox() {
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setPrefWidth(300);

        grid.add(new Label("Geny:"), 0, 0);
        grid.add(new Label("Aktywny gen:"), 0, 1);
        grid.add(new Label("Energia:"), 0, 2);
        grid.add(new Label("Liczba zjedzonej trawy:"), 0, 3);
        grid.add(new Label("Liczba dzieci:"), 0, 4);
        grid.add(new Label("Przeżyte dni:"), 0, 5);
        grid.add(new Label("Śmierć dnia:"), 0, 6);

        genesLabel = new Label();
        activeGeneLabel = new Label();
        energyLabel = new Label();
        grassEatenLabel = new Label();
        numberOfChildrenLabel = new Label();
        dayAliveLabel = new Label();
        deathDayLabel = new Label();

        grid.add(genesLabel, 1, 0);
        grid.add(activeGeneLabel, 1, 1);
        grid.add(energyLabel, 1, 2);
        grid.add(grassEatenLabel, 1, 3);
        grid.add(numberOfChildrenLabel, 1, 4);
        grid.add(dayAliveLabel, 1, 5);
        grid.add(deathDayLabel, 1, 6);

        animalChoice.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if(newValue != null) {
                if (oldValue != null) {
                    oldValue.removeStatsObserver(this);
                }
                newValue.addStatsObserver(this);
                updateAnimalStats(newValue.getStats());
            } else {
                animalChoice.getSelectionModel().select(oldValue);
            }
        });

        this.setSpacing(30);
        this.getChildren().add(animalChoice);
        this.getChildren().add(grid);
    }

    public void updateAnimalStats(AnimalStats animalStats) {
        Platform.runLater(() -> {
            genesLabel.setText(Arrays.toString(animalStats.genes()));
            activeGeneLabel.setText(Integer.toString(animalStats.activeGene()));
            energyLabel.setText(Integer.toString(animalStats.energy()));
            grassEatenLabel.setText(Integer.toString(animalStats.grassEaten()));
            numberOfChildrenLabel.setText(Integer.toString(animalStats.numberOfChildren()));
            dayAliveLabel.setText(Integer.toString(animalStats.daysAlive()));
            if(animalStats.deathDay().isPresent()) {
                deathDayLabel.setText(Integer.toString(animalStats.deathDay().get()));
            } else {
                deathDayLabel.setText("jeszcze żyje");
            }
        });
    }

    public void setChoices(Animal[] animals) {
        animalChoice.getItems().setAll(animals);
        animalChoice.setDisable(false);
    }

    public void confirmChoice() {
        animalChoice.setDisable(true);
    }
}
