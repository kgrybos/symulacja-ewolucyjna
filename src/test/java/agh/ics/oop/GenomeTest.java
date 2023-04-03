package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenomeTest {
    @Test
    void randomGenesTest() {
        Random random = new Random(0);
        Genome genome = new Genome(random, 3);
        assertEquals((byte) 1, genome.nextGene());
        assertEquals((byte) 5, genome.nextGene());
        assertEquals((byte) 6, genome.nextGene());
        assertEquals((byte) 1, genome.nextGene());
        assertEquals((byte) 5, genome.nextGene());
        assertEquals((byte) 6, genome.nextGene());
        assertEquals((byte) 1, genome.nextGene());
    }

    @Test
    void reproduceTest() {
        Random random = new Random(0);

        Genome parent1 = new Genome(random, 3);
        assertEquals((byte) 1, parent1.nextGene());
        assertEquals((byte) 5, parent1.nextGene());
        assertEquals((byte) 6, parent1.nextGene());

        Genome parent2 = new Genome(random, 3);
        assertEquals((byte) 4, parent2.nextGene());
        assertEquals((byte) 4, parent2.nextGene());
        assertEquals((byte) 0, parent2.nextGene());

        Genome child = new Genome(random, parent1, parent2);

        assertEquals((byte) 4, child.nextGene());
        assertEquals((byte) 0, child.nextGene());
        assertEquals((byte) 4, child.nextGene());
        assertEquals((byte) 5, child.nextGene());
        assertEquals((byte) 6, child.nextGene());
        assertEquals((byte) 1, child.nextGene());
        assertEquals((byte) 4, child.nextGene());
        assertEquals((byte) 0, child.nextGene());
        assertEquals((byte) 4, child.nextGene());
        assertEquals((byte) 5, child.nextGene());
        assertEquals((byte) 6, child.nextGene());
        assertEquals((byte) 1, child.nextGene());
    }
}
