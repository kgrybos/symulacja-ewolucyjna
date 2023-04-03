package agh.ics.oop;

import agh.ics.oop.observers.BirthEvent;
import agh.ics.oop.observers.ElementEvent;
import agh.ics.oop.observers.IElementEventObserver;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class SimulationEngine implements Runnable, IElementEventObserver {
    private final List<Animal> animals = new ArrayList<>();
    private final int dayDelay;
    private boolean paused = true;

    public SimulationEngine(int dayDelay) {
        this.dayDelay = dayDelay;
    }

    public void simulateDay() {
        for(Animal animal : animals) {
            animal.wakeUp();
        }
        animals.removeIf((animal -> animal.getEnergy() <= 0));

        for(Animal animal : animals) {
            animal.move();
        }

        for(Animal animal : animals) {
            animal.eat();
        }

        List<Animal> animalsCopy = new ArrayList<>(animals);
        for(Animal animal : animalsCopy) {
            animal.reproduce();
        }
    }

    @Override
    public void handleElementEvent(ElementEvent elementEvent) {
        if(elementEvent instanceof BirthEvent event) {
            if(event.element instanceof Animal animal) {
                animals.add(animal);
            }
        }
    }

    public Animal[] getAnimals() {
        return animals.toArray(new Animal[]{});
    }

    public boolean isPaused() {
        return this.paused;
    }

    public void pause() {
        paused = true;
    }

    public synchronized void resume() {
        paused = false;
        notify();
    }

    @Override
    public synchronized void run() {
        try {
            wait();
            int dayNumber = 0;
            while(true) {
                System.out.println("Dzień: " + dayNumber);
                simulateDay();
                if (paused) {
                    wait();
                } else {
                    sleep(dayDelay);
                }

                dayNumber += 1;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
