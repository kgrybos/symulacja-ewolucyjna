package agh.ics.oop;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class World {
    public static void main(String[] args) {
        System.out.println("Start");
        Stream<Direction> moves = interpret(Arrays.stream(args));
        run(moves);
        System.out.println("Stop");
    }

    static void run(Stream<Direction> moves) {
        moves
                .map(move -> switch (move) {
                    case FORWARD -> "Zwierzak idzie do przodu";
                    case BACKWARD -> "Zwierzak idzie do tyłu";
                    case LEFT -> "Zwierzak idzie w lewo";
                    case RIGHT -> "Zwierzak idzie w prawo";
                })
                .forEach(System.out::println);
    }

    static Stream<Direction> interpret(Stream<String> args) {
        return args
                .map(arg -> switch (arg) {
                   case "f" -> Direction.FORWARD;
                   case "b" -> Direction.BACKWARD;
                   case "l" -> Direction.LEFT;
                   case "r" -> Direction.RIGHT;
                   default -> null;
                })
                .filter(Objects::nonNull);
    }
}
