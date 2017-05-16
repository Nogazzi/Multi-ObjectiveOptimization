package heuristic.optimization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.*;

/**
 * Created by Nogaz on 15.05.2017.
 */
public class KungComparator implements HeuristicComparator {
    @Override
    public List<Individual> identifyDominatedSet(final List<Individual> input) {
        if( input.size() <= 1 ){
            return input;
        }
        List<Individual> sortedInput = new ArrayList<>(input);
        //Collections.copy(sortedInput, input);
        Collections.sort(sortedInput);

        return front(sortedInput);
    }

    public List<Individual> front(List<Individual> p){
        if( p.size() <= 1){
            return p;
        }else {
            List<Individual> result = new ArrayList<>();
            List<Individual> topPopulation;
            List<Individual> bottomPopulation;

            topPopulation = front(p.subList(0, (int) floor(p.size() / 2.0)-1));

            bottomPopulation = front(p.subList((int) floor(p.size() / 2.0) , p.size() - 1));

            result.addAll(topPopulation);

            /*for( int i = 0 ; i < bottomPopulation.size() ; ++i ){
                if( !bottomPopulation.get(i).dominatedByAnyFrom(topPopulation) ){
                    result.add(bottomPopulation.get(i));
                }
            }*/

            for (int i = 0; i < p.size(); ++i) {
                if (!p.get(i).dominatedByAnyFrom(bottomPopulation)) {
                    result.add(p.get(i));
                }
            }
            return result;
        }
    }

}
