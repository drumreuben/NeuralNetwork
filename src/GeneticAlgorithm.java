import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/***
 * Created by Reuben on 5/9/2015.
 * Handles the creation of each new generation
 * of sweepers via the principles of natural evolution.
 */
public class GeneticAlgorithm {

    //Rate of mutation and rate of crossover
    static double mutationRate = .001;
    static double crossoverRate = .7;

    /**
    Given a population of sweepers, return a new population of the same size
    where the principles of evolution have been applied
     */
    public static List<Sweeper> makeNextGeneration(List<Sweeper> currentGen){
        //sorts the current generation based on fitness from low to high
        Collections.sort(currentGen);
        //the sweepers of the next generation
        List<Sweeper> newGen = new ArrayList<Sweeper>();
        //the two fittest sweepers are selected to be parents
        Sweeper parent1 = currentGen.get(currentGen.size()-1);
        Sweeper parent2 = currentGen.get(currentGen.size()-2);
        //genomes of each parent
        List<Double> genome1 = parent1.getGenome();
        List<Double> genome2 = parent2.getGenome();
        //the template neural net
        NeuralNet neuralNet = parent1.getNeuralNet().getCopy();
        //fills the next generation with offspring of the parents
        for(int i = 0; i < currentGen.size(); i++){
            //creates a new genome
            List<Double> newGenome = (crossover(genome1, genome2));
            //mutates the genome
            mutate(newGenome);
            //creates a new child sweeper with the correct neural net structure
            Sweeper child = new Sweeper(neuralNet.getCopy());
            //pushes the new genome to the child's neural net
            child.getNeuralNet().pushAllWeights(newGenome);
            //adds the child to the new generation
            newGen.add(child);
        }
        return newGen;
    }

    /**
    crosses over two genomes by splitting, as long as a randomly generated number is less than the crossover rate
     */
    public static List<Double> crossover(List<Double> genome1, List<Double> genome2) {
        //determines whether or not to crossover
        if(Math.random() > crossoverRate){
            if(Math.random() < .5) {
                return genome1;
            }
            return genome2;
        }
        //the result of the crossover
        List<Double> newGenome = new ArrayList<Double>();
        //determines the split point
        int splitPoint = (int)(Math.random() * genome1.size());
        //cuts the genomes at the split point and adds the first half of one two the second half of the other, determined randomly
        if(Math.random() < .5){
            List<Double> firstPart = genome1.subList(0, splitPoint);
            List<Double> secondPart = genome2.subList(splitPoint, genome2.size());
            newGenome.addAll(firstPart);
            newGenome.addAll(secondPart);
        } else {
            List<Double> firstPart = genome2.subList(0, splitPoint);
            List<Double> secondPart = genome1.subList(splitPoint, genome1.size());
            newGenome.addAll(firstPart);
            newGenome.addAll(secondPart);
        }
        return newGenome;
    }

    /**
    mutates a genome based off the mutation rate
     */
    public static List<Double> mutate(List<Double> genome){
        for(int i = 0; i < genome.size(); i++){
            if(Math.random() < mutationRate){
                genome.set(i, Math.random());
            }
        }
        return genome;
    }

}
