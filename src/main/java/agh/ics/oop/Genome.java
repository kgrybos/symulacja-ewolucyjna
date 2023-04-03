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
    public Genome(Random random, Genome stronger, Genome weaker, float ratio, Side strongerSide) {
        int splitPoint = Math.round(stronger.genes.size()*ratio);

        List<Byte> strongerGenes = switch (strongerSide) {
            case LEFT -> stronger.genes.subList(0, splitPoint);
            case RIGHT -> stronger.genes.subList(splitPoint, stronger.genes.size());
        };

        List<Byte> weakerGenes = switch (strongerSide) {
            case LEFT -> weaker.genes.subList(splitPoint, stronger.genes.size());
            case RIGHT -> weaker.genes.subList(0, splitPoint);
        };

        switch (strongerSide) {
            case LEFT -> {
                genes.addAll(strongerGenes);
                genes.addAll(weakerGenes);
            }
            case RIGHT -> {
                genes.addAll(weakerGenes);
                genes.addAll(strongerGenes);
            }
        }

        for(int i = 0; i < random.nextInt(genes.size()); i++) {
            genesIterator.next();
        }
    }

    public Byte nextGene() {
        return genesIterator.next();
    }
}
