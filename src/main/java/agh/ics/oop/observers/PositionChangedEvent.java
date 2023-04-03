package agh.ics.oop.observers;

import agh.ics.oop.WorldMaps.AbstractMapElement;
import agh.ics.oop.Vector2d;

public class PositionChangedEvent extends ElementEvent {
    public final Vector2d oldPosition;
    public final Vector2d newPosition;
    public final AbstractMapElement element;

    public PositionChangedEvent(Vector2d oldPosition, Vector2d newPosition, AbstractMapElement element) {
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
        this.element = element;
    }
}
