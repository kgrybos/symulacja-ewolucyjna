package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {
    private GrassField worldMap;

    @Override
    public void init() throws Exception {
        super.init();

        try {
            MoveDirection[] directions = OptionsParser.parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
            worldMap = new GrassField(10);
            Vector2d[] positions = {new Vector2d(3, 4), new Vector2d(1, 4)};
            IEngine engine = new SimulationEngine(directions, worldMap, positions);
            engine.run();
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void start(Stage primaryStage) {
        System.out.println(worldMap.toString());

        GridPane grid = worldMap.render();

        Scene scene = new Scene(grid);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
