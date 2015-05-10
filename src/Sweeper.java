/**
 * Created by Reuben on 5/9/2015.
 * Virtual creatures whose evolutionary fitness is determined by how many targets they collect.
 * The motion of the sweeper every tick is determined by a neural network, being fed a variety of inputs.
 */
public class Sweeper implements Comparable{

    //position of the sweeper
    private int x;
    private int y;

    //rotation of the sweeper measured in degrees relative to the positive x-axis
    private double rotation;

    //current speed of the sweeper
    private double speed;

    //number of targets the sweeper has collected in its lifetime
    private int fitness;

    //x and y coords of the nearest target to the sweeper
    private int targetX;
    private int targetY;

    /*
    Gets fitness
     */
    public int getFitness() {
        return fitness;
    }

    /*
        Comparison based off fitness. Used by the genetic algorithm to select best candidates
    */
    public int compareTo(Object s){
        if (this.fitness > ((Sweeper)(s)).getFitness()) { return 1; }
        if (this.fitness == ((Sweeper)(s)).getFitness()) { return 0; }
        return -1;
    }



}
