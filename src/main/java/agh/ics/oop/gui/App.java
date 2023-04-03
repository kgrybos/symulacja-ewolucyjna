package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
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
import java.util.Random;

public class App extends Application {
    private Globe worldMap;
    private GraphicalMapVisualizer graphicalMapVisualizer;
    private SimulationEngine engine;

    @Override
    public void init() throws Exception {
        super.init();

        Vector2d[] positions = {new Vector2d(3, 4)};//, new Vector2d(1, 4)};

        worldMap = new Globe(50, 50);
        graphicalMapVisualizer = new GraphicalMapVisualizer(worldMap);

        Random random = new Random(0);

        List<Animal> animals = new ArrayList<>();
        for (Vector2d position : positions) {
            Animal newAnimal = new Animal(random, 8, worldMap, position);
            worldMap.place(newAnimal);
            newAnimal.addObserver(graphicalMapVisualizer);
            animals.add(newAnimal);
        }

        EquatorGrassGenerator equatorGrassGenerator = new EquatorGrassGenerator(random, worldMap.width, worldMap.height);
        equatorGrassGenerator.generate(worldMap, 300);

        for(int i = 0; i < 100; i++) {
            int size = random.nextInt(20)+1;
            Genome g1 = new Genome(random, size);
            Genome g2 = new Genome(random, size);
            new Genome(random, g1, g2, random.nextFloat(), Side.random(random));
        }

        engine = new SimulationEngine(animals, 300);
    }

    public void start(Stage primaryStage) {
        System.out.println(worldMap.toString());

        graphicalMapVisualizer.full_render();

        TextField textField = new TextField("f b r l f f r r f f f f f f f f");
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
}
