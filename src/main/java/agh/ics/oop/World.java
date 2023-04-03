package agh.ics.oop;

import java.util.Arrays;

public class World {
    public static void main(String[] args) {
        Animal animal = new Animal();
        Arrays.stream(OptionsParser.parse(args))
                .forEach(animal::move);
        System.out.println(animal);
    }
}
