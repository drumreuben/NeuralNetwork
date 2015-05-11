import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*****
 * Created by Reuben on 5/9/2015.
 * Virtual creatures whose evolutionary fitness is determined by how many targets they collect.
 * The motion of the sweeper every tick is determined by a neural network, being fed a variety of inputs.
 */
public class Sweeper implements Comparable{

    //position of the sweeper
    private double x;
    private double y;

    //rotation of the sweeper measured in degrees relative to the positive x-axis
    private double rotation = 180 - (Math.random()*360);

    //current speed of the sweeper
    private double speed = 1;

    //number of targets the sweeper has collected in its lifetime
    private int fitness;

    //current closest target to the sweeper
    Target closestTarget;

    //tolerance for how close a sweeper has to be to a target to pick it up
    private final int TOLERANCE = 25;

    //neural net that controls the movement of the sweeper
    private NeuralNet neuralNet;

    //the input into the neural net
    List<Double> inputs = new ArrayList<Double>();

    //what the sweeper looks like
    Image sprite;

    //the minefield the sweeper inhabits
    Minefield minefield;

    /**
     * Sweeper constructor
     * @param m
     */
    public Sweeper(Minefield m, NeuralNet n){
        neuralNet = n;
        x = (int) (Math.random()*m.getWidth());
        y = (int) (Math.random()*m.getHeight());
        this.minefield = m;
        //creates the inputs array and updates the values inside it
        inputs = new ArrayList<Double>();
        updateInputs();
        //gets the sweeper sprite
        try {
            sprite = ImageIO.read(new File("sweeperSprite.png"));
        } catch (Exception e){
            System.out.println("error reading minesweeper sprite file");
        }
    }

    public Sweeper(Minefield m){
        neuralNet = new NeuralNet(3,2,3,5);
        x = (int) (Math.random()*m.getWidth());
        y = (int) (Math.random()*m.getHeight());
        this.minefield = m;
        //creates the inputs array and updates the values inside it
        inputs = new ArrayList<Double>();
        updateInputs();
        //gets the sweeper sprite
        try {
            sprite = ImageIO.read(new File("sweeperSprite.png"));
        } catch (Exception e){
            System.out.println("error reading minesweeper sprite file");
        }
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
    public double getX() { return x; }

    /**
    Gets sweeper y coord
     */
    public double getY() { return y; }

    /**
    Sets sweeper x coord
     */
    public void setX(double x) { this.x = x; }

    /**
    Sets sweeper y coord
     */
    public void setY(double y) { this.y = y; }

    /**
     * gets sweeper rotation
     */
    public double getRotation() { return rotation; }

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
        double xComp = Math.cos(Math.toRadians(rotation)) * speed;
        double yComp = Math.sin(Math.toRadians(rotation)) * speed;
        //System.out.println("x: " + xComp + " y: " + yComp + " r: " + rotation);
        //System.out.println(Math.asin(Math.toRadians(40)));
        x += xComp;
        y += yComp;
        //x += 0.372;
        //y += -1.003;
    }

    /**
    Updates sweeper input list
     */
    public void updateInputs(){
        //clears values currently stored in inputs
        inputs.clear();
        //adds the x and y positions and current rotation
        //inputs.add(x);
        //inputs.add(y);
        inputs.add(rotation);
        //finds the closest target, ands adds its x and y coordinates
        closestTarget = findNearestTarget();
        //inputs.add((double)closestTarget.getX());
        //inputs.add((double)closestTarget.getY());
        inputs.add((double)closestTarget.getX()-x);
        inputs.add((double)closestTarget.getY()-y);
    }

    /*
    Updates sweeper rotation based on neural net output
     */
    public void getNeuralNetOutput(){
        updateInputs();
       // for(double d : neuralNet.getAllWeights()){
        //    System.out.println(d);
        //}
        double changeRotation = (neuralNet.processNet(inputs).get(0)-.5)*10;
        //System.out.println(changeRotation);
        rotation += changeRotation;
       rotation = rotation % 360;
       speed = 5* (neuralNet.getOutputs().get(1)-.5);
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

    public int getClosestTargetIndex(){
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
        return currentClosestIndex;
    }

    /**
    Returns true if the sweeper has passed within a tolerance of a target, and adds 1 to fitness of the sweeper
     */
    public boolean foundTarget() {
        //gets the nearest target
        Target nearestTarget = findNearestTarget();
        //determines if the sweeper is over the target
        if(Math.abs(this.getX() - nearestTarget.getX()) < TOLERANCE && Math.abs(this.getY() - nearestTarget.getY()) < TOLERANCE){
            //increments fitness
            fitness++;
            return true;
        }
        return false;
    }



}
