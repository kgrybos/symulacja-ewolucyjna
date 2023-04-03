package agh.ics.oop;

import com.google.common.collect.Iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Genome {
    private final List<Byte> genes = new ArrayList<>();
    private final Iterator<Byte> genesIterator = Iterators.cycle(genes);

    public Genome(Random random, int genomeSize) {
        for(int i = 0; i < genomeSize; i++) {
            genes.add((byte) random.nextInt(8));
        }

        for(int i = 0; i < random.nextInt(genes.size()); i++) {
            genesIterator.next();
        }
    }
    public Genome(Random random, Genome parent1, Genome parent2) {
        genes.addAll(parent1.genes);
        genes.addAll(parent2.genes);

        for(int i = 0; i < random.nextInt(genes.size()); i++) {
            genesIterator.next();
        }
    }

    public Byte nextGene() {
        return genesIterator.next();
    }
}
