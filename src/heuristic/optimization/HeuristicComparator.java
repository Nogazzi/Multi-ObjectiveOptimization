package heuristic.optimization;

import java.util.List;
import java.util.Set;

/**
 * Created by Nogaz on 15.05.2017.
 */
public interface HeuristicComparator {
    public List<Individual> identifyDominatedSet(List<Individual> input);
    public default int compare(Individual o1, Individual o2){
        if( o1.getSize() != o2.getSize() ){

        }
        int result = 0;
        for( int i = 0 ; i < o1.getSize() ; ++i) {
            double indexResult = o1.getCharacteristic(i) - o2.getCharacteristic(i);
            if( indexResult < 0 ){
                result--;
            }else if( indexResult > 0 ){
                result++;
            }
        }
        return result;
    }

    public default boolean dominate(Individual o1, Individual o2){
        if( o1.getSize() != o2.getSize() ){

        }
        int result = 0;
        for( int i = 0 ; i < o1.getSize() ; ++i) {
            double indexResult = o1.getCharacteristic(i) - o2.getCharacteristic(i);
            if( indexResult < 0 ){
                result--;
            }else if( indexResult > 0 ){
                result++;
            }
        }
        if(result > 0 ){
            return true;
        }
        return false;
    }
}
