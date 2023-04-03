package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class App extends Application implements IPositionChangeObserver {
    private GrassField worldMap;
    private Stage primaryStage;
    private Scene scene;

    @Override
    public void init() throws Exception {
        super.init();

        try {
            MoveDirection[] directions = OptionsParser.parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
            Vector2d[] positions = {new Vector2d(3, 4), new Vector2d(1, 4)};

            worldMap = new GrassField(10);

            List<Animal> animals = new ArrayList<>();
            for (Vector2d position : positions) {
                Animal newAnimal = new Animal(worldMap, position);
                worldMap.place(newAnimal);
                newAnimal.addObserver(this);
                animals.add(newAnimal);
            }

            Runnable engine = new SimulationEngine(directions, animals, 300);
            Thread engineThread = new Thread(engine);
            engineThread.start();
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void start(Stage primaryStage) {
        System.out.println(worldMap.toString());

        this.primaryStage = primaryStage;
        GridPane grid = worldMap.render();
        scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Platform.runLater(() -> {
            GridPane grid = worldMap.render();
            scene.setRoot(grid);
            primaryStage.show();
        });
    }
}
