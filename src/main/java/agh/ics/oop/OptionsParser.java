package agh.ics.oop;

import java.util.Arrays;

public class OptionsParser {
    public static MoveDirection[] parse(String[] options) {
        return Arrays.stream(options)
                .map(option -> switch (option) {
                    case "f", "forward" -> MoveDirection.FORWARD;
                    case "b", "backward" -> MoveDirection.BACKWARD;
                    case "r", "right" -> MoveDirection.RIGHT;
                    case "l", "left" -> MoveDirection.LEFT;
                    default -> throw new IllegalArgumentException(option + " is not legal move specification");
                })
                .toArray(MoveDirection[]::new);
    }
}
