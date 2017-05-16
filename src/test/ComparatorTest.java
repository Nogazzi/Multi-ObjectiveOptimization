package test;

import heuristic.optimization.HeuristicComparator;
import heuristic.optimization.Individual;
import heuristic.optimization.KungComparator;
import heuristic.optimization.NaiveComparator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nogaz on 16.05.2017.
 */
public class ComparatorTest {

    @Test
    public void ComparatorTest(){

        List<Individual> input = new ArrayList<>();
        input.add(new Individual(2, 2.1, 2.2));
        input.add(new Individual(2, 0.1, 0.2));
        input.add(new Individual(2, 0.3, 0.5));
        input.add(new Individual(2, 0.5, 0.2));

        HeuristicComparator kung = new KungComparator();
        HeuristicComparator naive = new NaiveComparator();

        List<Individual> kungResult = kung.identifyDominatedSet(input);
        kungResult = kungResult.stream().distinct().collect(Collectors.toList());
        List<Individual> naiveResult = naive.identifyDominatedSet(input);

        System.out.println("Size kung: " + kungResult.size());
        System.out.println(kungResult.toString());
        System.out.println("Size naive: " + naiveResult.size());
        System.out.println(naiveResult.toString());
    }
}
