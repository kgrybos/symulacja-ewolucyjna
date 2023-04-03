package agh.ics.oop;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;

public abstract class AbstractWorldMap implements IPositionChangeObserver {
    protected final Multimap<Vector2d, AbstractMapElement> mapElements = ArrayListMultimap.create();
    public final int width;
    public final int height;
    public final Vector2d lowerLeft;
    public final Vector2d upperRight;

    public AbstractWorldMap(int width, int height) {
        this.width = width;
        this.height = height;

        lowerLeft = new Vector2d(0, 0);
        upperRight  = new Vector2d(width-1, height-1);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, AbstractMapElement element) {
        mapElements.remove(oldPosition, element);
        mapElements.put(newPosition, element);
    }

    public boolean place(AbstractMapElement element) {
        Vector2d position = element.getPosition();
        if(isInsideMap(position)) {
            if(element instanceof Animal animal) {
                animal.addObserver(this);
            }
            mapElements.put(position, element);
        } else {
            throw new IllegalArgumentException("Elements can't be placed on position: " + position);
        }

        return true;
    }

    public boolean isOccupied(Vector2d position) {
        return ! objectsAt(position).isEmpty();
    }

    public Collection<AbstractMapElement> objectsAt(Vector2d position) {
        return mapElements.get(position);
    }

    public AbstractMapElement objectAt(Vector2d position) {
        Collection<AbstractMapElement> elements = objectsAt(position);
        return elements
                .stream()
                .filter(el -> el instanceof Animal)
                .findFirst()
                .orElseGet(() -> elements
                        .stream()
                        .findFirst()
                        .orElse(null)
                );
    }

    public boolean isInsideMap(Vector2d position) {
        return position.follows(lowerLeft) && position.precedes(upperRight);
    }

    public abstract PosDir getPosDirToMove(AbstractMapElement element, PosDir requestedPosDir);
}
