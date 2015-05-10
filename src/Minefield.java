import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/***
 * Created by Reuben on 5/9/2015.
 * The field in which the sweepers and targets exist
 */
public class Minefield {

    //targets on the field
    private List<Target> targets = new ArrayList<Target>();
    private int numTargets;

    //sweepers on the field
    private List<Sweeper> sweepers = new ArrayList<Sweeper>();
    private int numSweepers;

    //dimension of the minefield
    private int width;
    private int height;


    //Jframe window of the minefield
    JFrame frame;

    //panel that renders the minefield
    MyGraphicPanel panel;

    //controls speed of the animation by causing the thread to sleep for a set amount of milliseconds
    private final int ANIMATION_SPEED = 2;

    public Minefield(int width, int height, int numSweepers, int numTargets){
        this.width = width;
        this.height = height;
        for(int i = 0; i < numTargets; i++)
            targets.add(new Target(this));
        for(int i = 0; i < numSweepers; i++)
            sweepers.add(new Sweeper(this));
        //sets up swing components for graphical rendering
        panel = new MyGraphicPanel(this);
        frame = new JFrame("Window");
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
    gets the list of targets
     */
    public List<Target> getTargets() {
        return targets;
    }

    /**
    Gets the list of sweepers
     */
    public List<Sweeper> getSweepers() {
        return sweepers;
    }

    /**
    Gets width
    */
    public int getWidth() {
        return width;
    }

    public void setSweepers(List<Sweeper> toSet) {
        sweepers = toSet;
    }
    /**
    Gets height
     */
    public int getHeight() {
        return height;
    }

    /**
    simulates a single generation of sweepers
     */
    public void simulate(int ticks, int drawDelay, int generation){
        int i = 0;
        while(i < ticks){
            for(Sweeper s : sweepers) {
                //updates sweeper position
                s.updatePosition();
                //makes sure sweeper is within the bounds of the window
                fixPosition(s);
                if(s.foundTarget()){
                    targets.remove(s.getClosestTargetIndex());
                    targets.add(new Target(this));
                }
            }
            //only draws if the current generation is after the draw delay
            if(generation > drawDelay){
                panel.repaint();
            }
            //delays animation
            try{
                Thread.sleep(ANIMATION_SPEED);
            } catch (InterruptedException e){
                //there was an error!
            }
            i++;
        }
    }

    /**
    fixes the position of each sweeper on the field so they do not go out of bounds
     */
    public void fixPosition(Sweeper s){
        if(s.getX() > width){
            s.setX(0);
        }
        if(s.getX() < 0){
            s.setX(width);
        }
        if(s.getY() > height){
            s.setY(0);
        }
        if(s.getY() < 0){
            s.setY(height);
        }
    }
}
