package agh.ics.oop.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class GuiElementBox extends VBox {
    public GuiElementBox(String imageFile, String labelText) {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imageFile)));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        Label label = new Label(labelText);

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(imageView, label);
    }
}
