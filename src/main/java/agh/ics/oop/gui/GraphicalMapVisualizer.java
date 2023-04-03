package agh.ics.oop.gui;

import agh.ics.oop.AbstractWorldMap;
import agh.ics.oop.AbstractMapElement;
import agh.ics.oop.IPositionChangeObserver;
import agh.ics.oop.Vector2d;
import javafx.application.Platform;
import javafx.geometry.HPos;
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

    private static final double GRID_SIZE = 800;

    public GraphicalMapVisualizer(AbstractWorldMap map) {
        this.map = map;
        cells = new ImageView[map.width][map.height];

        gridPane = new GridPane();
        gridPane.setHgap(0);
        gridPane.setVgap(0);

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
//                double imageSize = 0.9*cellSize;
                imageView.setFitWidth(cellSize);
                imageView.setFitHeight(cellSize);

                GridPane.setHalignment(imageView, HPos.CENTER);
                gridPane.add(imageView, column, row);
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
