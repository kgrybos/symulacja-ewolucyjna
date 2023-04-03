package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static agh.ics.oop.OptionsParser.parse;
import static org.junit.jupiter.api.Assertions.*;

public class OptionsParserTest {
    @Test
    void parseTest() {
        assertArrayEquals(
                new MoveDirection[]{MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.BACKWARD, MoveDirection.LEFT, MoveDirection.LEFT, MoveDirection.RIGHT, MoveDirection.RIGHT},
                parse(new String[]{"f", "forward", "b", "backward", "l", "left", "r", "right"})
        );

        assertArrayEquals(
                new MoveDirection[]{},
                parse(new String[]{})
        );

        assertArrayEquals(
                new MoveDirection[]{MoveDirection.FORWARD, MoveDirection.BACKWARD},
                parse(new String[]{"f", "x", "sdsdfsdf", "b"})
        );
    }
}
