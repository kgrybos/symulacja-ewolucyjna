package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenomeTest {
    @Test
    void randomGenesTest() {
        Random random = new Random(0);
        Genome genome = new Genome(random, Config.getFromFile("normal"), 3);
        assertEquals(MoveDirection.FORWARD_RIGHT, genome.nextGene());
        assertEquals(MoveDirection.BACKWARD_LEFT, genome.nextGene());
        assertEquals(MoveDirection.LEFT, genome.nextGene());
        assertEquals(MoveDirection.FORWARD_RIGHT, genome.nextGene());
        assertEquals(MoveDirection.BACKWARD_LEFT, genome.nextGene());
        assertEquals(MoveDirection.LEFT, genome.nextGene());
        assertEquals(MoveDirection.FORWARD_RIGHT, genome.nextGene());
    }

    @Test
    void reproduceTest() {
        Random random = new Random(0);

        Genome parent1 = new Genome(random, Config.getFromFile("normal"), 3);
        assertEquals(MoveDirection.FORWARD_RIGHT, parent1.nextGene());
        assertEquals(MoveDirection.BACKWARD_LEFT, parent1.nextGene());
        assertEquals(MoveDirection.LEFT, parent1.nextGene());

        Genome parent2 = new Genome(random, Config.getFromFile("normal"), 3);
        assertEquals(MoveDirection.BACKWARD_LEFT, parent2.nextGene());
        assertEquals(MoveDirection.RIGHT, parent2.nextGene());
        assertEquals(MoveDirection.BACKWARD, parent2.nextGene());

        Genome child = new Genome(random, parent1, parent2, 0.7F, Side.LEFT, Config.getFromFile("no_mutations"));

        assertEquals(MoveDirection.BACKWARD_LEFT, child.nextGene());
        assertEquals(MoveDirection.LEFT, child.nextGene());
        assertEquals(MoveDirection.BACKWARD, child.nextGene());
        assertEquals(MoveDirection.BACKWARD_LEFT, child.nextGene());
        assertEquals(MoveDirection.LEFT, child.nextGene());
        assertEquals(MoveDirection.BACKWARD, child.nextGene());
        assertEquals(MoveDirection.BACKWARD_LEFT, child.nextGene());
    }
}
