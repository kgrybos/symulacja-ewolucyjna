package agh.ics.oop.WorldMaps;

import agh.ics.oop.MapDirection;
import agh.ics.oop.PosDir;
import agh.ics.oop.Vector2d;

public abstract class AbstractMapElement {
    protected PosDir posDir;

    public Vector2d getPosition() { return posDir.position(); }

    public MapDirection getDirection() {
        return posDir.direction();
    }

    public boolean isAt(Vector2d position){ return this.posDir.position().equals(position); }

    public abstract String getImageFilename();
}
