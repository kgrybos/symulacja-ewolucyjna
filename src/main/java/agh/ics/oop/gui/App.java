package agh.ics.oop.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public void start(Stage primaryStage) {
        ConfigEditor configEditor = new ConfigEditor();
        Scene scene = new Scene(configEditor);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Konfiguracja symulacji");
        primaryStage.show();
    }
}
