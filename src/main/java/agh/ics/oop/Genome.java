package agh.ics.oop;

import agh.ics.oop.AnimalBehaviours.AnimalBehaviour;
import agh.ics.oop.AnimalBehaviours.CrazyBehaviour;
import agh.ics.oop.AnimalBehaviours.PredestinicBehaviour;
import agh.ics.oop.Mutators.Mutator;
import agh.ics.oop.Mutators.RandomMutator;
import agh.ics.oop.Mutators.SlightMutator;

import java.util.*;

public class Genome {
    private final List<MoveDirection> genes = new ArrayList<>();
    private int i;
    private final AnimalBehaviour animalBehaviour;
    private final Random random;

    public Genome(Random random, Config config, int genomeSize) {
        this.animalBehaviour = switch (config.animalBehaviour()) {
            case PREDESTINATION -> new PredestinicBehaviour();
            case CRAZY -> new CrazyBehaviour();
        };
        this.random = random;

        for(int i = 0; i < genomeSize; i++) {
            genes.add(MoveDirection.random(random));
        }

        i = random.nextInt(genomeSize);
    }

    public Genome(Random random, Genome stronger, Genome weaker, float ratio, Side strongerSide, Config config) {
        this.animalBehaviour = switch (config.animalBehaviour()) {
            case PREDESTINATION -> new PredestinicBehaviour();
            case CRAZY -> new CrazyBehaviour();
        };
        this.random = random;

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

        Mutator mutator = switch (config.mutator()) {
            case RANDOM -> new RandomMutator();
            case SLIGHT -> new SlightMutator();
        };

        if(config.maxMutations()-config.minMutations() > 0) {
            int mutatedGenes = random.nextInt(config.maxMutations() - config.minMutations()) + config.minMutations();
            List<Integer> mutations = randomUniqueNumbers(random, genes.size(), mutatedGenes);
            for (Integer mutation : mutations) {
                genes.set(mutation, mutator.mutate(random, genes.get(mutation)));
            }
        }

        i = random.nextInt(stronger.genes.size());
    }

    private static List<Integer> randomUniqueNumbers(Random random, int bound, int number) {
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
        i = animalBehaviour.getNext(random, i, genes.size());
        return next;
    }
}
