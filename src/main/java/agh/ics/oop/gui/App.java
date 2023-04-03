package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    private SimulationEngine engine;

    @Override
    public void init() throws Exception {
        super.init();

        Config config = Config.getFromFile("normal");

        engine = new SimulationEngine(100, config);
    }

    public void start(Stage primaryStage) {
        GraphicalMapVisualizer graphicalMapVisualizer = engine.getGraphicalMapVisualizer();
        graphicalMapVisualizer.full_render();

        AnimalStatsBox animalStatsBox = new AnimalStatsBox();
        animalStatsBox.setChoices(engine.getAnimals());

        HBox mainContainer = new HBox(animalStatsBox, graphicalMapVisualizer.gridPane);
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

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation");
        primaryStage.show();
    }
}
