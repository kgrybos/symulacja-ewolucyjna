package agh.ics.oop.WorldMaps;

import agh.ics.oop.*;
import agh.ics.oop.observers.*;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.*;

public abstract class AbstractWorldMap implements IElementEventObserver {
    protected final Multimap<Vector2d, AbstractMapElement> mapElements = ArrayListMultimap.create();
    protected final List<IPositionsChangedObserver> positionsChangedObservers = new ArrayList<>();
    public final int width;
    public final int height;
    public final Boundary boundary;
    private final Comparator<Animal> comparator = Comparator.comparing(Animal::getEnergy)
            .thenComparing(Animal::getDaysAlive)
            .thenComparing(Animal::getNumberOfChildren)
            .thenComparing(System::identityHashCode);

    public AbstractWorldMap(int width, int height) {
        this.width = width;
        this.height = height;

        Vector2d lowerLeft = new Vector2d(0, 0);
        Vector2d upperRight  = new Vector2d(width-1, height-1);
        boundary = new Boundary(lowerLeft, upperRight);
    }

    @Override
    public void handleElementEvent(ElementEvent elementEvent) {
        if(elementEvent instanceof BirthEvent event) {
            mapElements.put(event.position, event.element);
            notifyPositionsChangedObservers(Collections.singletonList(event.position));
        } else if(elementEvent instanceof PositionChangedEvent event) {
            mapElements.remove(event.oldPosition, event.element);
            mapElements.put(event.newPosition, event.element);
            notifyPositionsChangedObservers(Arrays.asList(event.oldPosition, event.newPosition));
        } else if (elementEvent instanceof DeathEvent event) {
            mapElements.remove(event.position, event.element);
            notifyPositionsChangedObservers(Collections.singletonList(event.position));
        }
    }

    public boolean isOccupied(Vector2d position) {
        return ! objectsAt(position).isEmpty();
    }

    public Collection<AbstractMapElement> objectsAt(Vector2d position) {
        return mapElements.get(position);
    }

    public Optional<AbstractMapElement> objectAt(Vector2d position, Class<? extends AbstractMapElement> type) {
        Collection<AbstractMapElement> elements = objectsAt(position);
        return elements
                .stream()
                .filter(type::isInstance)
                .findFirst();
    }

    public int getFood(Animal animal) {
        Animal strongest = mapElements
                .get(animal.getPosition())
                .stream()
                .filter(Animal.class::isInstance)
                .map(Animal.class::cast)
                .max(comparator)
                .orElseThrow(() -> new IllegalArgumentException("Rozbieżność pozycji w Animal i WorldMap"));

        Optional<Grass> grass = mapElements
                .get(animal.getPosition())
                .stream()
                .filter(Grass.class::isInstance)
                .map(Grass.class::cast)
                .findAny();

        if(strongest == animal && grass.isPresent()) {
            grass.get().die();
            return grass.get().getEnergy();
        }

        return 0;
    }

    public Optional<Animal> getPartner(Animal animal) {
        List<Animal> animals = mapElements
                .get(animal.getPosition())
                .stream()
                .filter(Animal.class::isInstance)
                .map(Animal.class::cast)
                .sorted(comparator.reversed())
                .limit(2)
                .toList();

        if(animals.size() == 2) {
            Animal stronger = animals.get(0);
            Animal weaker = animals.get(1);

            if(animal == stronger) {
                return Optional.of(weaker);
            }
        }

        return Optional.empty();
    }

    public abstract PosDir getPosDirToMove(Animal animal, MoveDirection move);

    public void addPositionsChangedObserver(IPositionsChangedObserver observer) {
        positionsChangedObservers.add(observer);
    }

    public void removePositionsChangedObserver(IPositionsChangedObserver observer) {
        positionsChangedObservers.remove(observer);
    }

    public void notifyPositionsChangedObservers(List<Vector2d> position) {
        for(IPositionsChangedObserver observer : positionsChangedObservers) {
            observer.positionsChanged(position);
        }
    }
}