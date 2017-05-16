package heuristic.optimization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nogaz on 15.05.2017.
 */
public class HeuristicOptimizationMain {
    public static void main(String[] args){

        List<Individual> individuals = new ArrayList<>();
        int individualsSize = 2;
        int i = 0;
        while( i < 30 ){
            individuals.add(new Individual(individualsSize));
            i++;
        }
        Collections.sort(individuals);
        individuals = individuals.stream().distinct().collect(Collectors.toList());
        HeuristicComparator naiveComparator = new NaiveComparator();
        List<Individual> naiveIdentifiedSet = naiveComparator.identifyDominatedSet(individuals);

        HeuristicComparator kungComparator = new KungComparator();
        List<Individual> kungIdentifiedSet = kungComparator.identifyDominatedSet(individuals);
        kungIdentifiedSet = kungIdentifiedSet.stream().distinct().collect(Collectors.toList());

        System.out.println("Final naive size: " +naiveIdentifiedSet.size());
        System.out.println("Final kung size: " +kungIdentifiedSet.size());
        Collections.sort(naiveIdentifiedSet);
        System.out.println("Naive ind: " + naiveIdentifiedSet);
        Collections.sort(kungIdentifiedSet);
        System.out.println("Kung ind: " + kungIdentifiedSet);

    }
}
