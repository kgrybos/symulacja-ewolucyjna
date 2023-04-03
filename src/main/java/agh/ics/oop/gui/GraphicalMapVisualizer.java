package agh.ics.oop.gui;

import agh.ics.oop.*;
import agh.ics.oop.WorldMaps.AbstractMapElement;
import agh.ics.oop.WorldMaps.AbstractWorldMap;
import agh.ics.oop.observers.*;
import com.google.common.primitives.Doubles;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class GraphicalMapVisualizer implements IPositionsChangedObserver {
    private final AbstractWorldMap map;
    public final GridPane gridPane;
    private final ImageView[][] cells;

    private static final double GRID_SIZE = 800;

    public GraphicalMapVisualizer(AbstractWorldMap map) {
        this.map = map;
        cells = new ImageView[map.width][map.height];

        gridPane = new GridPane();
        gridPane.setHgap(0);
        gridPane.setVgap(0);
        gridPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        double cellSize;
        if(map.width > map.height) {
            cellSize = GRID_SIZE/map.width;
        } else {
            cellSize = GRID_SIZE/map.height;
        }

        for(int i = 0; i < map.width+1; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(cellSize));
        }

        for(int i = 0; i < map.width+1; i++) {
            gridPane.getRowConstraints().add(new RowConstraints(cellSize));
        }

        for(int column = 0; column < map.width; column++) {
            for(int row = 0; row < map.height; row++) {
                ImageView imageView = new ImageView();
                imageView.setFitWidth(cellSize);
                imageView.setFitHeight(cellSize);

                GridPane.setHalignment(imageView, HPos.CENTER);
                gridPane.add(imageView, column, row);
                cells[column][map.height-1-row] = imageView;
            }
        }
    }

    private void updateImage(Vector2d position) {
        Optional<AbstractMapElement> element = map
                .objectAt(position, Animal.class)
                .or(() -> map.objectAt(position, Grass.class));

        element.ifPresent(value -> {
            InputStream stream = Objects.requireNonNull(getClass().getResourceAsStream(value.getImageFilename()));
            Image image = new Image(stream);
            cells[position.x][position.y].setImage(image);

            double brightness = 0;
            if(value instanceof Animal animal) {
                brightness = 1-Doubles.constrainToRange(((double) animal.getEnergy()) / 100, 0, 1);
            }

            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(brightness);
            cells[position.x][position.y].setEffect(colorAdjust);
        });
    }

    public void full_render() {
        Platform.runLater(() -> {
            // Map elements
            for (int column = 0; column < map.width; column++) {
                for (int row = 0; row < map.height; row++) {
                    Vector2d position = new Vector2d(column, map.height - 1 - row);
                    if (map.isOccupied(position)) {
                        updateImage(position);
                    }
                }
            }
        });
    }

    @Override
    public void positionsChanged(List<Vector2d> positions) {
        Platform.runLater(() -> {
            for (Vector2d position : positions) {
                cells[position.x][position.y].setImage(null);
                updateImage(position);
            }
        });
    }

    public void highlightAnimals(List<Animal> animals) {
        Platform.runLater(() -> {
            for(Animal animal : animals) {
                Vector2d position = animal.getPosition();
                InputStream stream = Objects.requireNonNull(getClass().getResourceAsStream("highlight.png"));
                Image image = new Image(stream);
                cells[position.x][position.y].setImage(image);
                cells[position.x][position.y].setEffect(null);
            }
        });
    }
}
