package agh.ics.oop.gui;

import agh.ics.oop.Config;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.SimulationStatsCSVWriter;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.stage.Stage;

import java.io.IOException;

public class SimulationWindow {
    public static void start(Config config) {
        SimulationEngine engine = new SimulationEngine(100, config);
        GraphicalMapVisualizer graphicalMapVisualizer = engine.getGraphicalMapVisualizer();
        graphicalMapVisualizer.full_render();

        AnimalStatsBox animalStatsBox = new AnimalStatsBox();
        animalStatsBox.setChoices(engine.getAnimals());

        SimulationStatsBox simulationStatsBox = new SimulationStatsBox();
        engine.addObserver(simulationStatsBox);

        SimulationStatsCSVWriter simulationStatsCSVWriter = null;
        if(config.saveStats()) {
            try {
                simulationStatsCSVWriter = new SimulationStatsCSVWriter();
                engine.addObserver(simulationStatsCSVWriter);
            } catch (IOException exception) {
                System.err.println("Nie udało się utworzyć pliku na statystyki symulacji");
                exception.printStackTrace();
            }
        }

        HBox mainContainer = new HBox(animalStatsBox, graphicalMapVisualizer.gridPane, simulationStatsBox);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setSpacing(20);

        Button startStopButton = new Button("Start");
        startStopButton.setPrefWidth(250);

        Thread engineThread = new Thread(engine);
        engineThread.start();
        startStopButton.setOnAction(event -> {
            if(engine.isPaused()) {
                engine.resume();
                animalStatsBox.confirmChoice();
            } else {
                engine.pause();
                animalStatsBox.setChoices(engine.getAnimals());
            }
            startStopButton.setText(engine.isPaused() ? "Start" : "Stop");
        });

        VBox root = new VBox(20);
        root.getChildren().addAll(mainContainer, startStopButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Symulacja");
        SimulationStatsCSVWriter finalSimulationStatsCSVWriter = simulationStatsCSVWriter;
        stage.setOnCloseRequest(event -> {
            engineThread.interrupt();
            if(config.saveStats()) {
                finalSimulationStatsCSVWriter.close();
            }
        });
        stage.show();
    }
}
