package agh.ics.oop.observers;

import agh.ics.oop.AbstractMapElement;
import agh.ics.oop.Vector2d;

public class DeathEvent extends ElementEvent {
    public final Vector2d position;
    public final AbstractMapElement element;

    public DeathEvent(Vector2d position, AbstractMapElement element) {
        this.position = position;
        this.element = element;
    }
}
