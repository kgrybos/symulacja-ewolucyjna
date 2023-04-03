package agh.ics.oop;

public class Grass extends AbstractMapElement {
    Grass(Vector2d position) { posDir = new PosDir(position); }

    @Override
    public String toString() {
        return "Trawa";
    }

    @Override
    public String getImageFilename() {
        return "grass.png";
    }
}
