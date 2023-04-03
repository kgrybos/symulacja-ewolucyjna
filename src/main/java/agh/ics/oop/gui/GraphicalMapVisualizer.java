package agh.ics.oop.gui;

import agh.ics.oop.AbstractWorldMap;
import agh.ics.oop.AbstractMapElement;
import agh.ics.oop.IPositionChangeObserver;
import agh.ics.oop.Vector2d;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.InputStream;
import java.util.Objects;

public class GraphicalMapVisualizer implements IPositionChangeObserver {
    private final AbstractWorldMap map;
    public final GridPane gridPane;
    private final ImageView[][] cells;

    public static final double GRID_SIZE = 800;

    public GraphicalMapVisualizer(AbstractWorldMap map) {
        this.map = map;
        cells = new ImageView[map.width][map.height];

        gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        double fieldSize;
        if(map.width > map.height) {
            fieldSize = GRID_SIZE/map.width;
        } else {
            fieldSize = GRID_SIZE/map.height;
        }

        for(int i = 0; i < map.width+1; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(fieldSize));
        }

        for(int i = 0; i < map.width+1; i++) {
            gridPane.getRowConstraints().add(new RowConstraints(fieldSize));
        }

        Label legend = new Label("y/x");
        GridPane.setHalignment(legend, HPos.CENTER);
        gridPane.add(legend, 0, 0);

        // Columns legend
        for(int i = 0; i < map.width; i++) {
            Label label = new Label(Integer.toString(i));
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.add(label, i+1, 0);
        }

        // Rows legend
        for(int i = 0; i < map.height; i++) {
            Label label = new Label(Integer.toString(map.height-1-i));
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.add(label, 0, i+1);
        }

        for(int column = 0; column < map.width; column++) {
            for(int row = 0; row < map.height; row++) {
                ImageView imageView = new ImageView();
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);

                GridPane.setHalignment(imageView, HPos.CENTER);
                gridPane.add(imageView, column + 1, row + 1);
                cells[column][map.height-1-row] = imageView;
            }
        }
    }

    private void updateImage(Vector2d position) {
        AbstractMapElement element = map.objectAt(position);
        if(element != null) {
            InputStream stream = Objects.requireNonNull(getClass().getResourceAsStream(element.getImageFilename()));
            Image image = new Image(stream);
            cells[position.x][position.y].setImage(image);
        }
    }

    public void full_render() {
        // Map elements
        for(int column = 0; column < map.width; column++) {
            for(int row = 0; row < map.height; row++) {
                Vector2d position = new Vector2d(column, map.height-1 - row );
                if(map.isOccupied(position)) {
                    updateImage(position);
                }
            }
        }
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, AbstractMapElement element) {
        Platform.runLater(() -> {
            cells[oldPosition.x][oldPosition.y].setImage(null);
            cells[newPosition.x][newPosition.y].setImage(null);

            updateImage(oldPosition);
            updateImage(newPosition);
        });
    }
}
