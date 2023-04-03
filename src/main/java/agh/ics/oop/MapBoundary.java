package agh.ics.oop;

import javafx.util.Pair;

import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver {
    private final SortedSet<Pair<Vector2d, String>> xMapOrder;
    private final SortedSet<Pair<Vector2d, String>> yMapOrder;

    MapBoundary() {
        Comparator<Pair<Vector2d, String>> compareClass = Comparator.comparing(Pair::getValue);
        Comparator<Pair<Vector2d, String>> compareX = Comparator.comparing(el -> el.getKey().x);
        Comparator<Pair<Vector2d, String>> compareY = Comparator.comparing(el -> el.getKey().y);

        Comparator<Pair<Vector2d, String>> xComparator = compareX;
        xComparator = xComparator.thenComparing(compareY);
        xComparator = xComparator.thenComparing(compareClass);

        Comparator<Pair<Vector2d, String>> yComparator = compareY;
        yComparator = yComparator.thenComparing(compareX);
        yComparator = yComparator.thenComparing(compareClass);

        xMapOrder = new TreeSet<>(xComparator);
        yMapOrder = new TreeSet<>(yComparator);
    }

    public void put(IMapElement element) {
        xMapOrder.add(new Pair<>(element.getPosition(), element.getClass().toString()));
        yMapOrder.add(new Pair<>(element.getPosition(), element.getClass().toString()));
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Pair<Vector2d, String> oldPair = new Pair<>(oldPosition, Animal.class.toString());
        Pair<Vector2d, String> newPair = new Pair<>(newPosition, Animal.class.toString());

        xMapOrder.remove(oldPair);
        yMapOrder.remove(oldPair);
        xMapOrder.add(newPair);
        yMapOrder.add(newPair);
    }

    public Vector2d lowerLeft() {
        return new Vector2d(xMapOrder.first().getKey().x, yMapOrder.first().getKey().y);
    }

    public Vector2d upperRight() {
        return new Vector2d(xMapOrder.last().getKey().x, yMapOrder.last().getKey().y);
    }
}
