package heuristic.optimization;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Nogaz on 15.05.2017.
 */
public class NaiveComparator implements HeuristicComparator{


    @Override
    public List<Individual> identifyDominatedSet(List<Individual> input) {
        boolean dominated = false;
        List<Individual> result = new ArrayList<>();

        for (int i = 0; i < input.size(); ++i) {
            dominated = false;
            for (int j = 0; j < input.size(); ++j) {
                if (j == i) continue;
                if (dominate(input.get(j), input.get(i)) == true) {
                    dominated = true;
                    break;
                }
                j++;
            }
            if( dominated == false ){
                result.add(input.get(i));
            }
        }
        return result;
    }
}
