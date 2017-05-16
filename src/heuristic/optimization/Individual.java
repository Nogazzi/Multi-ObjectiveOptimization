package heuristic.optimization;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Nogaz on 15.05.2017.
 */
public class Individual implements Comparable<Individual> {

    private final int size;
    private final double[] characteristics;

    public Individual(final int size){
        this.size = size;
        this.characteristics = new double[size];
        for( int i = 0 ; i < size ; ++i ){
            characteristics[i] = new Random().nextDouble();
        }
    }
    public Individual(final double[] characteristics, final int size){
        this.size = size;
        this.characteristics = characteristics;
    }

    public int getSize(){
        return this.size;
    }
    public double getCharacteristic(final int index){
        return this.characteristics[index];
    }

    public boolean dominates(Individual o1){
        if( o1.getSize() != getSize() ){

        }
        int result = 0;
        for( int i = 0 ; i < o1.getSize() ; ++i) {
            double indexResult = getCharacteristic(i) - o1.getCharacteristic(i);
            if( indexResult < 0 ){
                result--;
            }else if( indexResult > 0 ){
                result++;
            }
        }
        if(result > 1 ){
            return true;
        }
        return false;
    }

    public boolean dominatedByAnyFrom(List<Individual> population){
        for (int i = 0 ;i < population.size() ; ++i){
            if( population.get(i).dominates(this) ){ return true; }
        }
        return false;
    }


    @Override
    public int compareTo(Individual o) {
        double difference = o.getCharacteristic(0) - getCharacteristic(0);
        if( difference < 0 ){
            return -1;
        }else if( difference > 0){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(getCharacteristic(0));
    }
}
