package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class App extends Application implements IPositionChangeObserver {
    private GrassField worldMap;
    private Stage primaryStage;
    private GraphicalMapVisualizer graphicalMapVisualizer;
    private SimulationEngine engine;

    @Override
    public void init() throws Exception {
        super.init();

        try {
//            MoveDirection[] directions = OptionsParser.parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
            Vector2d[] positions = {new Vector2d(3, 4), new Vector2d(1, 4)};

            worldMap = new GrassField(10);
            graphicalMapVisualizer = new GraphicalMapVisualizer(worldMap);

            List<Animal> animals = new ArrayList<>();
            for (Vector2d position : positions) {
                Animal newAnimal = new Animal(worldMap, position);
                worldMap.place(newAnimal);
                newAnimal.addObserver(this);
                animals.add(newAnimal);
            }

            engine = new SimulationEngine(animals, 300);
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void start(Stage primaryStage) {
        System.out.println(worldMap.toString());

        this.primaryStage = primaryStage;
        graphicalMapVisualizer.render();

        TextField textField = new TextField();
        textField.setPrefWidth(500);

        Button button = new Button("Start");
        button.setOnAction((ActionEvent event) -> {
            engine.setMoveDirections(textField.getText());
            Thread engineThread = new Thread(engine);
            engineThread.start();
        });

        HBox menu = new HBox(20);
        menu.getChildren().addAll(textField, button);
        menu.setAlignment(Pos.CENTER);

        VBox main = new VBox();
        main.getChildren().addAll(graphicalMapVisualizer.gridPane, menu);
        VBox.setMargin(menu, new Insets(20));

        Scene scene = new Scene(main);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Platform.runLater(() -> {
            graphicalMapVisualizer.render();
            primaryStage.sizeToScene();
        });
    }
}
