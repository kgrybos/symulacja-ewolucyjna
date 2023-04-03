package agh.ics.oop;

import agh.ics.oop.WorldMaps.AbstractMapElement;
import agh.ics.oop.observers.BirthEvent;
import agh.ics.oop.observers.DeathEvent;
import agh.ics.oop.observers.ElementEvent;
import agh.ics.oop.observers.IElementEventObserver;

import java.util.ArrayList;
import java.util.List;

public class Grass extends AbstractMapElement {
    private final List<IElementEventObserver> grassEventObservers;
    private final int energy;

    private Grass(Builder builder) {
        this.posDir = builder.posDir;
        this.grassEventObservers = builder.grassEventObservers;
        this.energy = builder.energy;

        notifyObservers(new BirthEvent(posDir.position(), this));
    }

    @Override
    public String toString() {
        return "Trawa";
    }

    @Override
    public String getImageFilename() {
        return "grass.png";
    }

    public int getEnergy() {
        return energy;
    }

    public void die() {
        notifyObservers(new DeathEvent(posDir.position(), this));
    }

    public void addObserver(IElementEventObserver observer) {
        grassEventObservers.add(observer);
    }

    public void removeObserver(IElementEventObserver observer) {
        grassEventObservers.remove(observer);
    }

    private void notifyObservers(ElementEvent elementEvent) {
        for(IElementEventObserver observer : grassEventObservers) {
            observer.handleElementEvent(elementEvent);
        }
    }

    public static class Builder {
        private final PosDir posDir;
        private final int energy;

        private final List<IElementEventObserver> grassEventObservers = new ArrayList<>();

        public Builder(Vector2d position, int energy) {
            this.posDir = new PosDir(position);
            this.energy = energy;
        }

        public Builder addGrassEventObserver(IElementEventObserver observer) {
            grassEventObservers.add(observer);
            return this;
        }

        public Grass build() {
            return new Grass(this);
        }
    }
}
