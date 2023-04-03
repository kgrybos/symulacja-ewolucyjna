package agh.ics.oop.gui;

import agh.ics.oop.IWorldMap;
import agh.ics.oop.Vector2d;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class GraphicalMapVisualizer {
    private final IWorldMap map;
    private static final int ROW_SIZE = 25;
    private static final int COLUMN_SIZE = 25;
    public GraphicalMapVisualizer(IWorldMap map) {
        this.map = map;
    }

    private void addCell(String label, int column, int row, GridPane gridPane) {
        Label coord = new Label(label);
        GridPane.setHalignment(coord, HPos.CENTER);
        gridPane.add(coord, column, row);
    }

    public GridPane render(Vector2d lowerLeft, Vector2d upperRight) {
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        Label legend = new Label("y/x");
        GridPane.setHalignment(legend, HPos.CENTER);
        gridPane.add(legend, 0, 0);
        gridPane.getColumnConstraints().add(new ColumnConstraints(COLUMN_SIZE));
        gridPane.getRowConstraints().add(new RowConstraints(ROW_SIZE));

        int numberOfColumns = upperRight.x - lowerLeft.x+1;
        int numberOfRows = upperRight.y - lowerLeft.y+1;

        // Columns legend
        for(int i = 0; i < numberOfColumns; i++) {
            addCell(Integer.toString(lowerLeft.x+i), i+1, 0, gridPane);
            gridPane.getColumnConstraints().add(new ColumnConstraints(COLUMN_SIZE));
        }

        // Rows legend
        for(int i = 0; i < numberOfRows; i++) {
            addCell(Integer.toString(upperRight.y-i), 0, i+1, gridPane);
            gridPane.getRowConstraints().add(new RowConstraints(ROW_SIZE));
        }

        // Map elements
        for(int column = 0; column < numberOfColumns; column++) {
            for(int row = 0; row < numberOfRows; row++) {
                Vector2d position = new Vector2d(lowerLeft.x + column, upperRight.y - row );
                if(map.isOccupied(position)) {
                    Object object = map.objectAt(position);
                    if(object != null) {
                        addCell(object.toString(), column+1, row+1, gridPane);
                    }
                }
            }
        }

        return gridPane;
    }
}
