package heuristic.optimization;

import java.util.List;

/**
 * Created by Nogaz on 15.05.2017.
 */
public interface HeuristicComparator {
    public List<Individual> identifyDominatedSet(List<Individual> input);

}
