package agh.ics.oop.gui;

import agh.ics.oop.AnimalBehaviours.AnimalBehaviourType;
import agh.ics.oop.Config;
import agh.ics.oop.GrassGenerators.GrassGeneratorType;
import agh.ics.oop.Mutators.MutatorType;
import agh.ics.oop.World;
import agh.ics.oop.WorldMaps.WorldMapType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigEditor extends VBox {
    private final ComboBox<ConfigFile> configChoice = new ComboBox<>();
    private final TextField mapWidthField = new TextField();
    private final TextField mapHeightField = new TextField();
    private final ComboBox<WorldMapType> worldMapField = new ComboBox<>();
    private final TextField initialGrassNumberField = new TextField();
    private final TextField energyFromGrassField = new TextField();
    private final TextField dailyNewGrassField = new TextField();
    private final ComboBox<GrassGeneratorType> grassGeneratorField = new ComboBox<>();
    private final TextField initialAnimalNumberField = new TextField();
    private final TextField initialAnimalEnergyField = new TextField();
    private final TextField satiatedEnergyField = new TextField();
    private final TextField energyForNewbornField = new TextField();
    private final TextField minMutationsField = new TextField();
    private final TextField maxMutationsField = new TextField();
    private final ComboBox<MutatorType> mutatorField = new ComboBox<>();
    private final TextField genomeSizeField = new TextField();
    private final ComboBox<AnimalBehaviourType> animalBehaviourField = new ComboBox<>();
    private final HashMap<TextField, Range> correctValuesRange = new HashMap<>();
    private final List<TextField> textfields = Arrays.asList(
            mapWidthField,
            mapHeightField,
            initialGrassNumberField,
            energyFromGrassField,
            dailyNewGrassField,
            initialAnimalNumberField,
            initialAnimalEnergyField,
            satiatedEnergyField,
            energyForNewbornField,
            minMutationsField,
            maxMutationsField,
            genomeSizeField
    );

    public ConfigEditor() {
        List<ConfigFile> configs = null;
        try {
            URL configsDirectory = World.class.getResource("configs/");
            configs = Files.walk(Paths.get(configsDirectory.toURI()))
                    .filter(Files::isRegularFile)
                    .map(ConfigFile::fromPath)
                    .toList();
        } catch (IOException | URISyntaxException exception) {
            exception.printStackTrace();
        }

        configChoice.getItems().setAll(configs);
        configChoice.setPrefWidth(300);

        configChoice.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if(newValue != null ) {
                updateTextFields(newValue.config);
            }
        });

        configChoice.getSelectionModel().select(configs
                .stream()
                .filter(el -> el.name.equals("Normalny"))
                .findFirst()
                .get());

        correctValuesRange.put(mapWidthField, new Range(0, 150));
        correctValuesRange.put(mapHeightField, new Range(0, 150));
        correctValuesRange.put(initialGrassNumberField, new Range( 0, 20000));
        correctValuesRange.put(energyFromGrassField, new Range( 0, 1000));
        correctValuesRange.put(dailyNewGrassField, new Range( 0, 20000));
        correctValuesRange.put(initialAnimalNumberField, new Range( 0, 20000));
        correctValuesRange.put(initialAnimalEnergyField, new Range( 0, 1000));
        correctValuesRange.put(satiatedEnergyField, new Range( 0, 1000));
        correctValuesRange.put(energyForNewbornField, new Range( 0, 1000));
        correctValuesRange.put(minMutationsField, new Range( 0, Integer.parseInt(genomeSizeField.getText())));
        correctValuesRange.put(maxMutationsField, new Range( 0, Integer.parseInt(genomeSizeField.getText())));
        correctValuesRange.put(genomeSizeField, new Range( 0, 1000));

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(10);

        grid.add(new Label("Szerokość mapy:"), 0, 0);
        grid.add(new Label("Wysokość mapy:"), 0, 1);
        grid.add(new Label("Rodzaj mapy:"), 0, 2);
        grid.add(new Label("Początkowa liczba pól trawy:"), 0, 3);
        grid.add(new Label("Energia uzyskiwana z jednego pola trawy:"), 0, 4);
        grid.add(new Label("Dzienny przyrost trawy:"), 0, 5);
        grid.add(new Label("Typ generatora trawy:"), 0, 6);
        grid.add(new Label("Początkowa liczba zwierząt:"), 0, 7);
        grid.add(new Label("Początkowa energia zwierząt:"), 0, 8);
        grid.add(new Label("Warunek najedzenia:"), 0, 9);
        grid.add(new Label("Energia oddawana młodemu:"), 0, 10);
        grid.add(new Label("Minimalna liczba mutacji:"), 0, 11);
        grid.add(new Label("Maksymalna liczba mutacji:"), 0, 12);
        grid.add(new Label("Typ mutacji:"), 0, 13);
        grid.add(new Label("Wielkość genomu:"), 0, 14);
        grid.add(new Label("Wariant ekspresji genów:"), 0, 15);

        ObservableList<WorldMapType> worldTypes = FXCollections.observableArrayList(WorldMapType.class.getEnumConstants());
        worldMapField.getItems().setAll(worldTypes);
        ObservableList<GrassGeneratorType> grassGeneratorTypes = FXCollections.observableArrayList(GrassGeneratorType.class.getEnumConstants());
        grassGeneratorField.getItems().setAll(grassGeneratorTypes);
        ObservableList<MutatorType> mutatorTypes = FXCollections.observableArrayList(MutatorType.class.getEnumConstants());
        mutatorField.getItems().setAll(mutatorTypes);
        ObservableList<AnimalBehaviourType> animalBehaviourTypes = FXCollections.observableArrayList(AnimalBehaviourType.class.getEnumConstants());
        animalBehaviourField.getItems().setAll(animalBehaviourTypes);

        for(TextField textField : textfields) {
            textField.setPrefWidth(300);
        }
        worldMapField.setPrefWidth(300);
        grassGeneratorField.setPrefWidth(300);
        mutatorField.setPrefWidth(300);
        animalBehaviourField.setPrefWidth(300);

        grid.add(mapWidthField, 1, 0);
        grid.add(mapHeightField, 1, 1);
        grid.add(worldMapField, 1, 2);
        grid.add(initialGrassNumberField, 1, 3);
        grid.add(energyFromGrassField, 1, 4);
        grid.add(dailyNewGrassField, 1, 5);
        grid.add(grassGeneratorField, 1, 6);
        grid.add(initialAnimalNumberField, 1, 7);
        grid.add(initialAnimalEnergyField, 1, 8);
        grid.add(satiatedEnergyField, 1, 9);
        grid.add(energyForNewbornField, 1, 10);
        grid.add(minMutationsField, 1, 11);
        grid.add(maxMutationsField, 1, 12);
        grid.add(mutatorField, 1, 13);
        grid.add(genomeSizeField, 1, 14);
        grid.add(animalBehaviourField, 1, 15);

        for(TextField textField : textfields) {
            createErrorListener(textField);
        }

        genomeSizeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(correctValue(newValue, genomeSizeField)) {
                correctValuesRange.put(minMutationsField, new Range(0, Integer.parseInt(newValue)));
                correctValuesRange.put(maxMutationsField, new Range(0, Integer.parseInt(newValue)));
            }
        });

        Button startButton = new Button("Uruchom symulację");
        startButton.setPrefWidth(200);
        startButton.setOnAction(event -> {
            if(textfields.stream().allMatch(textfield -> correctValue(textfield.getText(), textfield))) {
                SimulationWindow.start(createConfig());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText(null);
                alert.setContentText("Parametry nie są poprawne!");

                alert.showAndWait();
            }
        });

        this.setAlignment(Pos.CENTER);
        this.setSpacing(40);
        this.setPadding(new Insets(40));
        this.getChildren().add(configChoice);
        this.getChildren().add(grid);
        this.getChildren().add(startButton);
    }

    private void updateTextFields(Config config) {
        mapWidthField.setText(Integer.toString(config.mapWidth()));
        mapHeightField.setText(Integer.toString(config.mapHeight()));
        worldMapField.getSelectionModel().select(config.worldMap());
        initialGrassNumberField.setText(Integer.toString(config.initialGrassNumber()));
        energyFromGrassField.setText(Integer.toString(config.energyFromGrass()));
        dailyNewGrassField.setText(Integer.toString(config.dailyNewGrass()));
        grassGeneratorField.getSelectionModel().select(config.grassGenerator());
        initialAnimalNumberField.setText(Integer.toString(config.initialAnimalNumber()));
        initialAnimalEnergyField.setText(Integer.toString(config.initialAnimalEnergy()));
        satiatedEnergyField.setText(Integer.toString(config.satiatedEnergy()));
        energyForNewbornField.setText(Integer.toString(config.energyForNewborn()));
        minMutationsField.setText(Integer.toString(config.minMutations()));
        maxMutationsField.setText(Integer.toString(config.maxMutations()));
        mutatorField.getSelectionModel().select(config.mutator());
        genomeSizeField.setText(Integer.toString(config.genomeSize()));
        animalBehaviourField.getSelectionModel().select(config.animalBehaviour());
    }

    private Config createConfig() {
        return new Config(
            "własny",
            Integer.parseInt(mapWidthField.getText()),
            Integer.parseInt(mapHeightField.getText()),
            worldMapField.getValue(),
            Integer.parseInt(initialGrassNumberField.getText()),
            Integer.parseInt(energyFromGrassField.getText()),
            Integer.parseInt(dailyNewGrassField.getText()),
            grassGeneratorField.getValue(),
            Integer.parseInt(initialAnimalNumberField.getText()),
            Integer.parseInt(initialAnimalEnergyField.getText()),
            Integer.parseInt(satiatedEnergyField.getText()),
            Integer.parseInt(energyForNewbornField.getText()),
            Integer.parseInt(minMutationsField.getText()),
            Integer.parseInt(maxMutationsField.getText()),
            mutatorField.getValue(),
            Integer.parseInt(genomeSizeField.getText()),
            animalBehaviourField.getValue()
        );
    }

    private boolean correctValue(String value, TextField field) {
        if(value.equals("")) {
            return false;
        }

        if(value.matches("\\d*")) {
            int numValue = Integer.parseInt(value);
            int min = correctValuesRange.get(field).low;
            int max = correctValuesRange.get(field).high;
            return numValue >= min && numValue <= max;
        }
        return false;
    }

    private void createErrorListener(TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if(correctValue(newValue, field)) {
                field.setBorder(null);
            } else {
                field.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(2), new Insets(-2))));
            }
        });
    }

    private record ConfigFile(
            String name,
            Config config
    ) {
        public static ConfigFile fromPath(Path path) {
            Config config = Config.getFromFile(path);
            return new ConfigFile(config.name(), config);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private record Range(int low, int high) {}
}