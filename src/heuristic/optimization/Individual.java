package heuristic.optimization;

import java.util.*;

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
            double newDouble = new Random().nextDouble();

            characteristics[i] = newDouble;
        }
    }
    public Individual(final double[] characteristics, final int size){
        this.size = size;
        this.characteristics = characteristics;
    }

    public Individual(final int size, final double x1, final double x2){
        this.size = size;
        this.characteristics = new double[size];
        this.characteristics[0] = x1;
        this.characteristics[1] = x2;
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
        boolean dominates = false;
        for( int i = 0 ; i < o1.getSize() ; ++i) {
            if( this.getCharacteristic(i) < o1.getCharacteristic(i)){
                return false;
            }else if( this.getCharacteristic(i) > o1.getCharacteristic(i) ){
                dominates = true;
            }
        }
        return dominates;
    }


    public boolean dominatedByAnyFrom(List<Individual> population){
        for (int i = 0 ;i < population.size() ; ++i){
            if( population.get(i).dominates(this) ){ return true; }
        }
        return false;
    }

    @Override
    public int compareTo(Individual o) {

        /*
        if( o.getCharacteristic(0) > getCharacteristic(0) ){
            return 1;
        }else if( o.getCharacteristic(0) < getCharacteristic(0)){
            return -1;
        }else{
            if( o.getCharacteristic(1) > getCharacteristic(1) ){
                return 1;
            }else if( o.getCharacteristic(1) < getCharacteristic(1)){
                return -1;
            }else{
                return 0;
            }
        }*/
        int dif = Double.compare(o.getCharacteristic(0), getCharacteristic(0));
        if( dif == 0 ){
            return Double.compare(o.getCharacteristic(1), getCharacteristic(1));
        }
        return dif;

        /*double difference = o.getCharacteristic(0) - getCharacteristic(0);
        if( difference == 0 ){
            return (int) (o.getCharacteristic(1) - getCharacteristic(1))*10;
        }
        return (int) difference*10;*/
    }

    @Override
    public String toString() {
        String desc = "[";
        for (int i = 0 ; i < size ; ++i ){
            desc += this.characteristics[i];
            if( i != size-1 ){
                desc += ";";
            }
        }
        desc += "]";
        return desc;
    }

    @Override
    public boolean equals(Object obj) {
        Individual o1 = (Individual)obj;
        if( this.getSize() != o1.getSize() ){
            return false;
        }
        for( int i = 0 ; i < getSize() ; ++i ){
            if( getCharacteristic(i) != o1.getCharacteristic(i)){
                return false;
            }
        }
        return true;
    }

    public String printIndividual(){
        String result = "";
        for(int i = 0 ; i < size ; ++i ){
            result += characteristics[i] + "\t";
        }
        return result;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
