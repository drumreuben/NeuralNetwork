import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;

/*****
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

    //current closest target to the sweeper
    Target closestTarget;

    //tolerance for how close a sweeper has to be to a target to pick it up
    private final int TOLERANCE = 25;

    //neural net that controls the movement of the sweeper
    private NeuralNet neuralNet;

    //the input into the neural net
    List<Double> inputs;

    //what the sweeper looks like
    Image sprite;

    //the minefield the sweeper inhabits
    Minefield minefield;

    /**
     * Sweeper constructor
     * @param m
     */
    public Sweeper(Minefield m){
        neuralNet = new NeuralNet(6,2,1,5);
        x = (int) Math.random()*m.getWidth();
        y = (int) Math.random()*m.getHeight();
        this.minefield = m;
    }

    /**
    Gets fitness
     */
    public int getFitness() {
        return fitness;
    }

    /**
    Gets the sweepers genome
     */
    public List<Double> getGenome() {
        return neuralNet.getAllWeights();
    }

    /**
    Gets the sweepers Neural Net
     */
    public NeuralNet getNeuralNet() { return neuralNet; }

    /**
    Gets sweeper x coord
     */
    public int getX() { return x; }

    /**
    Gets sweeper y coord
     */
    public int getY() { return y; }

    /**
    Sets sweeper x coord
     */
    public void setX(int x) { this.x = x; }

    /**
    Sets sweeper y coord
     */
    public void setY(int y) { this.y = y; }

    /**
     * Gets target closest to sweeper
     */
    public Target getClosestTarget() { return closestTarget; }

    /**
        Comparison based off fitness. Used by the genetic algorithm to select best candidates
    */
    public int compareTo(Object s){
        if (this.fitness > ((Sweeper)(s)).getFitness()) { return 1; }
        if (this.fitness == ((Sweeper)(s)).getFitness()) { return 0; }
        return -1;
    }

    /**
     * Gets neural net output, then updates the position of the sweeper
     */
    public void updatePosition(){
        getNeuralNetOutput();
        x += Math.asin(rotation) * speed;
        y += Math.acos(rotation) * speed;
    }

    /**
    Updates sweeper input list
     */
    public void updateInputs(){
        //clears values currently stored in inputs
        inputs.clear();
        //adds the x and y positions and current rotation
        inputs.add((double)x);
        inputs.add((double)y);
        inputs.add(rotation);
        //finds the closest target, ands adds its x and y coordinates
        closestTarget = findNearestTarget();
        inputs.add((double)closestTarget.getX());
        inputs.add((double)closestTarget.getY());
    }

    /*
    Updates sweeper rotation based on neural net output
     */
    public void getNeuralNetOutput(){
        rotation = neuralNet.processNet(inputs).get(0);
    }

    /**
    A constructor for a sweeper where it is passed a neural net
     */
    public Sweeper(NeuralNet neuralNet){
        this.neuralNet = neuralNet;
    }

    /**
    Draw method for the sweeper. Used by the custom graphics panel for animation
     */
    public void draw(Graphics g){
        // System.out.println("drawing");
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        at.rotate(Math.toRadians(rotation), sprite.getWidth(null) / 2, sprite.getHeight(null) / 2);
        g2d.drawImage(sprite, at, null);
    }

    /**
    Gets the Target closest to the sweeper
     */
    public Target findNearestTarget(){
        //index of current best candidate
        int currentClosestIndex = 0;
        //current best distance starts at double max value
        double currentBestDistance = Double.MAX_VALUE;
        //stores the distance to the target it is currently checking
        double distanceToTarget;
        //checks each target on the minefield
        for(int i = 0; i < minefield.getTargets().size(); i++){
            //current mine
            Target t = minefield.getTargets().get(i);
            //calculates distance to target based off pythagorean theorem
            distanceToTarget = Math.sqrt(Math.pow(this.getX() - t.getX(), 2) + Math.pow(this.getY() - t.getY(), 2));
            //compares to best distance and updates best distance and best index if less than
            if(distanceToTarget < currentBestDistance){
                currentBestDistance = distanceToTarget;
                currentClosestIndex = i;
            }
        }
        return minefield.getTargets().get(currentClosestIndex);
    }

    /**
    Returns true if the sweeper has passed within a tolerance of a target, and adds 1 to fitness of the sweeper
     */
    public boolean foundTarget() {
        //gets the nearest target
        Target nearestTarget = findNearestTarget();
        //determines if the sweeper is over the target
        if(Math.abs(this.getX() - nearestTarget.getX()) < TOLERANCE && Math.abs(this.getY() - nearestTarget.getY()) < TOLERANCE){
            return true;
        }
        return false;
    }



}
