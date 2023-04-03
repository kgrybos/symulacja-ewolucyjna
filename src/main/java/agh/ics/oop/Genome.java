package agh.ics.oop;

import java.util.*;

public class Genome {
    private final List<MoveDirection> genes = new ArrayList<>();
    private int i;

    public Genome(Random random, int genomeSize) {
        for(int i = 0; i < genomeSize; i++) {
            genes.add(MoveDirection.random(random));
        }

        i = random.nextInt(genomeSize);
    }

    public Genome(Random random, Genome stronger, Genome weaker, float ratio, Side strongerSide, Config config) {
        int splitPoint = Math.round(stronger.genes.size()*ratio);

        List<MoveDirection> strongerGenes = switch (strongerSide) {
            case LEFT -> stronger.genes.subList(0, splitPoint);
            case RIGHT -> stronger.genes.subList(splitPoint, stronger.genes.size());
        };

        List<MoveDirection> weakerGenes = switch (strongerSide) {
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

        if(config.maxMutations()-config.minMutations() > 0) {
            int mutatedGenes = random.nextInt(config.maxMutations() - config.minMutations()) + config.minMutations();
            List<Integer> mutations = randomUniqueNumbers(random, genes.size(), mutatedGenes);
            for (Integer mutation : mutations) {
                genes.set(mutation, MoveDirection.random(random));
            }
        }

        i = random.nextInt(stronger.genes.size());
    }

    private List<Integer> randomUniqueNumbers(Random random, int bound, int number) {
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0; i < bound; i++) {
            list.add(i);
        }
        Collections.shuffle(list, random);
        return list.subList(0, number);
    }

    public MoveDirection[] getGenes() {
        return genes.toArray(new MoveDirection[]{});
    }

    public int getActiveGene() {
        return i;
    }

    public MoveDirection nextGene() {
        MoveDirection next = genes.get(i);
        i = (i + 1) % genes.size();
        return next;
    }
}
