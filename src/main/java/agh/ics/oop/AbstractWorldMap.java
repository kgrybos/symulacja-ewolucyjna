package agh.ics.oop;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;

public abstract class AbstractWorldMap implements IAnimalEventObserver {
    protected final Multimap<Vector2d, AbstractMapElement> mapElements = ArrayListMultimap.create();
    public final int width;
    public final int height;
    public final Boundary boundary;

    public AbstractWorldMap(int width, int height) {
        this.width = width;
        this.height = height;

        Vector2d lowerLeft = new Vector2d(0, 0);
        Vector2d upperRight  = new Vector2d(width-1, height-1);
        boundary = new Boundary(lowerLeft, upperRight);
    }

    @Override
    public void animalEvent(AnimalEvent animalEvent) {
        if(animalEvent instanceof BirthEvent event) {
            mapElements.put(event.position, event.animal);
        } else if(animalEvent instanceof PositionChangedEvent event) {
            mapElements.remove(event.oldPosition, event.animal);
            mapElements.put(event.newPosition, event.animal);
        } else if (animalEvent instanceof DeathEvent event) {
            mapElements.remove(event.position, event.animal);
        }
    }

    public void place(Grass grass) {
        Vector2d position = grass.getPosition();
        if(boundary.isInside(position)) {
            mapElements.put(position, grass);
        } else {
            throw new IllegalArgumentException("Elements can't be placed on position: " + position);
        }
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

    public abstract PosDir getPosDirToMove(AbstractMapElement element, MoveDirection move);
}
