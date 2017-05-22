package heuristic.optimization;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.jar.Pack200;
import java.util.stream.Collectors;

/**
 * Created by Nogaz on 15.05.2017.
 */
public class HeuristicOptimizationMain {
    public static void main(String[] args) throws IOException {

        /*List<Individual> readInd = readCsv("C:\\Users\\Nogaz\\IdeaProjects\\Multi-ObjectiveOptimization\\Kung-danetestowe.csv");
        simulate(readInd);
        writeToFile(readInd, "base.txt");*/
        List<Individual> initialIndividuals = new ArrayList<>();
        List<List<Individual>> dominanceRankNaive = new ArrayList<>();
        List<List<Individual>> dominanceRankKung = new ArrayList<>();
        int individualsAmount = 10;
        for(int i = 0 ; i < individualsAmount ; ++i ){
            initialIndividuals.add(new Individual(2));
        }
        List<Individual> initialIndividualsNaive = new ArrayList<>(initialIndividuals);
        List<Individual> initialIndividualsKung = new ArrayList<>(initialIndividuals);

        int counter = 0;
        while( initialIndividualsKung.size() > 0 ) {
            dominanceRankKung.add(new ArrayList<Individual>(simulateByKungAlgorithm(initialIndividualsKung)));
            for( int i = 0 ; i < dominanceRankKung.get(counter).size() ; ++i ){
                if( initialIndividualsKung.contains(dominanceRankKung.get(counter).get(i))){
                    dominanceRankKung.get(counter).get(i).setDominanceDepth(counter+1);
                    initialIndividualsKung.remove(dominanceRankKung.get(counter).get(i));
                }
            }
            counter++;
        }

        counter = 0;
        while( initialIndividualsNaive.size() > 0 ) {
            dominanceRankNaive.add(new ArrayList<Individual>(simulateByNaiveAlgorithm(initialIndividualsNaive)));
            for( int i = 0 ; i < dominanceRankNaive.get(counter).size() ; ++i ){
                if( initialIndividualsNaive.contains(dominanceRankNaive.get(counter).get(i))){
                    dominanceRankNaive.get(counter).get(i).setDominanceDepth(counter+1);
                    initialIndividualsNaive.remove(dominanceRankNaive.get(counter).get(i));
                }
            }
            counter++;
        }

        System.out.println("Kung:");
        for( int i = 0 ; i < dominanceRankKung.size() ; ++i ){
            System.out.println((i+1) + ".) " + dominanceRankKung.get(i).size() + " elementów");
            System.out.println("Dominance depth: " + dominanceRankKung.get(i).get(0).getDominanceDepth());
        }

        System.out.println("Naive:");
        for( int i = 0 ; i < dominanceRankNaive.size() ; ++i ){
            System.out.println((i+1) + ".) " + dominanceRankNaive.get(i).size() + " elementów");
            System.out.println("Dominance depth: " + dominanceRankNaive.get(i).get(0).getDominanceDepth());
        }

    }

    public static void simulate(List<Individual> individuals) throws IOException {
        Collections.sort(individuals);
        individuals = individuals.stream().distinct().collect(Collectors.toList());
        HeuristicComparator naiveComparator = new NaiveComparator();
        List<Individual> naiveIdentifiedSet = naiveComparator.identifyDominatedSet(individuals);

        HeuristicComparator kungComparator = new KungComparator();
        List<Individual> kungIdentifiedSet = kungComparator.identifyDominatedSet(individuals);
        kungIdentifiedSet = kungIdentifiedSet.stream().distinct().collect(Collectors.toList());

        System.out.println("Final naive size: " + naiveIdentifiedSet.size());
        System.out.println("Final kung size: " + kungIdentifiedSet.size());
        Collections.sort(naiveIdentifiedSet);
        System.out.println("Naive ind: " + naiveIdentifiedSet);
        Collections.sort(kungIdentifiedSet);
        System.out.println("Kung ind: " + kungIdentifiedSet);
        writeToFile(naiveIdentifiedSet, "naive.txt");
        writeToFile(kungIdentifiedSet, "kung.txt");
    }

    public static List<Individual> simulateByNaiveAlgorithm(List<Individual> individuals){
        individuals = individuals.stream().distinct().collect(Collectors.toList());
        HeuristicComparator naiveComparator = new NaiveComparator();
        List<Individual> naiveIdentifiedSet = naiveComparator.identifyDominatedSet(individuals);
        return naiveIdentifiedSet;
    }

    public static List<Individual> simulateByKungAlgorithm(List<Individual> individuals){
        Collections.sort(individuals);
        HeuristicComparator kungComparator = new KungComparator();
        List<Individual> kungIdentifiedSet = kungComparator.identifyDominatedSet(individuals);
        kungIdentifiedSet = kungIdentifiedSet.stream().distinct().collect(Collectors.toList());
        return kungIdentifiedSet;
    }

    public static List<Individual> readCsv(String title) throws IOException {
        List<Individual> individuals = new ArrayList<>();
        List<Double> numbers = new ArrayList<>();
        String readData;
        String line;
        BufferedReader br = null;
        String spliter = ";";
        try {
            br = new BufferedReader(new FileReader(title));
            while ((line = br.readLine()) != null) {
                String[] x = line.split(spliter);
                numbers.addAll(Arrays.stream(x).map(Double::new).collect(Collectors.toList()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        for (Double numb : numbers) {
            System.out.println(numb);
        }
        for (int i = 0; i < numbers.size(); i += 2) {
            individuals.add(new Individual(2, numbers.get(i), numbers.get(i + 1)));
        }
        return individuals;
    }

    public static void writeToFile(List<Individual> individuals, String filename) throws IOException {
        BufferedWriter bw = null;
        bw = new BufferedWriter(new FileWriter(filename));
        for (int i = 0; i < individuals.size(); ++i) {
            bw.write(individuals.get(i).printIndividual());
            bw.newLine();
        }
        bw.close();
    }
}
