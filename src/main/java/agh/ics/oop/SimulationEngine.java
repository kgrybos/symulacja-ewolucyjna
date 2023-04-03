package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class SimulationEngine implements Runnable, IAnimalEventObserver {
    private final List<Animal> animals = new ArrayList<>();
    private final int dayDelay;

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
    }

    @Override
    public void animalEvent(AnimalEvent animalEvent) {
        if(animalEvent instanceof BirthEvent event) {
            animals.add(event.animal);
        }
    }

    @Override
    public void run() {
        int i = 0;
        while(true) {
            System.out.println("Dzień: " + i);
            simulateDay();
            i += 1;
            try {
                //TODO: Przemyśleć to
                sleep(dayDelay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
