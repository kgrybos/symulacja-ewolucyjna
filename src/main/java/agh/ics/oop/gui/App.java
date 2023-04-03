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
import java.util.Random;

public class App extends Application {
    private Globe worldMap;
    private GraphicalMapVisualizer graphicalMapVisualizer;
    private SimulationEngine engine;

    @Override
    public void init() throws Exception {
        super.init();

        Vector2d[] positions = {new Vector2d(3, 4), new Vector2d(25, 25), new Vector2d(43, 12)};

        worldMap = new Globe(50, 50);
        graphicalMapVisualizer = new GraphicalMapVisualizer(worldMap);

        Random random = new Random(0);

        engine = new SimulationEngine(300);
        for (Vector2d position : positions) {
            new Animal.Builder(worldMap)
                    .setRandom(random)
                    .addAnimalEventObserver(worldMap)
                    .addAnimalEventObserver(engine)
                    .addAnimalEventObserver(graphicalMapVisualizer)
                    .setPosDir(new PosDir(position))
                    .buildNew(8);
        }

        EquatorGrassGenerator equatorGrassGenerator = new EquatorGrassGenerator(random, worldMap.width, worldMap.height);
        equatorGrassGenerator.generate(worldMap, 300);
    }

    public void start(Stage primaryStage) {
        System.out.println(worldMap.toString());

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
