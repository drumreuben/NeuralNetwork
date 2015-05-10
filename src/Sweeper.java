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

    //x and y coords of the nearest target to the sweeper
    private int targetX;
    private int targetY;

    //neural net that controls the movement of the sweeper
    private NeuralNet neuralNet;

    //what the sweeper looks like
    Image sprite;

    /**
     * Sweeper constructor
     * @param m
     */
    public Sweeper(Minefield m){
        neuralNet = new NeuralNet(6,2,1,5);
        x = (int) Math.random()*m.getWidth();
        y = (int) Math.random()*m.getHeight();
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

    /*****
    Sets sweeper y coord
     */
    public void setY(int y) { this.y = y; }

    /*****
        Comparison based off fitness. Used by the genetic algorithm to select best candidates
    */
    public int compareTo(Object s){
        if (this.fitness > ((Sweeper)(s)).getFitness()) { return 1; }
        if (this.fitness == ((Sweeper)(s)).getFitness()) { return 0; }
        return -1;
    }

    /*****
    A constructor for a sweeper where it is passed a neural net
     */
    public Sweeper(NeuralNet neuralNet){
        this.neuralNet = neuralNet;
    }

    /****
    Draw method for the sweeper. Used by the custom graphics panel for animation
     */
    public void draw(Graphics g){
        // System.out.println("drawing");
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        at.rotate(Math.toRadians(rotation), sprite.getWidth(null) / 2, sprite.getHeight(null) / 2);
        g2d.drawImage(sprite, at, null);
    }



}
