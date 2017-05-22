package test;

import heuristic.optimization.HeuristicComparator;
import heuristic.optimization.Individual;
import heuristic.optimization.KungComparator;
import heuristic.optimization.NaiveComparator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nogaz on 16.05.2017.
 */
public class ComparatorTest {

    @Test
    public void ComparatorTest() {

        List<Individual> input = new ArrayList<>();
        input.add(new Individual(2, 2.1, 2.2));
        input.add(new Individual(2, 2.2, 2.1));
        input.add(new Individual(2, 2.4, 2.0));
        input.add(new Individual(2, 0.1, 0.7));
        input.add(new Individual(2, 0.6, 0.5));
        input.add(new Individual(2, 0.1, 0.2));
        input.add(new Individual(2, 3.1, 2.0));
        input.add(new Individual(2, 2.1, 2.2));
        input.add(new Individual(2, 2.1, 2.3));

        Collections.sort(input);
        input = input.stream().distinct().collect(Collectors.toList());

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

    @Test
    public void dominatedByAnyTest() {
        List<Individual> input = new ArrayList<>();
        input.add(new Individual(2, 2.1, 2.2));
        input.add(new Individual(2, 2.2, 2.1));
        input.add(new Individual(2, 2.4, 2.0));
        input.add(new Individual(2, 0.1, 0.7));
        input.add(new Individual(2, 0.6, 0.5));
        input.add(new Individual(2, 0.1, 0.2));
        Individual ind = new Individual(2, 3.1, 2.0);
        Individual ind2 = new Individual(2, 2.0, 2.0);

        assertEquals(false, ind.dominatedByAnyFrom(input));
        assertEquals(true, ind2.dominatedByAnyFrom(input));
    }

    @Test
    public void dominationTest() {
        Individual ind = new Individual(2, 3.1, 2.0);
        Individual ind2 = new Individual(2, 2.0, 2.0);
        Individual ind3 = new Individual(2, 1.0, 2.3);
        Individual ind4 = new Individual(2, 3.2, 2.4);
        Individual ind5 = new Individual(2, 3.1, 2.0);
        assertEquals(true, ind.dominates(ind2));
        assertEquals(false, ind.dominates(ind3));
        assertEquals(false, ind2.dominates(ind));
        assertEquals(false, ind2.dominates(ind3));
        assertEquals(false, ind3.dominates(ind));
        assertEquals(false, ind3.dominates(ind2));
        assertEquals(true, ind4.dominates(ind));
        assertEquals(true, ind4.dominates(ind2));
        assertEquals(true, ind4.dominates(ind3));
        assertEquals(false, ind.dominates(ind5));
    }
}
