package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
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

        List<IAnimalEventObserver> observers = new ArrayList<>();
        observers.add(worldMap);
        observers.add(engine);
        observers.add(graphicalMapVisualizer);
        for (Vector2d position : positions) {
            new Animal(
                    random,
                    observers,
                    8,
                    worldMap,
                    position
            );
        }

        EquatorGrassGenerator equatorGrassGenerator = new EquatorGrassGenerator(random, worldMap.width, worldMap.height);
        equatorGrassGenerator.generate(worldMap, 300);
    }

    public void start(Stage primaryStage) {
        System.out.println(worldMap.toString());

        graphicalMapVisualizer.full_render();

        Thread engineThread = new Thread(engine);
        engineThread.start();

        Scene scene = new Scene(graphicalMapVisualizer.gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
