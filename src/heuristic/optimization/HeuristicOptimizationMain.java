package heuristic.optimization;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nogaz on 15.05.2017.
 */
public class HeuristicOptimizationMain {
    public static void main(String[] args) throws IOException {

        List<Individual> readInd = readCsv("in.csv");
        simulate(readInd);
        List<Individual> individuals = new ArrayList<>();
        int individualsSize = 2;
        int i = 0;
        while( i < 100 ){
            individuals.add(new Individual(individualsSize));
            i++;
        }
        simulate(individuals);
        writeToFile(individuals, "base.txt");

    }

    public static void simulate(List<Individual> individuals) throws IOException {
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
        writeToFile(naiveIdentifiedSet, "naive.txt");
        writeToFile(kungIdentifiedSet, "kung.txt");
    }

    public static List<Individual> readCsv(String title) throws IOException {
        List<Individual> individuals = new ArrayList<>();
        List<Double> numbers = new ArrayList<>();
        String readData;
        String line;
        BufferedReader br = null;
        String spliter = ",";
        try{
            br = new BufferedReader(new FileReader(title));
            while( (line = br.readLine()) != null ){
                String[] x = line.split(spliter);
                numbers.addAll(Arrays.stream(x).map(Double::new).collect(Collectors.toList()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            br.close();
        }
        for (Double numb: numbers) {
            System.out.println(numb);
        }
        for( int i = 0 ; i < numbers.size() ; i+=2 ){
            individuals.add(new Individual(2, numbers.get(i), numbers.get(i+1)));
        }
        return individuals;
    }

    public static void writeToFile(List<Individual> individuals, String filename) throws IOException {
        BufferedWriter bw = null;
        bw = new BufferedWriter(new FileWriter(filename));
        for( int i = 0 ; i < individuals.size() ; ++i ){
            bw.write(individuals.get(i).printIndividual());
            bw.newLine();
        }
        bw.close();

    }
}
