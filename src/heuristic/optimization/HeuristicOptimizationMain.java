package heuristic.optimization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        HeuristicComparator naiveComparator = new NaiveComparator();
        List<Individual> naiveIdentifiedSet = naiveComparator.identifyDominatedSet(individuals);

        HeuristicComparator kungComparator = new KungComparator();
        List<Individual> kungIdentifiedSet = kungComparator.identifyDominatedSet(individuals);

        System.out.println("Final naive size: " +naiveIdentifiedSet.size());
        System.out.println("Final kung size: " +kungIdentifiedSet.size());
        Collections.sort(individuals);
        System.out.println(individuals);

    }
}
