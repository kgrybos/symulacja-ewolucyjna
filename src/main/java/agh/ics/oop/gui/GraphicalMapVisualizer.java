package agh.ics.oop.gui;

import agh.ics.oop.AbstractWorldMap;
import agh.ics.oop.AbstractMapElement;
import agh.ics.oop.Vector2d;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class GraphicalMapVisualizer {
    private final AbstractWorldMap map;
    public final GridPane gridPane;
    private static final int ROW_SIZE = 50;
    private static final int COLUMN_SIZE = 50;
    public GraphicalMapVisualizer(AbstractWorldMap map) {
        this.map = map;
        gridPane = new GridPane();
    }

    public void render() {
        gridPane.setGridLinesVisible(false);
        gridPane.getChildren().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getColumnConstraints().clear();
        gridPane.setGridLinesVisible(true);

        Label legend = new Label("y/x");
        GridPane.setHalignment(legend, HPos.CENTER);
        gridPane.add(legend, 0, 0);
        gridPane.getColumnConstraints().add(new ColumnConstraints(COLUMN_SIZE));
        gridPane.getRowConstraints().add(new RowConstraints(ROW_SIZE));

        Vector2d upperRight = map.upperRight;
        Vector2d lowerLeft = map.lowerLeft;

        int numberOfColumns = upperRight.x - lowerLeft.x+1;
        int numberOfRows = upperRight.y - lowerLeft.y+1;

        // Columns legend
        for(int i = 0; i < numberOfColumns; i++) {
            Label label = new Label(Integer.toString(lowerLeft.x+i));
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.add(label, i+1, 0);
            gridPane.getColumnConstraints().add(new ColumnConstraints(COLUMN_SIZE));
        }

        // Rows legend
        for(int i = 0; i < numberOfRows; i++) {
            Label label = new Label(Integer.toString(upperRight.y-i));
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.add(label, 0, i+1);
            gridPane.getRowConstraints().add(new RowConstraints(ROW_SIZE));
        }

        // Map elements
        for(int column = 0; column < numberOfColumns; column++) {
            for(int row = 0; row < numberOfRows; row++) {
                Vector2d position = new Vector2d(lowerLeft.x + column, upperRight.y - row );
                if(map.isOccupied(position)) {
                    AbstractMapElement element = map.objectAt(position);
                    if(element != null) {
                        GuiElementBox geb = new GuiElementBox(element.getImageFilename(), element.toString());
                        GridPane.setHalignment(geb, HPos.CENTER);
                        gridPane.add(geb, column + 1, row + 1);
                    }
                }
            }
        }
    }
}
