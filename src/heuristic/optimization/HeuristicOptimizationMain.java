package heuristic.optimization;

import heuristic.optimization.comparators.DominanceCountComparator;
import heuristic.optimization.comparators.DominanceDepthComparator;
import heuristic.optimization.comparators.DominanceRankComparator;

import java.io.*;
import java.util.*;
import java.util.jar.Pack200;
import java.util.stream.Collectors;

/**
 * Created by Nogaz on 15.05.2017.
 */
public class HeuristicOptimizationMain {
    public static void main(String[] args) throws IOException {

        List<Individual> daneTestowe = readCsv("C:\\Users\\Nogaz\\IdeaProjects\\Multi-ObjectiveOptimization\\Kung-danetestowe.csv");

        List<Individual> initialIndividuals = new ArrayList<>();

        int individualsAmount = 10;
        for(int i = 0 ; i < individualsAmount ; ++i ){
            initialIndividuals.add(new Individual(2));
        }
        List<Individual> initialIndividualsNaive = new ArrayList<>(daneTestowe);
        List<Individual> initialIndividualsKung = new ArrayList<>(daneTestowe);

        List<Individual> dominanceRankKung = generateDominanceDepthLayersByKung(initialIndividualsKung);
        List<Individual> dominanceRankNaive = generateDominanceDepthLayersByNaive(initialIndividualsNaive);

        printSortedByDominanceCount(dominanceRankNaive);
        printSortedByDominanceCount(dominanceRankKung);

        printSortedByDominanceDepth(dominanceRankNaive);
        printSortedByDominanceDepth(dominanceRankKung);

        printSortedByDominanceRank(dominanceRankNaive);
        printSortedByDominanceRank(dominanceRankKung);


    }

    public static void printSortedByDominanceRank(List<Individual> individuals){
        System.out.println("Dominance rank sort:");
        Comparator<Individual> komparator = new DominanceRankComparator();
        individuals.sort(komparator);
        for( int i = 0 ; i < individuals.size() ; ++i ) {
            System.out.println((i+1) + ".) dominance rank: " + individuals.get(i).getDominanceRank());
        }
    }

    public static void printSortedByDominanceDepth(List<Individual> individuals){
        System.out.println("Dominance depth sort:");
        Comparator<Individual> komparator = new DominanceDepthComparator();
        individuals.sort(komparator);
        for( int i = 0 ; i < individuals.size() ; ++i ) {
            System.out.println((i+1) + ".) dominance depth: " + individuals.get(i).getDominanceDepth());
        }
    }

    public static void printSortedByDominanceCount(List<Individual> individuals){
        System.out.println("Dominance count sort:");
        Comparator<Individual> komparator = new DominanceCountComparator();
        individuals.sort(komparator);
        for( int i = 0 ; i < individuals.size() ; ++i ) {
            System.out.println((i+1) + ".) dominance count: " + individuals.get(i).getDominanceCount());
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

    public static List<Individual> generateDominanceDepthLayersByNaive(List<Individual> initialIndividualsNaive){
        int counter = 0;
        List<List<Individual>> dominanceRankNaive = new ArrayList<>();
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
        return dominanceRankNaive.stream().flatMap(List::stream).collect(Collectors.toList());
    }

    public static List<Individual> generateDominanceDepthLayersByKung(List<Individual> initialIndividualsKung){
        int counter = 0;
        List<List<Individual>> dominanceRankKung = new ArrayList<>();
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
        return dominanceRankKung.stream().flatMap(List::stream).collect(Collectors.toList());
    }

    public static List<Individual> simulateByNaiveAlgorithm(List<Individual> individuals){
        individuals = individuals.stream().distinct().collect(Collectors.toList());
        HeuristicComparator naiveComparator = new NaiveComparator();
        List<Individual> naiveIdentifiedSet = naiveComparator.identifyDominatedSet(individuals);
        return naiveIdentifiedSet;
    }

    public static List<Individual> simulateByKungAlgorithm(List<Individual> individuals){
        individuals = individuals.stream().distinct().collect(Collectors.toList());
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
